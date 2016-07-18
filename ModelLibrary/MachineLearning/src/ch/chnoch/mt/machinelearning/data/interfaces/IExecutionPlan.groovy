package ch.chnoch.mt.machinelearning.data.interfaces

import ch.chnoch.mt.machinelearning.data.model.Model
import weka.core.Instances

/**
 * Created by Chnoch on 12.02.2016.
 */
public interface IExecutionPlan {

    public void setClassifier(IClassifier classifier);

    public void setEvaluation(IEvaluation evaluation);

    public void startExecutionAndEvaluation();

    public void addFeature(IFeature feature);

    public int getFeatureCount();
}
