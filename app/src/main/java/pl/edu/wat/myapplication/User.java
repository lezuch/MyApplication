package pl.edu.wat.myapplication;

import android.media.Image;

import io.realm.RealmObject;

/**
 * Created by student on 10.07.15.
 */
public class User extends RealmObject {
    private String name;
    private String surname;
    private String password;
    private String login;
    private String urlAwatara;



    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getSurname() {
        return surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getUrlAwatara() {
        return urlAwatara;
    }

    public void setUrlAwatara(String urlAwatara) {
        this.urlAwatara = urlAwatara;
    }
}
