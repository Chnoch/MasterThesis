/**
 * Created by Chnoch on 17.06.2014.
 */

package ch.chnoch.mt.dataparsing
class Data {
    static getDefaultDataSet() {
        def dataEntries = Data.readFile('D:\\Workspaces\\MasterThesis\\ModelLibrary\\assets\\learning_data.csv')
        dataEntries.sort { a, b ->
            a.timestampStart <=> b.timestampStart
            a.userId <=> b.userId
        }
        return dataEntries
    }

    static getCleanedDataSet() {
        return Data.removeDuplicateEntries(Data.getDefaultDataSet())
    }

    static readFile(filename) {
        def dataEntries = []

        def file = new File(filename)
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
