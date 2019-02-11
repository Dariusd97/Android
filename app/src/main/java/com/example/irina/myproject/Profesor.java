package com.example.irina.myproject;

import java.io.Serializable;
import java.util.ArrayList;

import androidx.annotation.NonNull;

public class Profesor implements Serializable {

    private String username;
    private String email;
    private ArrayList<Materie> materii;
    private String origine;
    private String parola;
    public Profesor(){

    }

    public Profesor(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public Profesor(String username, String email, ArrayList<Materie> materii) {
        this.username = username;
        this.email = email;
        this.materii = materii;
    }

    public Profesor(String username, String email, ArrayList<Materie> materii, String origine, String parola) {
        this.username = username;
        this.email = email;
        this.materii = materii;
        this.origine = origine;
        this.parola = parola;
    }

    public String getOrigine() {
        return origine;
    }

    public void setOrigine(String origine) {
        this.origine = origine;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NonNull
    @Override
    public String toString() {
        return this.email+" "+this.username+" "+this.parola+" "+this.origine;
    }
}
