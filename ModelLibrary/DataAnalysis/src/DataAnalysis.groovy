/**
 * Created by Chnoch on 07.07.2014.
 */
class DataAnalysis {

    def static startDataAnalysis(dataEntries) {
        def cleanEntries = removeDuplicateEntries(dataEntries)
//        distributionOfStops(dataEntries)
//        averageOnPeopleAndStops(dataEntries)
//        averageOnStopUtilization(cleanEntries)
//        timeOfDayDistribution(cleanEntries)
//        dayOfMonthDistribution(cleanEntries)
//        numberOfStopsByUser(cleanEntries)
//        numberOfStationsPerUser(cleanEntries)
        numberOfStationsPerAverageUser(cleanEntries)
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

    def static removeDuplicateEntries(dataEntries) {
        dataEntries.sort { a, b ->
            a.timestampStart <=> b.timestampStart
            a.stationId <=> b.stationId
            a.userId <=> b.userId
        }

        def remainingEntries = []

        def previousEntryAdded = false
        for (def i = 0; i < dataEntries.size() - 2; i++) {
            def current = dataEntries[i]
            def next = dataEntries[i + 1]
            if (!(current.userId == next.userId &&
                    current.stationId == next.stationId &&
                    next.timestampStart - current.timestampStart < 60 &&
                    previousEntryAdded)) {
                remainingEntries.add(current)
                previousEntryAdded = true
            } else {
                previousEntryAdded = false
            }
        }

        return remainingEntries
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

    def static numberOfStationsPerAverageUser(dataEntries) {
        def userMap = [:]
        def stations = new HashSet()

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

            stations.add(it.stationId)
        }

        def stationAverages = [:]

        stations.each { it ->
            def cumulatedAccesses = 0.0f
            def numberOfUsers = 0.0f
            userMap.each { k, v ->
                if (v.containsKey(it)) {
                    cumulatedAccesses += v[it]
                    numberOfUsers++
                }
            }

            stationAverages[it] = cumulatedAccesses / numberOfUsers
        }

        stationAverages.sort { a, b ->
            a.value <=> b.value
        }.each { k, v ->
            println k
        }.each { k, v ->
            println v
        }


    }
}
