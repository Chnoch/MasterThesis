package ch.chnoch.mt.machinelearning.data.evaluation.classifiers

import ch.chnoch.mt.machinelearning.data.interfaces.IClassifier
import weka.classifiers.Classifier
import weka.classifiers.Evaluation
import weka.classifiers.trees.J48

/**
 * Created by Chnoch on 21.03.2015.
 */
class RandomForestClassifier implements IClassifier {
    def instances

    public RandomForestClassifier(instances) {
        this.instances = instances
    }

    public Classifier classifyModel() {

        String[] options = new String[1];
        options[0] = "-U";            // unpruned tree
//        NaiveBayes naiveBayes = new NaiveBayes();         // new instance of tree
//        tree.setOptions(options);     // set the options
//        naiveBayes.buildClassifier(instances);   // build classifier
        J48 classifier = new J48();
        classifier.setOptions(options)
        classifier.buildClassifier(instances);
        return classifier

        //displaying J48 tree
//        TreeVisualizer tv = new TreeVisualizer(null,classifier.graph(),new PlaceNode2());

//        System.out.println(eval.toSummaryString("\nResults\n======\n", false));
    }

    public Evaluation evaluateModel(Classifier classifier) {
        Evaluation eval = new Evaluation(instances);
        def folds = instances.size() > 10 ? 10 : instances.size()
        if (folds < 2) folds = 2

//        def folds = 2
        eval.crossValidateModel(classifier, instances, folds, new Random(1));

        return eval
    }
}

