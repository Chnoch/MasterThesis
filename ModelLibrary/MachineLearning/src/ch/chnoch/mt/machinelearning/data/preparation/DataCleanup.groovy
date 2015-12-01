package ch.chnoch.mt.machinelearning.data.preparation

import groovy.time.TimeCategory


/**
 * Created by Chnoch on 16.02.2015.
 */
class DataCleanup {

    public parseModel(model) {
        def allEntries = model.getAllEntries()
        def entries, duplicates
        (entries, duplicates) = removeDuplicates(allEntries)

        removeLowProfileUsers(entries)

        assignPreviousStations(entries)
        assignNextStations(entries)
        model.setEntries(entries)

        return model
    }

    private removeLowProfileUsers(entries) {

    }

    private assignPreviousStations(modelEntries) {

        // Just to make sure they're still properly sorted
        modelEntries.sort { a, b ->
            a.timestampStart <=> b.timestampStart
            a.stationId <=> b.stationId
            a.userId <=> b.userId
        }

        def previousEntry
        def amountOfNull = 0;

        modelEntries.each() { entry ->
            entry.previousStationId = getStationOrNull(entry, previousEntry)
            entry.previousStationId != null ?: amountOfNull++

            previousEntry = entry
        }
        println('amountOfNull: ' + amountOfNull)

    }


    private assignNextStations(modelEntries) {

        // Just to make sure they're still properly sorted
        modelEntries.sort { a, b ->
            a.timestampStart <=> b.timestampStart
            a.stationId <=> b.stationId
            a.userId <=> b.userId
        }

        def amountOfNull = 0;
        for (def i = 0; i < modelEntries.size() - 1; i++) {
            def currentEntry = modelEntries[i]
            def nextEntry = modelEntries[i + 1]
            currentEntry.nextStationId = getStationOrNull(currentEntry, nextEntry)
            currentEntry.nextStationId != null ?: amountOfNull++
        }
        println('amountOfNull: ' + amountOfNull)
    }

    private getStationOrNull(entry, questionableEntry) {
        def timestamp = entry.timestampStart
        def nextTimestamp = questionableEntry?.timestampStart

        def hourOfDay = Integer.parseInt(timestamp.format("HH"));
        def calendar = timestamp.toCalendar()
        def day = calendar.get(Calendar.DAY_OF_WEEK)

        def nextHourOfDay = nextTimestamp?.format("HH")?.toInteger();
        def nextCalendar = nextTimestamp?.toCalendar()
        def nextDay = nextCalendar?.get(Calendar.DAY_OF_WEEK)

        if (entry?.userId == questionableEntry?.userId) {
//            println('day: ' + day + '; nextDay: ' + nextDay + '; hourOfDay: ' + hourOfDay + '; nextHourOfDay: ' + nextHourOfDay);
            if (day == nextDay && !(hourOfDay > 4 && nextHourOfDay < 4)) {
//                println('true')
                return questionableEntry.stationId
            } else if (nextDay == (day - 1) % 7 && hourOfDay < 4) {
//                println('true')
                return questionableEntry.stationId
            }
        }
//        println('false')
        return null
    }

    private removeDuplicates(modelEntries) {
        modelEntries.sort { a, b ->
            a.timestampStart <=> b.timestampStart
            a.stationId <=> b.stationId
            a.userId <=> b.userId
        }

        def remainingEntries = []
        def duplicateEntries = []

        def duplicate = false
        def currentItem = null
        modelEntries.eachWithIndex() { value, index ->
            if (currentItem?.userId == value.userId &&
                    currentItem?.stationId == value.stationId &&
                    value.timestampStart - currentItem?.timestampStart < 60
            ) {
                duplicate = true
            }

            if (!duplicate) {
                currentItem = value
                remainingEntries.add(currentItem)
            } else {
                duplicateEntries.add(value)
            }

            duplicate = false
        }

        return [remainingEntries, duplicateEntries]
    }
}
