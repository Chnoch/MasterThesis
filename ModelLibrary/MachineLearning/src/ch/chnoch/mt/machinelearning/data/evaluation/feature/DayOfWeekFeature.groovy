package ch.chnoch.mt.machinelearning.data.evaluation.feature

import weka.core.Attribute

/**
 * Created by Chnoch on 05.01.2016.
 */
public class DayOfWeekFeature extends AbstractFeature {

    public DayOfWeekFeature() {
        def daysOfWeek = (0..7).collect({ it.toString() })

        def attr = new Attribute('dayOfWeek', daysOfWeek);
        super(attr, 'dayOfWeek');
    }
}
