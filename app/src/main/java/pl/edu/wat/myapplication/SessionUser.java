package pl.edu.wat.myapplication;

import android.provider.Settings;

public class SessionUser {
    private User user;
    private static SessionUser   objUser;
    private SessionUser(){



    }
    public static SessionUser getInstance()
    {
        if (objUser == null)
        {
            objUser = new SessionUser();
        }
        return objUser;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
