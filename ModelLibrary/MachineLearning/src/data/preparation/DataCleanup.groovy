package data.preparation

/**
 * Created by Chnoch on 16.02.2015.
 */
class DataCleanup {

    public parseModel(model) {
        def allEntries = model.getAllEntries()
        def entries, duplicates
        (entries, duplicates) = removeDuplicates(allEntries)

        model.setEntries(entries)

        return model
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
