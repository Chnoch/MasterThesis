package ch.chnoch.mt.machinelearning.data.evaluation.feature

import weka.core.Attribute

/**
 * Created by Chnoch on 05.01.2016.
 */
public class WeekdayFeature extends AbstractFeature {

    public WeekdayFeature() {
        def weekdayVector = new ArrayList()
        weekdayVector.add("true");
        weekdayVector.add("false");

        def attr = new Attribute('weekday', weekdayVector);
        super(attr, 'weekday');
    }
}
