package com.google.appengine.api.users;

import java.util.Set;

public class MockUserService implements UserService {

    public String createLoginURL(String arg0) {
        return null;
    }

    public String createLogoutURL(String arg0) {
        return null;
    }

    public User getCurrentUser() {
        return null;
    }

    public boolean isUserAdmin() {
        return false;
    }

    public boolean isUserLoggedIn() {
        return false;
    }

    public String createLoginURL(String arg0, String arg1) {
        return null;
    }

    public String createLogoutURL(String arg0, String arg1) {
        return null;
    }

    @Override
    public String createLoginURL(String arg0, String arg1, String arg2, Set<String> arg3) {
        return null;
    }

}
