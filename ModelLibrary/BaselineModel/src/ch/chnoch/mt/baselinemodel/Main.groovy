package ch.chnoch.mt.baselinemodel

import ch.chnoch.mt.dataparsing.Data

/**
 * Created by Chnoch on 11.07.2014.
 */

def completeSet = Data.cleanedDataSet
def userIdList = completeSet.collect { it.userId }
def userIds = new HashSet()
userIds.addAll(userIdList)
def count = 0.0f, sum = 0.0f, sumCorrect = 0, sumWrong = 0, sumNone = 0
userIds.each { userId ->
    def trainingSet = Data.getCleanedTrainingDataSetForUser(userId)
    def testingSet = Data.getCleanedTestingDataSetForUser(userId)
    def training = new DataTraining(userId, trainingSet)
    def model = training.createModel()
//    if (testingSet.size() > 1) {
    def testing = new DataTesting(model, testingSet)

    def countSuggestion, correct, wrong, none
    (countSuggestion, correct, wrong, none) = testing.startTesting()
    def percentage = correct.toFloat() / (countSuggestion - none).toFloat()
    if (correct) sumCorrect += correct
    if (wrong) sumWrong += wrong
    if (none) sumNone += none
    if (!percentage?.isNaN() && percentage > 0) {
        println 'User ' + userId + ' = ' + percentage * 100 + '%'
        count ++
        sum += percentage * 100
    }
//    }
}


println 'Complete Dataset: ' + sum / (count).toFloat() + '%'
println 'Complete Count: ' + count + ' users'
println 'Correct suggestion occured for ' + (int) sumCorrect + ' entries'
println 'Wrong suggestion occured for ' + (int) sumWrong + ' entries'
println 'No suggestions occured for ' + sumNone + ' entries'

//def userId = '568600737' // 7 stations
//def userId = '1984735043' // 2 stations
//def userId = '608764717' // 20 stations
//model.printGraph()
