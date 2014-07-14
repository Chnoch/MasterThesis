package webinterface

class MainController {

    def modelService

    def index() {
        respond true
    }

    def selectFile() {
        respond true
    }

    def selectUser() {
        def f = request.getFile('data')
        if (f.empty) {
            flash.message = 'file cannot be empty'
            render(view: 'uploadForm')
            return
        }

        session.setAttribute("file", f)

        def userList = modelService.getUsersFromFile(f)
        respond userList
    }

    def displayGraph() {
        def user = params.user
        def file = session.getAttribute("file")
        def model = modelService.createModel(file, user)
        session.setAttribute("model", model)
        respond true
    }
}
