package webinterface

class StationController {

    def stationService

    def selectFile() {
        []
    }

    def updateStations() {
        def f = request.getFile("data")

        def file = new File('data')
        f.transferTo(file)

        stationService.initializeStations(file)
        [stationList: Station.list()]
    }

}
