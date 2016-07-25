package ch.chnoch.mt.machinelearning.data.evaluation.evaluations

import ch.chnoch.mt.machinelearning.data.interfaces.IEvaluation
import weka.classifiers.Evaluation

/**
 * Created by Chnoch on 12.02.2016.
 */
public class DefaultEvaluation extends AbstractEvaluation {

    private long amount = 0L
    private long count = 0L
    private long correct = 0L
    private long incorrect = 0L
    private double precision = 0
    private double recall = 0

    @Override
    public void evaluate(Evaluation evaluation) {
        amount++;
        count += evaluation.correct() + evaluation.incorrect()
        correct += evaluation.correct()
        incorrect += evaluation.incorrect()
        precision += evaluation.precision(0)
        recall += evaluation.recall(0)

        System.println('Correct: ' + evaluation.correct())
        System.println('Incorrect: ' + evaluation.incorrect())
        System.println('--------------------------------------')
    }

    public void completeEvaluation() {
        System.println('Count: ' + count)
        System.println('Correct: ' + correct)
        System.println('Incorrect: ' + incorrect)
        System.println('Correctly Classified: ' + correct / count)
        System.println('Incorrectly Classified: ' + incorrect / count)
        System.println('Average precision: ' + precision / amount)
        System.println('Average recall: ' + recall / amount)
        System.println('--------------------------------------')
    }
}
