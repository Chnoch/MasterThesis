package ch.chnoch.mt.machinelearning.data.preparation

import weka.core.Instances

/**
 * Created by Chnoch on 21.03.2015.
 */
class WekaInstances {

    static loadWekaInstances(userId, filepath) {
        def path = filepath + userId + '.arff'
        if (new File(path).exists()) {
            BufferedReader reader = new BufferedReader(
                    new FileReader(path));
            Instances data = new Instances(reader);
            reader.close();
            // setting class attribute
            data.setClassIndex(data.numAttributes() - 1);
            return data
        } else {
            return null
        }
    }
}
