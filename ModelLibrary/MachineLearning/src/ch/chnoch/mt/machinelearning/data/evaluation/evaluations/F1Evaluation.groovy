package ch.chnoch.mt.machinelearning.data.evaluation.evaluations

import ch.chnoch.mt.machinelearning.data.interfaces.IEvaluation
import com.sun.java.util.jar.pack.ConstantPool.Index
import weka.classifiers.Evaluation

/**
 * Created by Chnoch on 18.07.2016.
 */
class F1Evaluation implements IEvaluation {

    private int classIndex;

    public F1Evaluation(int featureCount) {
        classIndex = featureCount - 1;
    }

    @Override
    public void evaluate(Evaluation evaluation) {
        try {

            double precision = evaluation.precision(classIndex);
            double recall = evaluation.recall(classIndex);
            double fMeasure = evaluation.fMeasure(classIndex);

            System.println('Precision: ' + precision);
            System.println('Recall: ' + recall)
            System.println('F1 Measure: ' + fMeasure)
            System.println('--------------------------------------')
        } catch (IndexOutOfBoundsException e) {
            println("ArrayIndexOutOfBoundsException")
        }
    }

    @Override
    public void completeEvaluation() {

    }
}
