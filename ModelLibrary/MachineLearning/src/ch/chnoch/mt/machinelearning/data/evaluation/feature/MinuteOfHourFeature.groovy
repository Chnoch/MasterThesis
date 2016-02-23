package ch.chnoch.mt.machinelearning.data.evaluation.feature

import weka.core.Attribute

/**
 * Created by Chnoch on 05.01.2016.
 */
public class MinuteOfHourFeature extends AbstractFeature {

    public MinuteOfHourFeature() {
        def minutesOfHour = (0..59).collect({ it.toString() })

        def attr = new Attribute('minuteOfHour', minutesOfHour);
        super(attr, 'minuteOfHour');
    }
}
