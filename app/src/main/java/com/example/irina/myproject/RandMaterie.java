package com.example.irina.myproject;

import androidx.annotation.NonNull;

public class RandMaterie {
    private String emailProfesor;
    private String descriere;
    private String origine;

    public RandMaterie(){

    }
    public RandMaterie(String emailProfesor, String descriere,String origine) {
        this.emailProfesor = emailProfesor;
        this.descriere = descriere;
        this.origine=origine;
    }

    public String getEmailProfesor() {
        return emailProfesor;
    }

    public void setEmailProfesor(String emailProfesor) {
        this.emailProfesor = emailProfesor;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public String getOrigine() {
        return origine;
    }

    public void setOrigine(String origine) {
        this.origine = origine;
    }

    @NonNull
    @Override
    public String toString() {
        return this.emailProfesor+" "+this.descriere+" "+ this.origine;
    }
}
