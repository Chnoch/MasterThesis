package data.model

/**
 * Created by Chnoch on 21.03.2015.
 */
class User {
    String userId
    List<Bag> bags

    public User(userId) {
        this.userId = userId
        this.bags = new ArrayList<Bag>()
    }

    public void addBag(Bag bag) {
        this.bags.add(bag)
    }

    public void setBags(bags) {
        this.bags = bags;
    }
}
