package com.example.irina.myproject;

import java.io.Serializable;

import androidx.annotation.NonNull;

public class Raspuns implements Serializable {

    private String textRasp;
    private int codRasp;
    private int corect; //0-gresit, 1-corect
    private int idIntrebare;
    private String origine;



    public Raspuns(){

    }

    public Raspuns(String textRasp, int codRasp, int corect) {
        this.textRasp = textRasp;
        this.codRasp = codRasp;
        this.corect = corect;
    }

    public Raspuns(String textRasp, int codRasp, int corect, int idIntrebare, String origine) {
        this.textRasp = textRasp;
        this.codRasp = codRasp;
        this.corect = corect;
        this.idIntrebare = idIntrebare;
        this.origine = origine;
    }

    public int getIdIntrebare() {
        return idIntrebare;
    }

    public void setIdIntrebare(int idIntrebare) {
        this.idIntrebare = idIntrebare;
    }

    public String getOrigine() {
        return origine;
    }

    public void setOrigine(String origine) {
        this.origine = origine;
    }

    public String getTextRasp() {
        return textRasp;
    }

    public void setTextRasp(String textRasp) {
        this.textRasp = textRasp;
    }

    public int getCodRasp() {
        return codRasp;
    }

    public void setCodRasp(int codRasp) {
        this.codRasp = codRasp;
    }

    public int getCorect() {
        return corect;
    }

    public void setCorect(int corect) {
        this.corect = corect;
    }

    @NonNull
    @Override
    public String toString() {
        return this.codRasp + " "+this.textRasp + " "+this.corect+" "+this.idIntrebare + " "+this.origine;
    }
}
