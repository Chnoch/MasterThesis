package ch.chnoch.mt.machinelearning.data.model

import com.sun.javafx.sg.prism.NGShape

/**
 * Created by Chnoch on 27.02.2015.
 */
public class Model {
    private List<User> users
    private List<User> lowProfileUsers;
    private List<User> preparedUsers;


    public Model() {
        users = new ArrayList<>()
    }

    public void addEntry(ModelEntry entry) {
        User user = users.find { it.getUserId() == entry.getUserId() };
        if (user != null) {
            user.addEntry(entry)
        } else {
            user = new User(entry.getUserId())
            user.addEntry(entry)
            users.add(user)
        }
    }

    public List<User> getUsers() {
        return users;
    }

    void setUsers(List<User> users) {
        this.users = users
    }

    List<User> getLowProfileUsers() {
        return lowProfileUsers
    }

    void setLowProfileUsers(List<User> lowProfileUsers) {
        this.lowProfileUsers = lowProfileUsers
    }

    List<User> getPreparedUsers() {
        return preparedUsers
    }

    void setPreparedUsers(List<User> preparedUsers) {
        this.preparedUsers = preparedUsers
    }
}
