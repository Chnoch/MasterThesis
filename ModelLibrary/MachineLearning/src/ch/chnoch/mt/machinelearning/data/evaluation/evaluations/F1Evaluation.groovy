package ch.chnoch.mt.machinelearning.data.evaluation.evaluations

import ch.chnoch.mt.machinelearning.data.interfaces.IEvaluation
import com.sun.java.util.jar.pack.ConstantPool.Index
import sun.security.x509.EDIPartyName
import weka.classifiers.Evaluation

import java.text.DecimalFormat

/**
 * Created by Chnoch on 18.07.2016.
 */
public class F1Evaluation extends AbstractEvaluation {

    private List<F1EvaluationData> dataList = new ArrayList<>()

    @Override
    public void evaluate(Evaluation evaluation) {
        F1EvaluationData data = new F1EvaluationData()
        data.precision = evaluation.weightedPrecision()
        data.recall = evaluation.weightedRecall()
        data.fMeasure = evaluation.weightedFMeasure()

        dataList.push(data)
    }

    @Override
    public void completeEvaluation() {
        dataList = dataList.sort { -it.fMeasure }
        DecimalFormat df = new DecimalFormat("0.000")

        for (F1EvaluationData data : dataList) {
            print(df.format(data.precision) + '\t')
            print(df.format(data.recall) + '\t')
            print(df.format(data.fMeasure) + '\n')
        }
        println()
    }
}
