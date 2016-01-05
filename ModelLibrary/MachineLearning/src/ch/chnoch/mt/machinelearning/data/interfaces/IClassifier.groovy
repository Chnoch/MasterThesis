package ch.chnoch.mt.machinelearning.data.interfaces;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;

/**
 * Created by Chnoch on 05.01.2016.
 */
public interface IClassifier {

    Classifier classifyModel();

    Evaluation evaluateModel(Classifier classifier);
}
