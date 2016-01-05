package ch.chnoch.mt.machinelearning.data.evaluation.feature;

import ch.chnoch.mt.machinelearning.data.interfaces.IFeature;
import weka.core.Attribute;

/**
 * Created by Chnoch on 05.01.2016.
 */
public class CurrentStationFeature extends AbstractStationFeature {

    public CurrentStationFeature(entries) {
        def attr = new Attribute('stationId', getStationIds(entries));
        super(attr, 'stationId');
    }
}
