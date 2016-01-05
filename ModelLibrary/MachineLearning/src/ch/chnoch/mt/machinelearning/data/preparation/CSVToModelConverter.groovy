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
        file.splitEachLine(',') { fields ->
            def entry = parseLine(fields)
            if (entry.userId) {
                model.addEntry(entry)
            }
        }
    }

    private parseLine(fields) {
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

        def hourOfDay = Integer.parseInt(timestampStart.format("HH"));
        def minuteOfHour = Integer.parseInt(timestampStart.format("mm"));
        def calendar = timestampStart.toCalendar()
        def day = calendar.get(Calendar.DAY_OF_WEEK)

        def dayOfWeek = day;
        def weekday = !(day in [Calendar.SUNDAY, Calendar.SATURDAY])

        return new ModelEntry(timestampStart, timestampStop, userId, stationId, hourOfDay, minuteOfHour, dayOfWeek, weekday)
    }
}
