package ch.chnoch.mt.machinelearning.data.evaluation.feature

import ch.chnoch.mt.machinelearning.data.model.User
import weka.core.Attribute

/**
 * Created by Chnoch on 05.01.2016.
 */
public class HourOfDayFeature extends AbstractFeature {

    public void instantiate(User user) {
        def hoursOfDay = (0..23).collect({ it.toString() })
        def attr = new Attribute('hourOfDay', hoursOfDay);
        super.instantiate(attr, 'hourOfDay');
    }
}
