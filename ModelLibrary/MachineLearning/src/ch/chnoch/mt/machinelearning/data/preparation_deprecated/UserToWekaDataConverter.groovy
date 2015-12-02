package ch.chnoch.mt.machinelearning.data.preparation_deprecated

import weka.core.Attribute
import weka.core.DenseInstance
import weka.core.FastVector
import weka.core.Instance
import weka.core.Instances
import weka.core.converters.ArffSaver

/**
 * Created by Chnoch on 16.02.2015.
 */
class UserToWekaDataConverter {

    public saveUsersToFile(users, filepath) {
        users.each { user ->
            if (user.bags.size() > 5) {
                def file = new File(filepath + user.userId + '.arff')

                def instances = convertToWeka(user.bags)
                // save ARFF
                ArffSaver saver = new ArffSaver();
                saver.setInstances(instances);
                saver.setFile(file);
                saver.writeBatch();
            }
        }
    }

    public convertToWeka(bags) {
        def dateFormat = "yyyy-MM-dd'T'HH:mm:ss"
        FastVector attributeVector = new FastVector()
        def timestampStart = new Attribute('timestampStart')
        def timestampEnd = new Attribute('timestampEnd')
        def userId = new Attribute('userId')
        def stationIdStart = new Attribute('stationIdStart')
        def stationIdEnd = new Attribute('stationIdEnd')
        def attrs = new FastVector(5)
        attrs.addElement(timestampStart)
        attrs.addElement(timestampEnd)
        attrs.addElement(userId)
        attrs.addElement(stationIdStart)
        attrs.addElement(stationIdEnd)
        def data = new Instances('test', attrs, 0)

        bags.each { bag ->
            def instance = new DenseInstance(5)
            instance.setValue(timestampStart, bag.timestampStartBag.time)
            instance.setValue(timestampEnd, bag.timestampEndBag.time)
            instance.setValue(userId, bag.userId.toLong())
            instance.setValue(stationIdStart, bag.stationIdStart.toLong())
            instance.setValue(stationIdEnd, bag.stationIdEnd.toLong())
            data.add(instance)
        }

        return data
    }
}
