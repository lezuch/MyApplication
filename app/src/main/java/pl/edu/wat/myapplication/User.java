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
    private String male;
    private int wiek;
    private float waga;
    private float wzrost;




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

    public String getMale() {
        return male;
    }

    public void setMale(String male) {
        this.male = male;
    }

    public int getWiek() {
        return wiek;
    }

    public float getWaga() {
        return waga;
    }

    public float getWzrost() {
        return wzrost;
    }

    public void setWiek(int wiek) {
        this.wiek = wiek;
    }

    public void setWaga(float waga) {
        this.waga = waga;
    }

    public void setWzrost(float wzrost) {
        this.wzrost = wzrost;
    }
}
