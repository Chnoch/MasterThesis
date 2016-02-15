package ch.chnoch.mt.machinelearning.data.preparation

import ch.chnoch.mt.machinelearning.data.interfaces.IConverter
import ch.chnoch.mt.machinelearning.data.model.Model
import ch.chnoch.mt.machinelearning.data.preparation.converters.CSVToModelConverter
import ch.chnoch.mt.machinelearning.data.preparation.converters.UpdatedCSVToModelConverter

/**
 * Created by Chnoch on 02.12.2015.
 */
class ModelHelper {

    public static Model getCleanModel(folder) {
        String filename = folder + 'log_data_training_january_15.csv'
        def file = new File(filename)

        def csvToModelConverter = new CSVToModelConverter()
        def model = csvToModelConverter.convert(file)

        def dataCleanup = new DataCleanup()
        model = dataCleanup.parseModel(model)
        return model
    }

    public static Model getCleanUpdatedModel(folder) {
        String filename = folder + 'stationboard_clean_nov_15.csv'
        def file = new File(filename)

        IConverter converter = new UpdatedCSVToModelConverter()
        def model = converter.convert(file)

        DataCleanup.parseModel(model)
        StatisticsPreparation.prepareStations(model)

        return model
    }
}
