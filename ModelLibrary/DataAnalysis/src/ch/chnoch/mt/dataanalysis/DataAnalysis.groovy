/**
 * Created by Chnoch on 07.07.2014.
 */
package ch.chnoch.mt.dataanalysis

class DataAnalysis {

    def static startDataAnalysis(dataEntries) {
//        def cleanEntries = removeDuplicateEntries(dataEntries)
//        distributionOfStops(dataEntries)
//        averageOnPeopleAndStops(dataEntries)
//        averageOnStopUtilization(dataEntries)
//        timeOfDayDistribution(dataEntries)
//        dayOfMonthDistribution(dataEntries)
//        numberOfStopsByUser(dataEntries)
//        numberOfStationsPerUser(dataEntries)
//        numberOfStationsPerAverageUser(dataEntries)
        numberOfStationsPerAverageUserForWeekdays(dataEntries)
        numberOfStationsPerAverageUserForWeekend(dataEntries)
    }

    def static distributionOfStops(dataEntries) {
        // Remove dublicate Entries. Comment this line to work with the full set of data.
        dataEntries = removeDuplicateEntries(dataEntries)


        def stopMap = [:]
        dataEntries.each {
            if (stopMap.containsKey(it.stationId)) {
                stopMap[it.stationId] = stopMap[it.stationId] + 1
            } else {
                stopMap[it.stationId] = 1
            }
        }

        stopMap.sort { a, b ->
            b.value <=> a.value
        }.each { k, v ->
            println k
        }.each { k, v ->
            println v
        }

        def count = 0.0f
        def sum = 0.0f

        stopMap.each { k, v ->
            sum += v
            count++
        }

        println 'Total number of Stations: ' + count
        println 'Total number of Stops: ' + sum
        println 'Average number of Stops per station: ' + (sum / count)
    }

    def static averageOnPeopleAndStops(dataEntries) {
        dataEntries = removeDuplicateEntries(dataEntries)

        def stopMap = [:]

        dataEntries.each {
            if (stopMap.containsKey(it.stationId)) {
                stopMap[it.stationId].add(it.userId)
            } else {
                stopMap[it.stationId] = new HashSet([it.userId])
            }
        }

        stopMap.sort { a, b ->
            b.value.size() <=> a.value.size()
        }.each { k, v ->
            println k
        }.each { k, v ->
            println v.size()
        }

        def count = 0.0f
        def sum = 0.0f

        stopMap.each { k, v ->
            sum += v.size()
            count++
        }

        println 'Total number of Stops: ' + count
        println 'Total number of persons: ' + sum
        println 'Average number of person per stop: ' + (sum / count)

    }

    def static averageOnStationUtilization(dataEntries) {

    }

    def static timeOfDayDistribution(dataEntries) {
        def timeMap = [:]
        (0..23).each { it ->
            timeMap[it] = 0
        }

        dataEntries.each {
            def calendar = it.timestampStart.toCalendar()
            timeMap[calendar.get(Calendar.HOUR_OF_DAY)] += 1
        }

        timeMap.each { k, v ->
            println k
        }.each { k, v ->
            println v
        }
    }

    def static dayOfMonthDistribution(dataEntries) {
        def timeMap = [:]
        (1..31).each { it ->
            timeMap[it] = 0
        }

        dataEntries.each {
            def calendar = it.timestampStart.toCalendar()
            timeMap[calendar.get(Calendar.DAY_OF_MONTH)] += 1
        }

        timeMap.each { k, v ->
            println k
        }.each { k, v ->
            println v
        }
    }

    def static numberOfStopsByUser(dataEntries) {
        def stopMap = [:]

        dataEntries.each { it ->
            if (stopMap.containsKey(it.userId)) {
                stopMap[it.userId] += 1
            } else {
                stopMap[it.userId] = 1
            }
        }

        stopMap.sort { a, b ->
            b.value <=> a.value
        }.each { k, v ->
            println k
        }.each { k, v ->
            println v
        }
    }

    def static numberOfStationsPerUser(dataEntries) {
        def stopMap = [:]

        dataEntries.each { it ->
            if (stopMap.containsKey(it.userId)) {
                if (!stopMap[it.userId].contains(it.stationId)) {
                    stopMap[it.userId].add(it.stationId)
                }
            } else {
                stopMap[it.userId] = new HashSet()
                stopMap[it.userId].add(it.stationId)
            }
        }

        stopMap.sort { a, b ->
            b.value.size() <=> a.value.size()
        }.each { k, v ->
            println k
        }.each { k, v ->
            println v.size()
        }

        def sum = 0
        def count = 0

        stopMap.each { k, v ->
            sum += v.size()
            count++
        }

        println 'Average number of stations per person: ' + (sum / count)
    }

    def static numberOfStationsPerAverageUserForWeekdays(dataEntries) {
        println '----------------WEEKDAYS----------------'
        def weekdayEntries = []
        dataEntries.each {
            def calendar = it.timestampStart.toCalendar()
            def day = calendar.get(Calendar.DAY_OF_WEEK)
            if (!(day in [Calendar.SUNDAY, Calendar.SATURDAY])) {
                weekdayEntries.add(it)
            }
        }
        numberOfStationsPerAverageUser(weekdayEntries)
        println '----------------END WEEKDAYS----------------'
    }

    def static numberOfStationsPerAverageUserForWeekend(dataEntries) {
        println '----------------WEEKEND----------------'
        def weekendEntries = []
        dataEntries.each {
            def calendar = it.timestampStart.toCalendar()
            def day = calendar.get(Calendar.DAY_OF_WEEK)
            if (day in [Calendar.SUNDAY, Calendar.SATURDAY]) {
                weekendEntries.add(it)
            }
        }
        numberOfStationsPerAverageUser(weekendEntries)
        println '----------------END WEEKEND----------------'
    }

    def static numberOfStationsPerAverageUser(dataEntries) {
        def userMap = [:]

        // sorting the data entries by user and station accesses
        dataEntries.each { it ->
            if (userMap.containsKey(it.userId)) {
                if (userMap[it.userId].containsKey(it.stationId)) {
                    userMap[it.userId][it.stationId] += 1
                } else {
                    userMap[it.userId].put(it.stationId, 1)
                }
            } else {
                userMap[it.userId] = [:]
                userMap[it.userId].put(it.stationId, 1)
            }
        }

        // sorting all the users maps by the number of accesses
        userMap.each { userID, stationMap ->
            userMap[userID] = stationMap.sort { -it.value }
        }

        // figuring out max value of stations to account for
        def maxValue = 0
        userMap.each { k, v ->
            if (v.size() > maxValue) {
                maxValue = v.size()
            }
        }

        // remove users with very few (5) accesses on their top station.
        def workingUserMap = [:]
        userMap.each { k, v ->
            if (v.iterator()[0]?.value > 5) {
                workingUserMap.put(k, v)
            }
        }

        // generating the average over all users
        def graph = [:]
        (0..maxValue).each { i ->
            def value = 0.0f
            def count = 0.0f
            workingUserMap.each { userID, stationMap ->
                try {
                    value += stationMap.iterator()[i]?.value
                    count++
                } catch (def e) {
                }
            }
            graph[i] = value / count
        }
        graph.each { k, v ->
            println k
        }.each { k, v ->
            println v
        }
    }
}
