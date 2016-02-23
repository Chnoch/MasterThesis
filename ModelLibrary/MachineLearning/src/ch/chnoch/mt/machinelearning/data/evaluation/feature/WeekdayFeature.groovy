package ch.chnoch.mt.machinelearning.data.evaluation.feature

import ch.chnoch.mt.machinelearning.data.model.User
import weka.core.Attribute

/**
 * Created by Chnoch on 05.01.2016.
 */
public class WeekdayFeature extends AbstractFeature {

    public void instantiate(User user) {
        def weekdayVector = new ArrayList()
        weekdayVector.add("true");
        weekdayVector.add("false");

        def attr = new Attribute('weekday', weekdayVector);
        super.instantiate(attr, 'weekday');
    }
}
