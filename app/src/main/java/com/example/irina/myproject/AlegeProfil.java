package com.example.irina.myproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AlegeProfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alege_profil);
    }


    public void navigateToCreareProfilStud(View view) {
        Intent explicitIntent = new Intent(AlegeProfil.this, CreareProfilStudent.class);
        startActivity(explicitIntent);
    }

    public void navigateToCreareProfilProfesor(View view){
        Intent explicitIntent = new Intent(AlegeProfil.this, CreareProfilProfesor.class);
        startActivity(explicitIntent);
    }

}
