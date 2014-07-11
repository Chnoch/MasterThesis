package ch.chnoch.mt.baselinemodel

import ch.chnoch.mt.dataparsing.Data

/**
 * Created by Chnoch on 11.07.2014.
 */
def dataInit = new DataInitialization(Data.cleanedDataSet)

def userId = '568600737' // 7 stations
//def userId = '1984735043' // 2 stations
//def userId = '608764717' // 20 stations
dataInit.start(userId)