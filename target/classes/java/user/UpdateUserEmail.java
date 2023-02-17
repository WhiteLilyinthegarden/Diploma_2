package user;

public class UpdateUserEmail extends User {
    //private String email;
    //public UpdateUserEmail(String email) {
    //    this.email = email;
   // }

    public String getEmail() {
        return email;
    }
    public User setEmail(String email) {
        this.email = email;
        return this;
    }
}
