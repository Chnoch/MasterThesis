package ch.chnoch.mt.machinelearning.data.evaluation.feature;

import ch.chnoch.mt.machinelearning.data.interfaces.IFeature
import ch.chnoch.mt.machinelearning.data.model.User;
import weka.core.Attribute;

/**
 * Created by Chnoch on 05.01.2016.
 */
public class CurrentStationFeature extends AbstractFeature {

    public CurrentStationFeature(User user) {
        def attr = new Attribute('stationId', user.getAvailableCurrentStations());
        super(attr, 'stationId');
    }
}
