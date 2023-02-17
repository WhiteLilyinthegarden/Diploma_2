package user;

import java.util.UUID;

public class User {
    protected String email = "try78@mail.ru";
    protected String name = "lily";
    protected String password = "P@ssword";


    public User generateUserEmail(){
        setEmail(UUID.randomUUID().toString() + "@yandex.ru");
        return this;
    }


    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }
    public User setName(String name) {
        this.name = name;
        return this;
    }
    public String getName(){
        return this.name;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }
}
