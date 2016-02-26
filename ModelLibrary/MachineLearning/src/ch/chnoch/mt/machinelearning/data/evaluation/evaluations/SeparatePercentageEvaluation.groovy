package ch.chnoch.mt.machinelearning.data.evaluation.evaluations

import ch.chnoch.mt.machinelearning.data.interfaces.IEvaluation
import weka.classifiers.Evaluation

/**
 * Created by Chnoch on 12.02.2016.
 */
public class SeparatePercentageEvaluation implements IEvaluation {

    private long amount = 0L
    private double correct = 0L
    private double incorrect = 0L

    @Override
    public void evaluate(Evaluation evaluation) {
        amount++;
        long count = evaluation.correct() + evaluation.incorrect()
        correct += evaluation.correct() / count
        incorrect += evaluation.incorrect() / count

        System.println(evaluation.toSummaryString())

//        System.println('Correct: ' + evaluation.correct() / count)
//        System.println('Incorrect: ' + evaluation.incorrect() / count)
//        System.println('--------------------------------------')
    }

    public void completeEvaluation() {
        System.println('Correctly Classified: ' + correct / amount)
        System.println('Incorrectly Classified: ' + incorrect / amount)
        System.println('Amount of classified users: ' + amount)
        System.println('--------------------------------------')
    }
}
