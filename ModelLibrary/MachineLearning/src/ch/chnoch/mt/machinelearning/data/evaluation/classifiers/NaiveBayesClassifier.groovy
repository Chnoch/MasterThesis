package ch.chnoch.mt.machinelearning.data.evaluation.classifiers

import weka.classifiers.Classifier
import weka.classifiers.bayes.NaiveBayesMultinomial

/**
 * Created by Chnoch on 21.03.2015.
 */
class NaiveBayesClassifier extends AbstractClassifier {

    public NaiveBayesClassifier(instances) {
        super(instances)
    }

    public Classifier classifyModel() {
        NaiveBayesMultinomial naiveBayes = new NaiveBayesMultinomial();
        naiveBayes.buildClassifier(instances);
        return naiveBayes
    }

}

