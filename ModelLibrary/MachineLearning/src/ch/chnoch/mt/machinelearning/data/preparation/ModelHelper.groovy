package ch.chnoch.mt.machinelearning.data.preparation

import ch.chnoch.mt.machinelearning.data.evaluation.ModelToWekaDataConverter

/**
 * Created by Chnoch on 02.12.2015.
 */
class ModelHelper {

    public static getCleanModel(folder) {
        def filename = folder + 'log_data_training_january_15.csv'
        def file = new File(filename)

        def csvToModelConverter = new CSVToModelConverter()
        def model = csvToModelConverter.convertCSVFile(file)

        def dataCleanup = new DataCleanup()
        dataCleanup.parseModel(model)
        return model
    }

    public static getCleanUpdatedModel(folder) {
        def filename = folder + 'stationboard_clean_nov_15.csv'
        def file = new File(filename)

        def updatedCsvToModelConverter = new UpdatedCSVToModelConverter()
        def model = updatedCsvToModelConverter.convertCSVFile(file)

        DataCleanup.parseModel(model)
        StatisticsPreparation.prepareStations(model)

        return model
    }

//    public static createWEKAFile(folder, model) {
//        def modelToWeka = new ModelToWekaDataConverter()
//        def arffFilename = folder + 'log_data_training_january_15.arff'
//        def arffFile = new File(arffFilename)
//        modelToWeka.saveToFile(model, arffFile)
//    }
}
