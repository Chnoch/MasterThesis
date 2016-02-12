package ch.chnoch.mt.machinelearning.data.evaluation.evaluations

import ch.chnoch.mt.machinelearning.data.interfaces.IEvaluation
import weka.classifiers.Evaluation

/**
 * Created by Chnoch on 12.02.2016.
 */
public class DefaultEvaluation implements IEvaluation {

    @Override
    def startEvaluation(Evaluation evaluation) {
        System.println('Correct: ' + evaluation.correct())
        System.println('Incorrect: ' + evaluation.incorrect())
        System.println('Correctly Classified: ' + evaluation.correct() / (evaluation.correct() + evaluation.incorrect()))
        System.println('Incorrectly Classified: ' + evaluation.incorrect() / (evaluation.correct() + evaluation.incorrect()))
        System.println('--------------------------------------')
    }
}
