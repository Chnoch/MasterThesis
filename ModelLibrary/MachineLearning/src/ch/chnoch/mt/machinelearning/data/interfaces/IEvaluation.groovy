package ch.chnoch.mt.machinelearning.data.interfaces

import weka.classifiers.Evaluation

/**
 * Created by Chnoch on 12.02.2016.
 */
public interface IEvaluation {

    public void evaluate(Evaluation evaluation)

    public void completeEvaluation()
}
