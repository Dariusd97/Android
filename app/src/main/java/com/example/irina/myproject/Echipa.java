package com.example.irina.myproject;

import java.io.Serializable;
import java.util.ArrayList;

public class Echipa implements Serializable {

    private String numeEchipa;
    private ArrayList<String> listaMembrii;

    public Echipa(String numeEchipa, ArrayList<String> listaParticipanti) {
        this.numeEchipa = numeEchipa;
        this.listaMembrii = listaParticipanti;
    }

    public Echipa(ArrayList<String> listaParticipanti) {
        this.listaMembrii = listaParticipanti;
    }

    public ArrayList<String> getListaMembrii() {
        return listaMembrii;
    }

    public void setListaMembrii(ArrayList<String> listaMembrii) {
        this.listaMembrii = listaMembrii;
    }

    public String getNumeEchipa() {
        return numeEchipa;
    }

    public void setNumeEchipa(String numeEchipa) {
        this.numeEchipa = numeEchipa;
    }
}
