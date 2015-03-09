package data.preparation

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
        def instances = convertToWeka(model)

        // save ARFF
        ArffSaver saver = new ArffSaver();
        saver.setInstances(instances);
        saver.setFile(file);
//        saver.setDestination(file);
        saver.writeBatch();
    }

    public convertToWeka(model) {
        def dateFormat = "yyyy-MM-dd'T'HH:mm:ss"
        FastVector attributeVector = new FastVector()
        def timestampStart = new Attribute('timestampStart')
        def timestampEnd = new Attribute('timestampEnd')
        def userId = new Attribute('userId')
        def stationId = new Attribute('stationId')
        def attrs = new FastVector(4)
        attrs.addElement(timestampStart)
        attrs.addElement(timestampEnd)
        attrs.addElement(userId)
        attrs.addElement(stationId)
        def data = new Instances('test', attrs, 0)

        model.getAllEntries().each { it ->
            def instance = new Instance(4)
            instance.setValue(timestampStart, it.timestampStart.time)
            instance.setValue(timestampEnd, it.timestampEnd.time)
            instance.setValue(userId, it.userId.toLong())
            instance.setValue(stationId, it.stationId.toLong())
            data.add(instance)
        }

        return data
    }
}
