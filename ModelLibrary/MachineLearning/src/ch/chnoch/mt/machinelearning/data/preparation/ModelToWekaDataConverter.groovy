package ch.chnoch.mt.machinelearning.data.preparation

import com.sun.org.apache.xpath.internal.operations.Bool
import weka.core.Attribute
import weka.core.FastVector
import weka.core.Instance
import weka.core.Instances
import weka.core.ProtectedProperties
import weka.core.converters.ArffSaver

/**
 * Created by Chnoch on 16.02.2015.
 */
class ModelToWekaDataConverter {

    public saveToFile(model, file) {
        def instances = convertToWeka(model.getAllEntries())

        // save ARFF
        ArffSaver saver = new ArffSaver();
        saver.setInstances(instances);
        saver.setFile(file);
        saver.writeBatch();// save ARFF
    }

    public saveToFileForUsers(model, filepath) {
        model.getUsers().each { user ->
            def userEntries = model.getEntriesForUser(user)
            if (userEntries.size() > 5) {
                def instances = convertToWeka(userEntries)
                def file = new File(filepath + user + '.arff')

                ArffSaver saver = new ArffSaver();
                saver.setInstances(instances);
                saver.setFile(file);
                saver.writeBatch();
            }
        }
    }

    public convertToWeka(entries) {
        def dateFormat = "yyyy-MM-dd'T'HH:mm:ss"
        FastVector attributeVector = new FastVector()
        def timestampStart = new Attribute('timestampStart')
        def timestampEnd = new Attribute('timestampEnd')
        def userId = new Attribute('userId')
        def stationId = new Attribute('stationId')
        def hourOfDay = new Attribute('hourOfDay')
        def dayOfWeek = new Attribute('dayOfWeek')
        def weekdayVector = new FastVector(2)
        weekdayVector.addElement("true");
        weekdayVector.addElement("false");
        def weekday = new Attribute('weekday', weekdayVector)
        def previousStationId = new Attribute('previousStationId')
        def attrs = new FastVector(8)
        attrs.addElement(timestampStart)
        attrs.addElement(timestampEnd)
        attrs.addElement(userId)
        attrs.addElement(stationId)
        attrs.addElement(hourOfDay)
        attrs.addElement(dayOfWeek)
        attrs.addElement(weekday)
        attrs.addElement(previousStationId)


        def data = new Instances('test', attrs, 0)

        entries.each { it ->
            def instance = new Instance(8)
            instance.setValue(timestampStart, it.timestampStart.time)
            instance.setValue(timestampEnd, it.timestampEnd.time)
            instance.setValue(userId, it.userId.toLong())
            instance.setValue(stationId, it.stationId.toLong())
            instance.setValue(hourOfDay, it.hourOfDay)
            instance.setValue(dayOfWeek, it.dayOfWeek)
            instance.setValue(weekday, it.weekday.toString())
            instance.setValue(previousStationId, it.previousStationId?.toLong() ?: 0)
            data.add(instance)
        }

        return data
    }
}
