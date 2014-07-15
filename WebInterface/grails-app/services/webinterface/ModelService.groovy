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
        def userIds = users.collect {k,v -> k}

        return userIds
    }

    private sortByImportance(dataEntries) {
        def stopMap = [:]

        dataEntries.each { it ->
            if (stopMap.containsKey(it.userId)) {
                stopMap[it.userId] += 1
            } else {
                stopMap[it.userId] = 1
            }
        }

        stopMap.sort { a, b ->
            a.value <=> b.value
        }
        return stopMap
    }
}
