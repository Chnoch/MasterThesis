package ch.chnoch.mt.machinelearning.data.evaluation.feature;

import ch.chnoch.mt.machinelearning.data.interfaces.IFeature
import ch.chnoch.mt.machinelearning.data.model.ModelEntry;
import weka.core.Attribute
import weka.core.Instance;

/**
 * Created by Chnoch on 05.01.2016.
 */
public abstract class AbstractFeature implements IFeature {

    protected Attribute attribute;
    protected String property;

    public AbstractFeature(){};

    protected AbstractFeature(Attribute attribute, String property) {
        this.attribute = attribute;
        this.property = property
    }

    public void setValue(ModelEntry entry, Instance instance) {
        instance.setValue(this.attribute, entry.getElement(this.property));
    }
}
