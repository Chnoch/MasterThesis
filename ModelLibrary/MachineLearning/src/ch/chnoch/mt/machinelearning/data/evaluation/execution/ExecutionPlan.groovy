package ch.chnoch.mt.machinelearning.data.evaluation.execution

import ch.chnoch.mt.machinelearning.data.interfaces.IClassifier
import ch.chnoch.mt.machinelearning.data.interfaces.IEvaluation
import ch.chnoch.mt.machinelearning.data.interfaces.IExecutionPlan
import ch.chnoch.mt.machinelearning.data.interfaces.IFeature
import ch.chnoch.mt.machinelearning.data.model.Model
import ch.chnoch.mt.machinelearning.data.model.User
import weka.classifiers.Classifier
import weka.classifiers.Evaluation
import weka.core.Attribute
import weka.core.DenseInstance
import weka.core.Instances

/**
 * Created by Chnoch on 12.02.2016.
 */
public class ExecutionPlan implements IExecutionPlan {

    private List<IFeature> features = new ArrayList<>();

    private IClassifier classifier;

    private IEvaluation evaluation;

    private Model model;

    public ExecutionPlan(Model model) {
        this.model = model;
    }

    @Override
    public void addFeature(IFeature feature) {
        features.add(feature)
    }

    @Override
    public void setClassifier(IClassifier classifier) {
        this.classifier = classifier;
    }

    @Override
    public void setEvaluation(IEvaluation evaluation) {
        this.evaluation = evaluation;
    }

    @Override
    public void startExecutionAndEvaluation() {
        model.getPreparedUsers().each { user ->
            prepareWeka(user);

            Classifier wekaClassifier = classifier.classifyModel();
            Evaluation wekaEvaluation = classifier.evaluateModel(wekaClassifier);
            evaluation.evaluate(wekaEvaluation);
        }
        evaluation.completeEvaluation()
    }

    private void prepareWeka(final User user) {
        features.each { feature -> feature.instantiate(user) }

        List<Attribute> attrs = new ArrayList<>()
        features.each { feature -> attrs.add(feature.getAttribute()) }
        Instances instances = new Instances(user.getUserId(), attrs, user.getPreparedEntries().size())

        user.getPreparedEntries().each { entry ->
            DenseInstance instance = new DenseInstance(attrs.size())

            features.each { feature -> feature.setValue(entry, instance) }

            instances.add(instance)
        }
        instances.setClassIndex(attrs.size() - 1)

        classifier.instantiate(instances)
    }
}
