package ch.chnoch.mt.machinelearning.data.evaluation.feature

import weka.core.Attribute

/**
 * Created by Chnoch on 05.01.2016.
 */
public class PreviousStationFeature extends AbstractStationFeature {

    public PreviousStationFeature(entries) {
        def attr = new Attribute('previousStationId', getStationIds(entries));
        super(attr, 'previousStationId');
    }
}
