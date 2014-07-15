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
        def f = request.getFile("data")
        if (f.empty) {
            flash.message = 'file cannot be empty'
            render(view: 'uploadForm')
            return
        }

        def file = new File('data')
        f.transferTo(file)
        session.setAttribute("file", file)

        def userList = modelService.getUsersFromFile(file)
        [userList: userList]
    }

    def displayGraph() {
        def user = params.user
        if (!user) {
            user = session.getAttribute("user")
        } else {
            session.setAttribute("user", user)
        }
        def file = session.getAttribute("file")
        def model = modelService.createModel(file, user)
        session.setAttribute("model", model)
        def graph = model.getGraph()
        [model: model, graph: graph]
    }
}
