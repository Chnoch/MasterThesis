/**
 * Created by Chnoch on 19.06.2014.
 */

def main() {
    def data = new Data()
    def dataEntries = data.readFile('D:\\Workspaces\\MasterThesis\\TrajectoryExtraction\\assets\\learning_data.csv')
    dataEntries.sort { a, b ->
        a.timestampStart <=> b.timestampStart
        a.userId <=> b.userId
    }

    DataAnalysis.startDataAnalysis(dataEntries)



//    .each {
//        println it.userId + ', ' + it.timestampStart + ', ' + it.stopId
//    }
}


main()