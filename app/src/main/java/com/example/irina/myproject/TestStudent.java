package com.example.irina.myproject;

import androidx.annotation.NonNull;

public class TestStudent {
    private String email_student;
    private int id_test;
    private String origine;
    private int punctaj;

    public TestStudent() {
    }

    public TestStudent(String email_student, int id_test, String origine, int punctaj) {
        this.email_student = email_student;
        this.id_test = id_test;
        this.origine = origine;
        this.punctaj = punctaj;
    }

    public int getPunctaj() {
        return punctaj;
    }

    public void setPunctaj(int punctaj) {
        this.punctaj = punctaj;
    }

    public String getEmail_student() {
        return email_student;
    }

    public void setEmail_student(String email_student) {
        this.email_student = email_student;
    }

    public int getId_test() {
        return id_test;
    }

    public void setId_test(int id_test) {
        this.id_test = id_test;
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
        return this.email_student + " " + this.id_test + " " + this.origine+" "+this.punctaj;
    }
}

