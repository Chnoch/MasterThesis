/**
 * Created by Chnoch on 07.07.2014.
 */

//def timestampStart = '2013-12-28T22:44:43'
//def date = Date.parse("yyyy-MM-dd'T'HH:mm:ss", timestampStart)
//println date

def nD = new Date()

Calendar cal = nD.toCalendar()
println cal.get(Calendar.HOUR_OF_DAY)
println nD.getHours()