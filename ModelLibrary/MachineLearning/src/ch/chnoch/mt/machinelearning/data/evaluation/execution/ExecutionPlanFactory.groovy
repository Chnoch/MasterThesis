package ch.chnoch.mt.machinelearning.data.evaluation.execution

import ch.chnoch.mt.machinelearning.data.evaluation.classifiers.DecisionTreeClassifier
import ch.chnoch.mt.machinelearning.data.evaluation.classifiers.HMMClassifier
import ch.chnoch.mt.machinelearning.data.evaluation.classifiers.MultilayerPerceptronClassifier
import ch.chnoch.mt.machinelearning.data.evaluation.classifiers.NaiveBayesClassifier
import ch.chnoch.mt.machinelearning.data.evaluation.classifiers.RandomForestClassifier
import ch.chnoch.mt.machinelearning.data.evaluation.evaluations.F1Evaluation
import ch.chnoch.mt.machinelearning.data.evaluation.evaluations.F1FrequentVsNonFrequentEvaluation
import ch.chnoch.mt.machinelearning.data.evaluation.evaluations.SeparatePercentageEvaluation
import ch.chnoch.mt.machinelearning.data.evaluation.feature.CurrentStationFeature
import ch.chnoch.mt.machinelearning.data.evaluation.feature.DayOfWeekFeature
import ch.chnoch.mt.machinelearning.data.evaluation.feature.HourOfDayFeature
import ch.chnoch.mt.machinelearning.data.evaluation.feature.MinuteOfHourFeature
import ch.chnoch.mt.machinelearning.data.evaluation.feature.NextStationFeature
import ch.chnoch.mt.machinelearning.data.evaluation.feature.PreviousStationFeature
import ch.chnoch.mt.machinelearning.data.evaluation.feature.WeekdayFeature
import ch.chnoch.mt.machinelearning.data.interfaces.IEvaluation
import ch.chnoch.mt.machinelearning.data.interfaces.IExecutionPlan
import ch.chnoch.mt.machinelearning.data.model.Model

/**
 * Created by Chnoch on 12.02.2016.
 */
public class ExecutionPlanFactory {

    public static List<IExecutionPlan> createAllExecutionPlansFrequentVsNonFrequent(Model model) {
        List<IExecutionPlan> plans = new ArrayList<>()

        plans.addAll(createDecisionTreeExecutionPlanList(model))
        plans.addAll(createNaiveBayesExecutionPlanList(model))
        plans.addAll(createMultilayerPerceptronExecutionPlanList(model))

        return plans;
    }

    public static List<IExecutionPlan> createAllExecutionPlans(Model model) {
        List<IExecutionPlan> plans = new ArrayList<>()

        plans.addAll(createDecisionTreeExecutionPlanList(model))
        plans.addAll(createNaiveBayesExecutionPlanList(model))
        plans.addAll(createMultilayerPerceptronExecutionPlanList(model))

        return plans;
    }

    public static IExecutionPlan createRandomForestExecutionPlan(Model model) {
        IExecutionPlan plan = new ExecutionPlan(model, "RandomForest Default")

        addDefaultFeatures(plan)

        plan.setClassifier(new RandomForestClassifier())
        plan.setEvaluation(new F1Evaluation())

        return plan;
    }

    public static IExecutionPlan createDecisionTreeExecutionPlan(Model model) {
        IExecutionPlan plan = new ExecutionPlan(model, "DecisionTree Default")

        addDefaultFeatures(plan)

        plan.setClassifier(new DecisionTreeClassifier())
        plan.setEvaluation(new F1Evaluation())
        return plan
    }

    public static List<IExecutionPlan> createDecisionTreeExecutionPlanList(Model model) {
        List<IExecutionPlan> plans = getFullPlanList(model, "DecisionTree")

        for (IExecutionPlan plan : plans) {
            plan.setClassifier(new DecisionTreeClassifier())
            plan.setEvaluation(new F1Evaluation())
        }
        return plans
    }


    public static IExecutionPlan createMultilayerPerceptronExecutionPlan(Model model) {
        IExecutionPlan plan = new ExecutionPlan(model, "MultilayerPerceptron Default")

        addOnlyStationFeatures(plan)

        plan.setClassifier(new MultilayerPerceptronClassifier())
        plan.setEvaluation(new F1Evaluation())

        return plan
    }

