package ch.chnoch.mt.baselinemodel

/**
 * Created by Chnoch on 16.07.2014.
 */
class DataTesting {

    def model
    def testingSet

    public DataTesting(model, testingSet) {
        this.model = model
        this.testingSet = testingSet
    }

    def startTesting() {
        def count = 0.0f, correct = 0.0f, none = 0, wrong = 0
        testingSet.eachWithIndex { entry, i ->
            if (i <= testingSet.size() - 1) {
                def current = entry
                def next = testingSet[i + 1]
                def suggestion = model.getSuggestion(current.stationId)
                if (!suggestion) {
                    // no suggestion
                    none++
                } else if (suggestion == next?.stationId) {
                    // correct suggestion
                    correct++
                } else {
                    // wrong suggestion
                    wrong++
                }
                count++
            }
        }

        return [count, correct, wrong, none]
    }
}
