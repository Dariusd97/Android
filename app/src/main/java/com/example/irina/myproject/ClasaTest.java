package com.example.irina.myproject;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class ClasaTest implements Serializable {

    public int id;
    public String titlu;
    public String categorie;
    public int timp;
    public String acces;    //0 = public , 1= privat
    public String parcurgere; //0= intr un sens, 1 = parcurgere cu revenire
    public int nrRulari;
    public int amestecareIntrebari;
    public int amestecareRaspunri;
    private int punctaj;
    public String origine;
    public String emailProfeor;


    public ClasaTest(){}
    public ClasaTest(String titlu){
        this.titlu=titlu;
    }
    public ClasaTest(String titlu, String categorie) {
        this.titlu = titlu;
        this.categorie = categorie;
    }

    public ClasaTest(String titlu, int punctaj) {
        this.titlu = titlu;
        this.punctaj = punctaj;
    }

    public ClasaTest(int pin,String titlu, String categorie, int timp, String acces, String parcurgere, int nrRulari, int amestecareIntrebari, int amestecareRaspunri, int punctaj) {

        this.titlu = titlu;
        this.categorie = categorie;
        this.timp = timp;
        this.acces = acces;
        this.parcurgere = parcurgere;
        this.nrRulari = nrRulari;
        this.amestecareIntrebari = amestecareIntrebari;
        this.amestecareRaspunri = amestecareRaspunri;

        this.punctaj = punctaj;
    }

    public ClasaTest(int id, String titlu, String categorie, int timp, String acces, String parcurgere, int nrRulari, int amestecareIntrebari, int amestecareRaspunri, int punctaj, String origine, String emailProfeor, int pin) {
        this.id = id;
        this.titlu = titlu;
        this.categorie = categorie;
        this.timp = timp;
        this.acces = acces;
        this.parcurgere = parcurgere;
        this.nrRulari = nrRulari;
        this.amestecareIntrebari = amestecareIntrebari;
        this.amestecareRaspunri = amestecareRaspunri;

        this.punctaj = punctaj;
        this.origine = origine;
        this.emailProfeor = emailProfeor;

    }

    public int getPunctaj() {
        return punctaj;
    }

    public void setPunctaj(int punctaj) {
        this.punctaj = punctaj;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(titlu);
        return sb.toString();
    }

    public String AfisarePunctaj(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.getPunctaj());
        return sb.toString();
    }
    public String afisare(){
        return this.titlu +" -> "+ this.punctaj+"\n";
    }

    public String afisareAtribute(){
        return this.id+" "+this.titlu+" "+this.categorie+" "+this.timp+" "+this.acces+" "+this.parcurgere+" "+this.nrRulari+" "+this.amestecareRaspunri+" "+this.amestecareIntrebari+" "+this.punctaj+" "+this.origine+" "+this.emailProfeor;
    }



}
