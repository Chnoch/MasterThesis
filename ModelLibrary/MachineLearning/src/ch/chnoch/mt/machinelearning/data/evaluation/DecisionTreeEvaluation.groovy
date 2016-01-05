package ch.chnoch.mt.machinelearning.data.evaluation

import weka.classifiers.Evaluation
import weka.classifiers.trees.J48
import weka.gui.treevisualizer.PlaceNode2
import weka.gui.treevisualizer.TreeVisualizer

/**
 * Created by Chnoch on 21.03.2015.
 */
class DecisionTreeEvaluation {
    def instances

    public DecisionTreeEvaluation(instances) {
        this.instances = instances
    }

    public classifyModel() {
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

    public evaluateModel(classifier) {
        Evaluation eval = new Evaluation(instances);
        def folds = instances.size() > 10 ? 10 : instances.size()
        if (folds < 2) folds = 2

//        def folds = 2
        eval.crossValidateModel(classifier, instances, folds, new Random(1));

        return eval
    }
}

