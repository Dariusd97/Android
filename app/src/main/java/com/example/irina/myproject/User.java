package com.example.irina.myproject;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    private String username;
    private String email;
    private String anStudiu;
    private int grupa;
    private String serie;
    private String parola;
    private String origin;

    public User() {

    }

    public User(String username, String email, String anStudiu, int grupa, String serie) {
        this.username = username;
        this.email = email;
        this.anStudiu = anStudiu;
        this.grupa = grupa;
        this.serie = serie;
    }
    public User(String username, String email, String anStudiu, int grupa, String serie, String parola) {
        this.username = username;
        this.email = email;
        this.anStudiu = anStudiu;
        this.grupa = grupa;
        this.serie = serie;
        this.parola = parola;
    }

    public User(String username, String email, String anStudiu, int grupa, String serie, String parola, String origin) {
        this.username = username;
        this.email = email;
        this.anStudiu = anStudiu;
        this.grupa = grupa;
        this.serie = serie;
        this.parola = parola;
        this.origin = origin;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getAnStudiu() {
        return anStudiu;
    }

    public int getGrupa() {
        return grupa;
    }

    public String getSerie() {
        return serie;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAnStudiu(String anStudiu) {
        this.anStudiu = anStudiu;
    }

    public void setGrupa(int grupa) {
        this.grupa = grupa;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }
    @NonNull
    @Override
    public String toString() {
        return this.username;
    }
}