    public static List<IExecutionPlan> createMultilayerPerceptronExecutionPlanList(Model model) {
        List<IExecutionPlan> plans = getFullPlanList(model, "MultilayerPerceptron")

        for (IExecutionPlan plan : plans) {
            plan.setClassifier(new MultilayerPerceptronClassifier())
            plan.setEvaluation(new F1Evaluation())
        }
        return plans
    }

    public static IExecutionPlan createHmmExecutionPlan(Model model) {
        IExecutionPlan plan = new ExecutionPlan(model, "HMM Default")

        addDefaultFeatures(plan)

        plan.setClassifier(new HMMClassifier())
        plan.setEvaluation(new F1Evaluation())

        return plan
    }


    public static IExecutionPlan createNaiveBayesExecutionPlan(Model model) {
        IExecutionPlan plan = new ExecutionPlan(model, "NaiveBayes Default")

        addDefaultFeatures(plan)

        plan.setClassifier(new NaiveBayesClassifier())
        plan.setEvaluation(new F1Evaluation())

        return plan
    }

    public static List<IExecutionPlan> createNaiveBayesExecutionPlanList(Model model) {
        List<IExecutionPlan> plans = getFullPlanList(model, "NaiveBayes")

        for (IExecutionPlan plan : plans) {
            plan.setClassifier(new NaiveBayesClassifier())
            plan.setEvaluation(new F1Evaluation())
        }
        return plans
    }


    private static void addDefaultFeatures(IExecutionPlan plan) {
        plan.addFeature(new CurrentStationFeature())
        plan.addFeature(new DayOfWeekFeature())
        plan.addFeature(new HourOfDayFeature())
        plan.addFeature(new MinuteOfHourFeature())
        plan.addFeature(new PreviousStationFeature())
        plan.addFeature(new WeekdayFeature())
        plan.addFeature(new NextStationFeature())
    }

    private static void addCurrentStationAndTimestampFeatures(IExecutionPlan plan) {
        plan.addFeature(new CurrentStationFeature())
        plan.addFeature(new DayOfWeekFeature())
        plan.addFeature(new HourOfDayFeature())
        plan.addFeature(new MinuteOfHourFeature())
//        plan.addFeature(new PreviousStationFeature())
        plan.addFeature(new WeekdayFeature())
        plan.addFeature(new NextStationFeature())
    }

    private static void addStationsAndDayAndHourFeatures(IExecutionPlan plan) {
        plan.addFeature(new CurrentStationFeature())
        plan.addFeature(new DayOfWeekFeature())
        plan.addFeature(new HourOfDayFeature())
//        plan.addFeature(new MinuteOfHourFeature())
        plan.addFeature(new PreviousStationFeature())
//        plan.addFeature(new WeekdayFeature())
        plan.addFeature(new NextStationFeature())
    }

    private static void addStationsAndDayFeatures(IExecutionPlan plan) {
        plan.addFeature(new CurrentStationFeature())
        plan.addFeature(new DayOfWeekFeature())
        plan.addFeature(new PreviousStationFeature())
        plan.addFeature(new WeekdayFeature())
        plan.addFeature(new NextStationFeature())
    }

    private static void addOnlyStationFeatures(IExecutionPlan plan) {
        plan.addFeature(new CurrentStationFeature())
        plan.addFeature(new PreviousStationFeature())
        plan.addFeature(new NextStationFeature())
    }

    private static List<IExecutionPlan> getFullPlanList(Model model, String title) {
        List<IExecutionPlan> plans = new ArrayList<>()

        IExecutionPlan plan = new ExecutionPlan(model, title + " only stations")
        addOnlyStationFeatures(plan)
        plans.add(plan)
//        plan = new ExecutionPlan(model, title + " stations and day")
//        addStationsAndDayFeatures(plan)
//        plans.add(plan)
        plan = new ExecutionPlan(model, title + " current station and timestamp")
        addCurrentStationAndTimestampFeatures(plan)
        plans.add(plan)
//        plan = new ExecutionPlan(model, title + " stations and day and hour")
//        addStationsAndDayAndHourFeatures(plan)
//        plans.add(plan)
        plan = new ExecutionPlan(model, title + " all features")
        addDefaultFeatures(plan)
        plans.add(plan)

        return plans;
    }

}
