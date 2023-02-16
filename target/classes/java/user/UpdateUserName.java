package user;

public class UpdateUserName extends User {
    //private String name;

    public UpdateUserName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }
}