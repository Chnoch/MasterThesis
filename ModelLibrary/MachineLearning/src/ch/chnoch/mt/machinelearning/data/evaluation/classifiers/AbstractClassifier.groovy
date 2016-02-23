package ch.chnoch.mt.machinelearning.data.evaluation.classifiers

import ch.chnoch.mt.machinelearning.data.interfaces.IClassifier
import weka.classifiers.Classifier
import weka.classifiers.Evaluation
import weka.core.Instances

/**
 * Created by Chnoch on 29.01.2016.
 */
public abstract class AbstractClassifier implements IClassifier {
    protected Instances instances

    public AbstractClassifier(instances) {
        this.instances = instances
    }

    public abstract Classifier classifyModel();

    @Override
    public Evaluation evaluateModel(Classifier classifier) {
        Evaluation eval = new Evaluation(instances);
        def folds = instances.size() > 10 ? 10 : instances.size()
        if (folds < 2) folds = 2

        eval.crossValidateModel(classifier, instances, folds, new Random(1));

        return eval
    }
}
