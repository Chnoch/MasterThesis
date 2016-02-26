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

    public void setUsers(List<User> users) {
        this.users = users
    }

    public List<User> getLowProfileUsers() {
        return lowProfileUsers
    }

    public void setLowProfileUsers(List<User> lowProfileUsers) {
        this.lowProfileUsers = lowProfileUsers
    }

    public List<User> getPreparedUsers() {
        return preparedUsers
    }

    public void setPreparedUsers(List<User> preparedUsers) {
        this.preparedUsers = preparedUsers
    }

    public void removeUsers(List<User> users) {
        this.preparedUsers.removeAll(users)
        this.lowProfileUsers.addAll(users)
    }
}
