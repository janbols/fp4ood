package be.guterfluss.user;

import java.util.function.BiFunction;

public class User {

    Password password;
    Email email;

    public User(Password password, Email email) {
        this.password = password;
        this.email = email;
    }

    public User(String password, String email) {
        // ...
    }


    User createUser(String email, String password){
        return new User(email, password);
    }


    private boolean invalidPassword() {
        return !password.contains(email);
    }

    private boolean invalidPassword(
            Password password,
            Email email) {
        return !password.contains(email);
    }


    static class Utils {

        static boolean invalidPassword(
                Password password,
                Email email) {
            return !password.contains(email);
        }

        static BiFunction<Password, Email, Boolean>
                checksInvalidPassword =
                (password, email) -> !password.contains(email);


    }
}
