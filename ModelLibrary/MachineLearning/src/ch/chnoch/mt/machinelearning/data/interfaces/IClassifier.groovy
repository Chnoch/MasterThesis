package ch.chnoch.mt.machinelearning.data.interfaces;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation
import weka.core.Instances;

/**
 * Created by Chnoch on 05.01.2016.
 */
public interface IClassifier {

    void instantiate(Instances instances)

    Classifier classifyModel();

    Evaluation evaluateModel(Classifier classifier);
}
