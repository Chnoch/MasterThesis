package webinterface

import grails.transaction.Transactional
import ch.chnoch.mt.dataparsing.*
import ch.chnoch.mt.baselinemodel.*

@Transactional
class ModelService {

    def createModel(file, user) {
        def dataEntries = Data.getFullCleanedDataSetForUserFromFile(file, user)
        def dataTraining = new DataTraining(user, dataEntries)
        return dataTraining.createModel()
    }

    def getUsersFromFile(file) {
        def users = sortByImportance(Data.getDataSetFromFile(file, true))
        return users
    }

    private sortByImportance(dataEntries) {
        def userMap = [:]

        dataEntries.each { it ->
            if (userMap.containsKey(it.userId)) {
                userMap[it.userId] += 1
            } else {
                userMap[it.userId] = 1
            }
        }

        userMap = userMap.sort { -it.value }
        return userMap
    }
}
