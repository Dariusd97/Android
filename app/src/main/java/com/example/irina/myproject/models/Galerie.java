package com.example.irina.myproject.models;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Galerie implements Serializable {
    public Bitmap imagine;
    public String numeArtist;
    public String siteArtist;

    public Galerie(){

    }

    public Galerie(Bitmap imagine) {
        this.imagine = imagine;
    }
}
