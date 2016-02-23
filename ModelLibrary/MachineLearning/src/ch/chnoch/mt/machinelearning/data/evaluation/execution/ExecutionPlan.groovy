package ch.chnoch.mt.machinelearning.data.evaluation.execution

import ch.chnoch.mt.machinelearning.data.interfaces.IClassifier
import ch.chnoch.mt.machinelearning.data.interfaces.IEvaluation
import ch.chnoch.mt.machinelearning.data.interfaces.IExecutionPlan
import ch.chnoch.mt.machinelearning.data.interfaces.IFeature
import weka.classifiers.Classifier
import weka.classifiers.Evaluation
import weka.core.Instances

/**
 * Created by Chnoch on 12.02.2016.
 */
public class ExecutionPlan implements IExecutionPlan {

    private List<IFeature> features = new ArrayList<>();

    private IClassifier classifier;

    private IEvaluation evaluation;

    public ExecutionPlan(List<IFeature> features) {
        this.features = features;
    }

    @Override
    void setClassifier(IClassifier classifier) {
        this.classifier = classifier;
    }

    @Override
    void setEvaluation(IEvaluation evaluation) {
        this.evaluation = evaluation;
    }

    @Override
    void startExecutionAndEvaluation() {
        Classifier wekaClassifier = classifier.classifyModel();
        Evaluation wekaEvaluation = classifier.evaluateModel(wekaClassifier);
        evaluation.startEvaluation(wekaEvaluation);
    }
}
