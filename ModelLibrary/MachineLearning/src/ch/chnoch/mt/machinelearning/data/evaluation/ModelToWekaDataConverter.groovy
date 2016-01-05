package ch.chnoch.mt.machinelearning.data.evaluation

import com.sun.org.apache.xpath.internal.operations.Bool
import weka.core.Attribute
import weka.core.DenseInstance
import weka.core.FastVector
import weka.core.Instance
import weka.core.Instances
import weka.core.ProtectedProperties
import weka.core.converters.ArffSaver

/**
 * Created by Chnoch on 16.02.2015.
 */
class ModelToWekaDataConverter {

    public static saveToFile(model, file) {
        def instances = convertToWeka(model.getAllEntries())

        // save ARFF
        ArffSaver saver = new ArffSaver();
        saver.setInstances(instances);
        saver.setFile(file);
        saver.writeBatch();// save ARFF
    }

    public static saveToFileForUsers(model, filepath) {
        model.getUsers().each { user ->
            def userEntries = model.getEntriesForUser(user)
            if (userEntries.size() > 1) {
                def instances = convertToWeka(userEntries)
                def file = new File(filepath + user + '.arff')

                ArffSaver saver = new ArffSaver();
                saver.setInstances(instances);
                saver.setFile(file);
                saver.writeBatch();
            }
        }
    }

    public static getInstances(model) {
        return convertToWeka(model.getAllEntries())
    }

    public static getInstancesForUser(model, user) {
        def userEntries = model.getEntriesForUser(user)
        def stations = model.getNextStationsForUser(user)
        return convertToWeka(userEntries, stations)
    }

    private static getStationIds(entries) {
        // Create list to hold nominal values "first", "second", "third"
        def stationValues = new HashSet<String>()

        entries.each { it ->
            stationValues.add(it.stationId)
        }

        stationValues.add('null')

        return stationValues.toList()
    }

    private static getUserIds(entries) {
        def userValues = new HashSet<String>()

        entries.each { it ->
            userValues.add(it.userId)
        }

        userValues.add('null')

        return userValues.toList()
    }

    private static convertToWeka(entries, nextStations) {
        def stationIds = getStationIds(entries)
        def userIds = getUserIds(entries)
        def hoursOfDay = (0..23).collect({ it.toString() })
        def minutesOfHour = (0..59).collect({ it.toString() })

        def daysOfWeek = (0..7).collect({ it.toString() })

//        def timestampStart = new Attribute('timestampStart')
//        def timestampEnd = new Attribute('timestampEnd')

        def userId = new Attribute('userId', userIds)
        def stationId = new Attribute('stationId', stationIds)
        def hourOfDay = new Attribute('hourOfDay', hoursOfDay)
        def minuteOfHour = new Attribute('minuteOfHour', minutesOfHour)
        def dayOfWeek = new Attribute('dayOfWeek', daysOfWeek)
        def weekdayVector = new ArrayList()
        weekdayVector.add("true");
        weekdayVector.add("false");
        def weekday = new Attribute('weekday', weekdayVector)
        def previousStationId = new Attribute('previousStationId', stationIds)
        def nextStationId = new Attribute('nextStationId', nextStations)


        def attrs = new ArrayList()
//        attrs.add(timestampStart)
//        attrs.add(timestampEnd)
//        attrs.add(userId)
        attrs.add(stationId)
//        attrs.add(hourOfDay)
//        attrs.add(minuteOfHour)
//        attrs.add(dayOfWeek)
//        attrs.add(weekday)
//        attrs.add(previousStationId)
        attrs.add(nextStationId)


        def data = new Instances('users', attrs, 0)

        entries.each { it ->
            if (it.nextStationId) {
                def instance = new DenseInstance(attrs.size())
//            instance.setValue(timestampStart, it.timestampStart.time)
//            instance.setValue(timestampEnd, it.timestampEnd.time)
//                instance.setValue(userId, it.userId)
                instance.setValue(stationId, it.stationId)
//                instance.setValue(hourOfDay, it.hourOfDay)
//                instance.setValue(minuteOfHour, it.minuteOfHour)
//                instance.setValue(dayOfWeek, it.dayOfWeek)
//                instance.setValue(weekday, it.weekday.toString())
//                instance.setValue(previousStationId, it.previousStationId ?: 'null')
                instance.setValue(nextStationId, it.nextStationId)
                data.add(instance)
            }
        }

        return data
    }
}
