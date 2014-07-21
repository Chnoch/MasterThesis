package webinterface

import grails.transaction.Transactional
import ch.chnoch.mt.dataparsing.*
import ch.chnoch.mt.baselinemodel.*

@Transactional
class ModelService {

    def createModel(file, user) {
        def dataEntries = Data.getDataSetFromFile(file, true)
        def dataInit = new DataInitialization(dataEntries)
        return dataInit.createModel(user)
    }

    def getUsersFromFile(file) {
        def users = sortByImportance(Data.getDataSetFromFile(file, true))
//        def userIds = users.collect {k,v -> k}

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
