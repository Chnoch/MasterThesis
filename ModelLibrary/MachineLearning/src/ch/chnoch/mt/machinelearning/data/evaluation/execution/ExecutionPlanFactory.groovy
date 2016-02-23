package ch.chnoch.mt.machinelearning.data.evaluation.execution

import ch.chnoch.mt.machinelearning.data.evaluation.classifiers.DecisionTreeClassifier
import ch.chnoch.mt.machinelearning.data.evaluation.classifiers.NaiveBayesClassifier
import ch.chnoch.mt.machinelearning.data.evaluation.classifiers.RandomForestClassifier
import ch.chnoch.mt.machinelearning.data.evaluation.evaluations.DefaultEvaluation
import ch.chnoch.mt.machinelearning.data.evaluation.feature.CurrentStationFeature
import ch.chnoch.mt.machinelearning.data.evaluation.feature.DayOfWeekFeature
import ch.chnoch.mt.machinelearning.data.evaluation.feature.HourOfDayFeature
import ch.chnoch.mt.machinelearning.data.evaluation.feature.MinuteOfHourFeature
import ch.chnoch.mt.machinelearning.data.evaluation.feature.NextStationFeature
import ch.chnoch.mt.machinelearning.data.evaluation.feature.PreviousStationFeature
import ch.chnoch.mt.machinelearning.data.evaluation.feature.WeekdayFeature
import ch.chnoch.mt.machinelearning.data.interfaces.IClassifier
import ch.chnoch.mt.machinelearning.data.interfaces.IExecutionPlan
import ch.chnoch.mt.machinelearning.data.interfaces.IFeature
import ch.chnoch.mt.machinelearning.data.model.Model

/**
 * Created by Chnoch on 12.02.2016.
 */
public class ExecutionPlanFactory {

    public static IExecutionPlan createFullFeatureSetExecutionPlan(Model model) {
        IExecutionPlan plan = new ExecutionPlan(model)

        plan.addFeature(new CurrentStationFeature())
//        plan.addFeature(new DayOfWeekFeature())
//        plan.addFeature(new HourOfDayFeature())
//        plan.addFeature(new MinuteOfHourFeature())
        plan.addFeature(new PreviousStationFeature())
//        plan.addFeature(new WeekdayFeature())
        plan.addFeature(new NextStationFeature())

        plan.setClassifier(new RandomForestClassifier())

        plan.setEvaluation(new DefaultEvaluation())

        return plan;
    }
}
