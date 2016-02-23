package ch.chnoch.mt.machinelearning.data.interfaces

import ch.chnoch.mt.machinelearning.data.model.ModelEntry
import ch.chnoch.mt.machinelearning.data.model.User
import weka.core.Attribute
import weka.core.Instance;

/**
 * Created by Chnoch on 05.01.2016.
 */
public interface IFeature {

    public void setValue(ModelEntry entry, Instance instance);

    public void instantiate(User user);

    public Attribute getAttribute()

    public String getProperty()
}
