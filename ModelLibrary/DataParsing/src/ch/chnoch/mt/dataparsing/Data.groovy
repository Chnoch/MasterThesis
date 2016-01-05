/**
 * Created by Chnoch on 17.06.2014.
 */

package ch.chnoch.mt.dataparsing

class Data {

    public static dataSet
    public static cleanedDataSet


    public static getCleanedTrainingDataSetForUser(userId) {
        def completeSet = Data.cleanedDataSet
        return getUserTrainingDataSet(completeSet, userId)
    }

    public static getCleanedTestingDataSetForUser(userId) {
        def completeSet = Data.cleanedDataSet
        def completeTestingSet = completeSet.findAll { it -> it.userId == userId }
        completeTestingSet.sort { a, b ->
            a.timestampStart <=> b.timestampStart
        }

        def testingSet = completeTestingSet.subList((int) (completeTestingSet.size() / 3 * 2), completeTestingSet.size())
        return testingSet
    }


    public static getDefaultDataSet() {
        if (!dataSet) {
            def filename = 'D:\\Workspaces\\MasterThesis\\ModelLibrary\\assets\\learning_data.csv'
            def file = new File(filename)
            dataSet = getDataSetFromFile(file, false)
        }
        return dataSet
    }

    public static getCleanedDataSet() {
        if (!cleanedDataSet) {
            cleanedDataSet = Data.removeDuplicateEntries(Data.getDefaultDataSet())
        }
        return cleanedDataSet
    }

    public static getDataSetFromFile(file, removeDuplicates) {
        def dataEntries = Data.readFile(file)
        dataEntries.sort { a, b ->
            a.timestampStart <=> b.timestampStart
            a.userId <=> b.userId
        }

        if (removeDuplicates) {
            dataEntries = removeDuplicateEntries(dataEntries)
        }
        return dataEntries
    }

    public static getCleanedTrainingDataSetForUserFromFile(file, userId) {
        def completeSet = Data.getDataSetFromFile(file, true)
        return getUserTrainingDataSet(completeSet, userId)
    }

    public static getFullCleanedDataSetForUserFromFile(file, userId) {
        def completeSet = Data.getDataSetFromFile(file, true)

        def userSet = completeSet.findAll { it -> it.userId == userId }
        userSet.sort { a, b ->
            a.timestampStart <=> b.timestampStart
        }
        return userSet
    }

    private static getUserTrainingDataSet(completeSet, userId) {
        def completeTrainingSet = completeSet.findAll { it -> it.userId == userId }
        completeTrainingSet.sort { a, b ->
            a.timestampStart <=> b.timestampStart
        }
        def trainingSet = completeTrainingSet.subList(0, (int) (completeTrainingSet.size() / 3 * 2))
        return trainingSet
    }

    static readFile(file) {
        def dataEntries = []

        if (file) {
            file.splitEachLine(',') { fields ->
                def entry = parseLine(fields)
                if (entry.userId) {
                    dataEntries.add(entry)
                }
            }
        }
        return dataEntries
    }

    static parseLine(fields) {
        def timestampStartString = fields[0]
        def timestampStopString = fields[1]
        def url = fields[2]
        def params = url.split('\\?')[1].split('&')
//        println params
        def stationId = (params[0].split('=') as List)[1]
        def userId = (params[1].split('=') as List)[1]

        if (stationId.startsWith('00')) {
            stationId = stationId.substring(2)
        }

        def timestampStart = Date.parse("yyyy-MM-dd'T'HH:mm:ss", timestampStartString)
        def timestampStop = Date.parse("yyyy-MM-dd'T'HH:mm:ss'Z'", timestampStopString)

        def entry = new DataEntry(userId, timestampStart, timestampStop, stationId)
        return entry
    }

    static removeDuplicateEntries(dataEntries) {
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
}
