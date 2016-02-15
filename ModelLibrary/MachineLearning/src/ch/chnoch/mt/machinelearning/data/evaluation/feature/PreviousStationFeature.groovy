package ch.chnoch.mt.machinelearning.data.evaluation.feature

import ch.chnoch.mt.machinelearning.data.model.User
import weka.core.Attribute

/**
 * Created by Chnoch on 05.01.2016.
 */
public class PreviousStationFeature extends AbstractFeature {

    public PreviousStationFeature(User user) {
        def attr = new Attribute('previousStationId', user.getAvailablePreviousStations());
        super(attr, 'previousStationId');
    }
}
