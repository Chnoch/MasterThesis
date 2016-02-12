package ch.chnoch.mt.machinelearning.data.preparation.converters

import ch.chnoch.mt.machinelearning.data.model.Model
import ch.chnoch.mt.machinelearning.data.model.ModelEntry

/**
 * Created by Chnoch on 27.02.2015.
 * The data set and format of the file has changed slightly in mid-2015, that's why we have different parsers for different files
 */
class UpdatedCSVToModelConverter extends AbstractConverter {

    protected ModelEntry parseLine(fields) {
        def timestampStartString = fields[0]
        def url = fields[1]
        def params = url.split('\\?')[1].split('&')

        def stationId = (params[0].split('=') as List)[1]
        def userId = (fields[2].split('=') as List)[1]

        if (stationId.startsWith('00')) {
            stationId = stationId.substring(2)
        }

        def timestampStart = Date.parse("yyyy-MM-dd'T'HH:mm:ss", timestampStartString)

        def hourOfDay = Integer.parseInt(timestampStart.format("HH"));
        def minuteOfHour = Integer.parseInt(timestampStart.format("mm"));
        def calendar = timestampStart.toCalendar()
        def day = calendar.get(Calendar.DAY_OF_WEEK)

        def dayOfWeek = day;
        def weekday = !(day in [Calendar.SUNDAY, Calendar.SATURDAY])

        return new ModelEntry(timestampStart, userId, stationId, hourOfDay, minuteOfHour, dayOfWeek, weekday)
    }
}
