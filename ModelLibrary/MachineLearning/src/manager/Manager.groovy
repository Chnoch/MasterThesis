package manager

import data.preparation.CSVToModelConverter
import data.preparation.DataCleanup
import data.preparation.ModelToWekaDataConverter

/**
 * Created by Chnoch on 27.02.2015.
 */


def filename = 'D:\\Workspaces\\MasterThesis\\ModelLibrary\\assets\\log_data_training_january_15.csv'
def file = new File(filename)

def csvToModelConverter = new CSVToModelConverter()
def model = csvToModelConverter.convertCSVFile(file)

def dataCleanup =  new DataCleanup()
dataCleanup.parseModel(model)

def modelToWeka = new ModelToWekaDataConverter()
def arffFilename = 'D:\\Workspaces\\MasterThesis\\ModelLibrary\\assets\\log_data_training_january_15.arff'
def arffFile = new File(arffFilename)
modelToWeka.saveToFile(model, arffFile)
//modelToWeka.convertToWeka(model)
