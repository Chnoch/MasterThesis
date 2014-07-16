package webinterface

class MainController {

    def modelService

    def selectFile() {
        session.setAttribute("file", null)
        respond true
    }

    def selectUser() {
        def file = session.getAttribute("file")
        if (!file) {
            if (params.defaultFile) {
                file = grailsAttributes.getApplicationContext().
                        getResource("assets/learning_data.csv").getFile()
            } else {
                def f = request.getFile("data")

                file = new File('data')
                f.transferTo(file)
            }

            session.setAttribute("file", file)
        }

        def userMap = modelService.getUsersFromFile(file)
        [userMap: userMap]
    }

    def displayGraph() {
        def user = params.id
        if (!user) {
            user = session.getAttribute("user")
        } else {
            session.setAttribute("user", user)
        }
        def file = session.getAttribute("file")
        def model = modelService.createModel(file, user)
        session.setAttribute("model", model)
        def graph = model.getGraph()
        [model: model, graph: graph, user: user]
    }
}
