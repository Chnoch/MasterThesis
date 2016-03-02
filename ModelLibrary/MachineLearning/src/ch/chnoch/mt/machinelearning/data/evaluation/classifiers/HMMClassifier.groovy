package ch.chnoch.mt.machinelearning.data.evaluation.classifiers

import weka.classifiers.Classifier
import weka.classifiers.bayes.HMM
import weka.classifiers.functions.MultilayerPerceptron

/**
 * Created by Chnoch on 21.03.2015.
 */
class HMMClassifier extends AbstractClassifier {

    public Classifier classifyModel() {
        HMM classifier = new HMM()
        classifier.initEstimatorsMultivariateNormal()
        classifier.buildClassifier(instances);
        return classifier
    }
}

