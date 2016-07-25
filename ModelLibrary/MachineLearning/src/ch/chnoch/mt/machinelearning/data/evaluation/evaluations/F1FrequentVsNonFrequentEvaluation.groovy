package ch.chnoch.mt.machinelearning.data.evaluation.evaluations

import ch.chnoch.mt.machinelearning.data.interfaces.IEvaluation
import ch.chnoch.mt.machinelearning.data.model.User
import weka.classifiers.Evaluation

import java.text.DecimalFormat

/**
 * Created by Chnoch on 18.07.2016.
 */
public class F1FrequentVsNonFrequentEvaluation extends AbstractEvaluation {

    private Map<User, F1EvaluationData> dataMap = new LinkedHashMap<>()

    @Override
    public void evaluateUser(Evaluation evaluation, User user) {
        F1EvaluationData data = new F1EvaluationData()
        data.precision = evaluation.weightedPrecision()
        data.recall = evaluation.weightedRecall()
        data.fMeasure = evaluation.weightedFMeasure()

        dataMap.put(user, data)
    }

//    @Override
//    public void completeEvaluation() {
//        dataMap.sort { -it.key.preparedEntries.size() }
//        DecimalFormat df = new DecimalFormat("0.000")
//
//        List<F1EvaluationData> dataList = dataMap.values().asList();
//        for (List<F1EvaluationData> list : dataList.collate(((dataList.size() / 2) + 1).toInteger())) {
//            list = list.sort { -it.fMeasure }
//
//            for (F1EvaluationData data : list) {
//                print(df.format(data.precision) + '\t')
//                print(df.format(data.recall) + '\t')
//                print(df.format(data.fMeasure) + '\n')
//            }
//            println('--------------split--------------')
//        }
//        println()
//    }

    @Override
    public void completeEvaluation() {
        dataMap = dataMap.sort { -it.key.preparedEntries.size() }
        DecimalFormat df = new DecimalFormat("0.000")

        List<Map.Entry<User, F1EvaluationData>> dataList = dataMap.entrySet().asList();

        for (List<Map.Entry<User, F1EvaluationData>> list : dataList.collate(((dataList.size() / 2) + 1).toInteger())) {
            list = list.sort { -it.value.fMeasure }

            for (Map.Entry<User, F1EvaluationData> data : list) {
                print(df.format(data.value.precision) + '\t')
                print(df.format(data.value.recall) + '\t')
                print(df.format(data.value.fMeasure) + '\t')
                print(data.key.preparedEntries.size() + '\n')
            }
            println('--------------split--------------')
        }
        println()
    }

}
