package services;

import io.javalin.http.Context;
import modelpojos.User;

public interface UserService {
    public void create(String newUser, String pass, String fname, String lname);
    public User read(String email);

    public void update(String field, String match, String column, String value);

    //public void delete();
}
