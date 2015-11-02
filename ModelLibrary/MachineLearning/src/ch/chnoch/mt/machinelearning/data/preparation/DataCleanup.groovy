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

        assignPreviousStations(entries)
        model.setEntries(entries)

        return model
    }

    private assignPreviousStations(modelEntries) {

        // Just to make sure they're still properly sorted
        modelEntries.sort { a, b ->
            a.timestampStart <=> b.timestampStart
            a.stationId <=> b.stationId
            a.userId <=> b.userId
        }

        def previousEntry
        modelEntries.each() { entry ->
            use(TimeCategory) {
                if (previousEntry?.timestampStart > (entry.timestampStart - 10.minutes)) {
                    entry.previousStationId = previousEntry.stationId
                }
                previousEntry = entry
            }
        }
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
