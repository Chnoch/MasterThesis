package ch.chnoch.mt.machinelearning

import ch.chnoch.mt.machinelearning.data.evaluation.execution.ExecutionPlan
import ch.chnoch.mt.machinelearning.data.evaluation.execution.ExecutionPlanFactory
import ch.chnoch.mt.machinelearning.data.interfaces.IExecutionPlan
import ch.chnoch.mt.machinelearning.data.model.Model
import ch.chnoch.mt.machinelearning.data.preparation.ModelHelper
import ch.chnoch.mt.machinelearning.data.evaluation.ModelEvaluation

/**
 * Created by Chnoch on 27.02.2015.
 */


def folder = 'D:\\Workspaces\\MasterThesis\\ModelLibrary\\assets\\'

/*
 * PREPARATION
 */
Model model = ModelHelper.getCleanUpdatedModel(folder)

/*
 * EXECUTION & EVALUATION
 */
//ExecutionPlan plan = ExecutionPlanFactory.createFullFeatureSetExecutionPlan(model);
//plan.startExecutionAndEvaluation()

//ExecutionPlan dtPlan = ExecutionPlanFactory.createDecisionTreeExecutionPlan(model)
//dtPlan.startExecutionAndEvaluation()

//IExecutionPlan multilayerPlan = ExecutionPlanFactory.createMultilayerPerceptronExecutionPlan(model)
//multilayerPlan.startExecutionAndEvaluation()

//ExecutionPlan hmmPlan = ExecutionPlanFactory.createHmmExecutionPlan(model)
//multilayerPlan.startExecutionAndEvaluation()

//ExecutionPlan naiveBayesPlan = ExecutionPlanFactory.createNaiveBayesExecutionPlan(model)
//naiveBayesPlan.startExecutionAndEvaluation()

List<IExecutionPlan> plans = ExecutionPlanFactory.createAllExecutionPlans(model)
//List<IExecutionPlan> plans = ExecutionPlanFactory.createAllExecutionPlansFrequentVsNonFrequent(model)
printf("Starting Execution and Evaluation of %d plans.\n", plans.size())
for (IExecutionPlan plan : plans) {
    plan.startExecutionAndEvaluation()
}
