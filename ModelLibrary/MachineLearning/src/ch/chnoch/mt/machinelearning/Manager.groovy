package ch.chnoch.mt.machinelearning

import ch.chnoch.mt.machinelearning.data.evaluation.execution.ExecutionPlan
import ch.chnoch.mt.machinelearning.data.evaluation.execution.ExecutionPlanFactory
import ch.chnoch.mt.machinelearning.data.model.Model
import ch.chnoch.mt.machinelearning.data.preparation.ModelHelper
import ch.chnoch.mt.machinelearning.data.evaluation.ModelEvaluation

/**
 * Created by Chnoch on 27.02.2015.
 */


def folder = 'D:\\Personal\\Workspaces\\MasterThesis\\MasterThesis\\ModelLibrary\\assets\\'

/*
 * PREPARATION
 */
Model model = ModelHelper.getCleanUpdatedModel(folder)

/*
 * EXECUTION & EVALUATION
 */
ExecutionPlan plan = ExecutionPlanFactory.createFullFeatureSetExecutionPlan(model);
plan.startExecutionAndEvaluation()

//ModelToWekaDataConverter.saveToFileForUsers(model, userFolder)

//def modelEvaluation = new ModelEvaluation(model)
//modelEvaluation.evaluateCompleteModel()
//modelEvaluation.evaluateModelForUser()
