package com.example.irina.myproject;

import java.io.Serializable;
import java.util.ArrayList;

public class Intrebare implements Serializable {

    int[] raspunsuriCorecte=new int[4];
    ArrayList<String> variante=new ArrayList<String>();
    private String intrebare;
    private String timp;
    private int id;
    private String tipIntrebare;
    private String origine;
    private int punctaj;
    private int idTest;

    public Intrebare() {
    }

    public Intrebare(String intrebare) {
        this.intrebare = intrebare;
    }

    public Intrebare(String intrebare,int[] raspunsuriCorecte, ArrayList<String> variante,  String tipIntrebare) {
        this.raspunsuriCorecte = raspunsuriCorecte;
        this.variante = variante;
        this.intrebare = intrebare;
        this.tipIntrebare = tipIntrebare;
    }

    public Intrebare(String intrebare, String timp, int id, String tipIntrebare, String origine, int punctaj, int idTestl) {
        this.intrebare = intrebare;
        this.timp = timp;
        this.id = id;
        this.tipIntrebare = tipIntrebare;
        this.origine = origine;
        this.punctaj = punctaj;
        this.idTest = idTestl;
    }

    public String getIntrebare() {
        return intrebare;
    }

    public void setIntrebare(String intrebare) {
        this.intrebare = intrebare;
    }

    public String getTimp() {
        return timp;
    }

    public void setTimp(String timp) {
        this.timp = timp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipIntrebare() {
        return tipIntrebare;
    }

    public void setTipIntrebare(String tipIntrebare) {
        this.tipIntrebare = tipIntrebare;
    }

    public String getOrigine() {
        return origine;
    }

    public void setOrigine(String origine) {
        this.origine = origine;
    }

    public int getPunctaj() {
        return punctaj;
    }

    public void setPunctaj(int punctaj) {
        this.punctaj = punctaj;
    }

    public int getIdTest() {
        return idTest;
    }

    public void setIdTest(int idTest) {
        this.idTest = idTest;
    }

    @Override
    public String toString() {
        return this.id+" "+this.intrebare + " "+ this.tipIntrebare + " "+ this.timp + " "+this.idTest+" "+this.punctaj+" "+this.origine;
    }
}

