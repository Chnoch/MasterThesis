package ch.chnoch.mt.machinelearning.data.evaluation.classifiers

import ch.chnoch.mt.machinelearning.data.interfaces.IClassifier
import weka.classifiers.Classifier
import weka.classifiers.Evaluation
import weka.classifiers.trees.J48
import weka.classifiers.trees.RandomForest

/**
 * Created by Chnoch on 21.03.2015.
 */
class RandomForestClassifier extends AbstractClassifier {

    public Classifier classifyModel() {
        RandomForest classifier = new RandomForest()
        classifier.buildClassifier(instances);
        return classifier
    }
}

