package ch.chnoch.mt.machinelearning.data.preparation

import ch.chnoch.mt.machinelearning.data.model.Model
import ch.chnoch.mt.machinelearning.data.model.ModelEntry

/**
 * Created by Chnoch on 27.02.2015.
 */
class CSVToModelConverter {

    public convertCSVFile(file) {
        def model = new Model()
        parseFile(file, model)

        return model
    }

    private parseFile(file, model) {
        def previousEntry
        file.splitEachLine(',') { fields ->
            def entry = parseLine(fields, previousEntry)
            if (entry.userId) {
                previousEntry = entry
                model.addEntry(entry)
            }
        }
    }

    private parseLine(fields, previousEntry) {
        def timestampStartString = fields[0]
        def timestampStopString = fields[1]
        def url = fields[2]
        def params = url.split('\\?')[1].split('&')

        def stationId = (params[0].split('=') as List)[1]
        def userId = (params[1].split('=') as List)[1]

        if (stationId.startsWith('00')) {
            stationId = stationId.substring(2)
        }

        def timestampStart = Date.parse("yyyy-MM-dd'T'HH:mm:ss", timestampStartString)
        def timestampStop = Date.parse("yyyy-MM-dd'T'HH:mm:ss'Z'", timestampStopString)

        def hourOfDay = Integer.parseInt(timestampStart.format("HH").toString());
        def calendar = timestampStart.toCalendar()
        def day = calendar.get(Calendar.DAY_OF_WEEK)

        def dayOfWeek = Integer.parseInt(day.toString());
        def weekday = true
        if (day in [Calendar.SUNDAY, Calendar.SATURDAY]) {
            weekday = false
        }

        return new ModelEntry(timestampStart, timestampStop, userId, stationId, hourOfDay, dayOfWeek, weekday)
    }
}
