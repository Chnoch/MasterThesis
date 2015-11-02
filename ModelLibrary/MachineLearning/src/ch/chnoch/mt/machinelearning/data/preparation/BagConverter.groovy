package ch.chnoch.mt.machinelearning.data.preparation

import ch.chnoch.mt.machinelearning.data.model.Bag
import ch.chnoch.mt.machinelearning.data.model.User

/**
 * Created by Chnoch on 12.03.2015.
 * Creates bags from the raw data. A bag is consisting of a single trip, including userID, start time, start station,
 * end time and end station.
 * Stations with timestamps in between are cut out.
 */
class BagConverter {

    public createStatisticsOfData(model) {
        def users = model.getUsers()

        def previous
        def completeDifference = []
        users.each { user ->
            model.getEntriesForUser(user).each { it ->
                if (previous != null) {
                    completeDifference.add(it.timestampStart.time - previous.timestampStart.time)
                }
                previous = it
            }
        }
        return completeDifference.sort()
    }

    public createBagsForUser(model) {
        def users = model.getUsers();
        def userList = new ArrayList<User>()
        def bags = [:]

        users.each { userId ->
            def userBags = createBags(model.getEntriesForUser(userId), userId)
            def user = new User(userId);
            user.setBags(userBags)
            userList.add(user)
        }

        return userList
    }

    private createBags(data, user) {
        data.sort { a, b ->
            a.timestampStart <=> b.timestampStart
        }
//        println data

        def bags = []
        def previous, bagStart
        data.each { dataPoint ->
            if (bagStart == null) {
                bagStart = dataPoint
                previous = dataPoint
            } else {
                println user + ': ' + dataPoint.timestampStart.time - previous.timestampStart.time
                if (dataPoint.timestampStart.time - previous.timestampStart.time < 20 * 1000 * 60) {
                    // Part of bag
                    previous = dataPoint
                } else {
                    if (bagStart.stationId != previous.stationId) {
                        def bag = new Bag(bagStart.timestampStart, previous.timestampStart, user, bagStart.stationId, previous.stationId)
                        bags.add(bag)
                    }
                    bagStart = dataPoint
                    previous = dataPoint
                }
            }
        }
        return bags
    }
}
