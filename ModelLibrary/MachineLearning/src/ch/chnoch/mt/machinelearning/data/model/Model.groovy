package ch.chnoch.mt.machinelearning.data.model

/**
 * Created by Chnoch on 27.02.2015.
 */
class Model {
    private model

    public Model() {
        model = new ArrayList()
    }

    public addEntry(entry) {
        model.add(entry)
    }

    public getAllEntries() {
        return model;
    }

    public setEntries(entries) {
        model = new ArrayList()
        model.addAll(entries)
    }

    public getEntriesForUser(userId) {
        return model.findAll { it ->
            it.userId == userId
        }
    }

    public getEntriesForStation(stationId) {
        return model.findAll { it ->
            it.stationId == stationId
        }
    }

    public getUsers() {
        def users = []
        model.each { it ->
            if (!users.contains(it.userId)) {
                users.add(it.userId)
            }
        }
        return users
    }
}
