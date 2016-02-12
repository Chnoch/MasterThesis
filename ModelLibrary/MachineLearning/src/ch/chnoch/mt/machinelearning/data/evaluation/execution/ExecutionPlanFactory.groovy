package ch.chnoch.mt.machinelearning.data.evaluation.execution

import ch.chnoch.mt.machinelearning.data.evaluation.feature.CurrentStationFeature
import ch.chnoch.mt.machinelearning.data.interfaces.IExecutionPlan
import ch.chnoch.mt.machinelearning.data.interfaces.IFeature

/**
 * Created by Chnoch on 12.02.2016.
 */
public static class ExecutionPlanFactory {

    public static IExecutionPlan createFullFeatureSetExecutionPlan() {
        List<IFeature> features = new ArrayList<>()
        features.add(new CurrentStationFeature())
        IExecutionPlan plan = new ExecutionPlan()
    }
}
