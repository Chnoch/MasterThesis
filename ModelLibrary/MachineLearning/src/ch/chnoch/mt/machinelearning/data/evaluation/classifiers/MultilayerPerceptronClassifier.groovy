package ch.chnoch.mt.machinelearning.data.evaluation.classifiers

import weka.classifiers.Classifier
import weka.classifiers.functions.MultilayerPerceptron
import weka.classifiers.trees.RandomForest

/**
 * Created by Chnoch on 21.03.2015.
 */
class MultilayerPerceptronClassifier extends AbstractClassifier {

    public Classifier classifyModel() {
        MultilayerPerceptron classifier = new MultilayerPerceptron()
        classifier.setTrainingTime(10)
        classifier.buildClassifier(instances);
        return classifier
    }
}

