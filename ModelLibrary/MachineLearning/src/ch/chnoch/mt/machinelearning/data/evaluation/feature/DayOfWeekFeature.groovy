package ch.chnoch.mt.machinelearning.data.evaluation.feature

import ch.chnoch.mt.machinelearning.data.model.User
import weka.core.Attribute

/**
 * Created by Chnoch on 05.01.2016.
 */
public class DayOfWeekFeature extends AbstractFeature {

    public void instantiate(User user) {
        def daysOfWeek = (0..7).collect({ it.toString() })

        def attr = new Attribute('dayOfWeek', daysOfWeek);
        super.instantiate(attr, 'dayOfWeek');
    }
}
