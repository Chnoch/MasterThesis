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

    private String title;

    public ExecutionPlan(Model model, String title) {
        this.model = model;
        this.title = title;
    }

    @Override
    public void addFeature(IFeature feature) {
        features.add(feature)
    }

    @Override
    int getFeatureCount() {
        return features.size();
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
        println(this.title)
        println('Precision\tRecall\tF-Measure')
        model.getPreparedUsers().each { user ->
            prepareWeka(user);
            Classifier wekaClassifier = classifier.classifyModel();
            Evaluation wekaEvaluation = classifier.evaluateModel(wekaClassifier);
            evaluation.evaluate(wekaEvaluation);
            evaluation.evaluateUser(wekaEvaluation, user);
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
