/**
 * Created by Chnoch on 17.06.2014.
 */
class Data {

    def readFile(filename) {
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

    def parseLine(fields) {
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
}
