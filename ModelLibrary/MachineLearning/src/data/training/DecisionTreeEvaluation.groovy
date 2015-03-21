package data.training

import weka.classifiers.Evaluation
import weka.classifiers.trees.J48

/**
 * Created by Chnoch on 21.03.2015.
 */
class DecisionTreeEvaluation {
    def instances

    public DecisionTreeEvaluation(instances) {
        this.instances = instances
    }

    def evaluateModel() {
        String[] options = new String[1];
        options[0] = "-U";            // unpruned tree
        J48 tree = new J48();         // new instance of tree
        tree.setOptions(options);     // set the options
        tree.buildClassifier(instances);   // build classifier

        Evaluation eval = new Evaluation(instances);
        eval.crossValidateModel(tree, instances, 10, new Random(1));

        System.out.println(eval.toSummaryString("\nResults\n======\n", false));
    }
}

