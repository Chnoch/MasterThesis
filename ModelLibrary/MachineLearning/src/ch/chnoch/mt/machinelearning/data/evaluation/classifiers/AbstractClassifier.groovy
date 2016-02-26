package ch.chnoch.mt.machinelearning.data.evaluation.classifiers

import ch.chnoch.mt.machinelearning.data.interfaces.IClassifier
import weka.classifiers.Classifier
import weka.classifiers.Evaluation
import weka.core.Instances

/**
 * Created by Chnoch on 29.01.2016.
 */
public abstract class AbstractClassifier implements IClassifier {

    private static final int AMOUNT_OF_FOLDS = 10;

    protected Instances instances

    public void instantiate(Instances instances) {
        this.instances = instances
    }

    public abstract Classifier classifyModel();

    @Override
    public Evaluation evaluateModel(Classifier classifier) {
        System.println(instances.size())

        Evaluation eval = new Evaluation(instances);
        def folds = instances.size() > AMOUNT_OF_FOLDS ? AMOUNT_OF_FOLDS : instances.size()
        if (folds < 2) folds = 2

        eval.crossValidateModel(classifier, instances, folds, new Random(10));

        return eval
    }
}
