package ch.chnoch.mt.machinelearning

import ch.chnoch.mt.machinelearning.data.preparation.BagConverter
import ch.chnoch.mt.machinelearning.data.preparation.CSVToModelConverter
import ch.chnoch.mt.machinelearning.data.preparation.DataCleanup
import ch.chnoch.mt.machinelearning.data.preparation.ModelToWekaDataConverter
import ch.chnoch.mt.machinelearning.data.preparation.UpdatedCSVToModelConverter
import ch.chnoch.mt.machinelearning.data.preparation.UserToWekaDataConverter
import ch.chnoch.mt.machinelearning.data.preparation.WekaInstances
import ch.chnoch.mt.machinelearning.data.training.DecisionTreeEvaluation

/**
 * Created by Chnoch on 27.02.2015.
 */


def createWEKAFile() {
    def model = getCleanModel()

    def modelToWeka = new ModelToWekaDataConverter()
    def arffFilename = 'D:\\Workspaces\\MasterThesis\\ModelLibrary\\assets\\log_data_training_january_15.arff'
    def arffFile = new File(arffFilename)
    modelToWeka.saveToFile(model, arffFile)
//modelToWeka.convertToWeka(model)
}

def createBags() {
    def model = getCleanModel()

    def bagConverter = new BagConverter()
//    def stats = bagConverter.createStatisticsOfData(model)
//    stats.each{println it + ';'}
    def users = bagConverter.createBagsForUser(model)
    def userToWeka = new UserToWekaDataConverter()
    def filepath = 'D:\\Workspaces\\MasterThesis\\ModelLibrary\\assets\\users\\'
    userToWeka.saveUsersToFile(users, filepath)
}

def createStations() {
    def model = getCleanUpdatedModel()
    def modelToWeka = new ModelToWekaDataConverter()
//    def arffFilename = 'D:\\Workspaces\\MasterThesis\\ModelLibrary\\assets\\log_data_training_january_15.arff'
    def filepath = 'D:\\Workspaces\\MasterThesis\\ModelLibrary\\assets\\users\\'

    modelToWeka.saveToFileForUsers(model, filepath)
    return model
}

def evaluateModel(model) {
//    def model = getCleanModel()
    def filepath = 'D:\\Workspaces\\MasterThesis\\ModelLibrary\\assets\\users\\'


    def correct = 0;
    def incorrect = 0;
    def users = model.getUsers()
    users.each { user ->
        def instances = WekaInstances.loadWekaInstances(user, filepath)
        if (instances) {
            instances.setClassIndex(instances.numAttributes() - 1)
            def decisionTree = new DecisionTreeEvaluation(instances)
            def evaluation = decisionTree.evaluateModel()
//            System.println('Correct: ' + evaluation.correct() + '; Incorrect:' + evaluation.incorrect())
            correct += evaluation.correct()
            incorrect += evaluation.incorrect()
        }
    }
    System.println('Correct: ' + correct)
    System.println('Incorrect: ' + incorrect)
    System.println('Correctly Classified: ' + correct / (correct + incorrect))
    System.println('Incorrectly Classified: ' + incorrect / (correct + incorrect))
}

def getCleanModel() {
    def filename = 'D:\\Workspaces\\MasterThesis\\ModelLibrary\\assets\\log_data_training_january_15.csv'
    def file = new File(filename)

    def csvToModelConverter = new CSVToModelConverter()
    def model = csvToModelConverter.convertCSVFile(file)

    def dataCleanup = new DataCleanup()
    dataCleanup.parseModel(model)
    return model
}

def getCleanUpdatedModel() {
    def filename = 'D:\\Workspaces\\MasterThesis\\ModelLibrary\\assets\\stationboard_clean_nov_15.csv'
    def file = new File(filename)

    def updatedCsvToModelConverter = new UpdatedCSVToModelConverter()
    def model = updatedCsvToModelConverter.convertCSVFile(file)

    def dataCleanup = new DataCleanup()
    dataCleanup.parseModel(model)
    return model

}

def model = createStations()
evaluateModel(model)
//createBags()
