package ch.chnoch.mt.webinterface

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
        redirect(action: 'showStations')
    }

    def showStations() {
        [stationList: Station.list()]
    }

}
