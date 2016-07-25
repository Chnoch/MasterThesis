package ch.chnoch.mt.machinelearning.data.evaluation.classifiers

import weka.classifiers.Classifier
import weka.classifiers.bayes.NaiveBayesMultinomial
import weka.core.Instance
import weka.core.Instances
import weka.filters.Filter
import weka.filters.supervised.attribute.NominalToBinary

/**
 * Created by Chnoch on 21.03.2015.
 */
class NaiveBayesClassifier extends AbstractClassifier {

    public Classifier classifyModel() {
        NaiveBayesMultinomial naiveBayes = new NaiveBayesMultinomial()
        preprocessData()
        naiveBayes.buildClassifier(instances);
        return naiveBayes
    }

    /*
     * Converts all multi-valued nominal attributes into binary attributes in order to be able to work with
     * the Naive Bayes algorithm
     */
    private void preprocessData() {
        Filter filter = new NominalToBinary()
        filter.setInputFormat(instances)
        for (int i = 0; i < instances.size(); i++) {
            filter.input(instances.instance(i))
        }
        filter.batchFinished()
        Instances newData = filter.getOutputFormat()
        Instance processed
        while ((processed = filter.output()) != null) {
            newData.add(processed)
        }

        instances = newData
    }
}

