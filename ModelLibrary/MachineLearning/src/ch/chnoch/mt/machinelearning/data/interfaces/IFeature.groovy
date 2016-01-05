package ch.chnoch.mt.machinelearning.data.interfaces

import ch.chnoch.mt.machinelearning.data.model.ModelEntry
import weka.core.Instance;

/**
 * Created by Chnoch on 05.01.2016.
 */
public interface IFeature {

    void setValue(ModelEntry entry, Instance instance);
}
