package ch.chnoch.mt.machinelearning.data.evaluation.classifiers

import ch.chnoch.mt.machinelearning.data.interfaces.IClassifier
import weka.classifiers.Classifier
import weka.classifiers.Evaluation
import weka.classifiers.trees.J48
import weka.core.Instances

/**
 * Created by Chnoch on 21.03.2015.
 */
class DecisionTreeClassifier extends AbstractClassifier {

    public Classifier classifyModel() {
        String[] options = new String[1];
        options[0] = "-U";            // unpruned tree
        J48 classifier = new J48();
        classifier.setOptions(options)
        classifier.buildClassifier(instances);
        return classifier

    }

    public Evaluation evaluateModel(Classifier classifier) {
        Evaluation eval = new Evaluation(instances);
        def folds = instances.size() > 10 ? 10 : instances.size()
        if (folds < 2) folds = 2

        eval.crossValidateModel(classifier, instances, folds, new Random(1));

        return eval
    }
}

