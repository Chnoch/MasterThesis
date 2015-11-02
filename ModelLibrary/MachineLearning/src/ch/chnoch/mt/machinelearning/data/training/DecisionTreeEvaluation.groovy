package ch.chnoch.mt.machinelearning.data.training

import weka.classifiers.Evaluation
import weka.classifiers.bayes.NaiveBayes
import weka.classifiers.functions.LinearRegression
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
//        NaiveBayes naiveBayes = new NaiveBayes();         // new instance of tree
//        tree.setOptions(options);     // set the options
//        naiveBayes.buildClassifier(instances);   // build classifier
        LinearRegression regression = new LinearRegression();
        regression.buildClassifier(instances);

        Evaluation eval = new Evaluation(instances);
        eval.crossValidateModel(regression, instances, 10, new Random(1));

        System.out.println(eval.toSummaryString("\nResults\n======\n", false));
    }
}

