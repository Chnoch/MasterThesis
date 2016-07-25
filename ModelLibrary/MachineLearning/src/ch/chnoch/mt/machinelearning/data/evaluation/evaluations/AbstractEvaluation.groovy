package ch.chnoch.mt.machinelearning.data.evaluation.evaluations

import ch.chnoch.mt.machinelearning.data.interfaces.IEvaluation
import ch.chnoch.mt.machinelearning.data.model.User
import weka.classifiers.Evaluation

/**
 * Created by Chnoch on 25.07.2016.
 */
public class AbstractEvaluation implements IEvaluation {

    @Override
    void evaluateUser(Evaluation evaluation, User user) {}

    @Override
    void evaluate(Evaluation evaluation) {}

    @Override
    void completeEvaluation() {}
}
