package com.example.irina.myproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class Corect extends AppCompatActivity {

    Button btnOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corect);

        btnOK = (Button) findViewById(R.id.button9);

       /* btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Corect.this,RezultateTeste.class);
                finish();
            }
        });*/
    }

    public void navigateToRezultateTeste(View view){
        Intent intent = new Intent(Corect.this, RezultateTeste.class);
        startActivity(intent);
    }
}
