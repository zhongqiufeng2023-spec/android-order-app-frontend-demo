package com.example.myapplication.data.login;

import com.example.myapplication.data.Result;
import com.example.myapplication.data.model.LoggedInUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {

        try {
            if (username.equals("admin") && password.equals("123456")) {
                LoggedInUser user = new LoggedInUser(
                        java.util.UUID.randomUUID().toString(),
                        "admin");
                return new Result.Success<>(user);
            } else if (username.equals("test") && password.equals("111111")) {
                LoggedInUser user = new LoggedInUser(
                        java.util.UUID.randomUUID().toString(),
                        "test");
                return new Result.Success<>(user);
            } else {

                return new Result.Error(new IOException("Wrong password or username"));
            }
        } catch (Exception e) {
            return new Result.Error(new IOException("Login failed", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}