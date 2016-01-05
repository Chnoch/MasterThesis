package ch.chnoch.mt.machinelearning.data.model

/**
 * Created by Chnoch on 27.02.2015.
 */
class Model {
    private model
    private availableNextStations

    public Model() {
        model = new ArrayList()
        availableNextStations = new HashMap<String, List>()
    }

    public addEntry(entry) {
        model.add(entry)
    }

    public getAllEntries() {
        return model;
    }

    public setEntries(entries) {
        model = new ArrayList()
        model.addAll(entries)
    }

    public getEntriesForUser(userId) {
        return model.findAll { it ->
            it.userId == userId
        }
    }

    public getEntriesForStation(stationId) {
        return model.findAll { it ->
            it.stationId == stationId
        }
    }

    public getUsers() {
        def users = []
        model.each { it ->
            if (!users.contains(it.userId)) {
                users.add(it.userId)
            }
        }
        return users
    }

    public getNextStationsForUser(user) {
        def nextStationList = availableNextStations.get(user)
        if (nextStationList == null) {
            nextStationList = new ArrayList()
            nextStationList.add('null')
        }
        return nextStationList
    }

    public setAvailableStationsForUser(user, stations) {
        availableNextStations.put(user, stations)
    }
}
