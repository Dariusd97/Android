package com.example.irina.myproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import static java.lang.Integer.parseInt;

public class SetariTest extends AppCompatActivity {

    Spinner acces;
    Spinner parcurgere;
    EditText nrRulari;
    Switch switchQ;
    Switch switchR;
    Button btnApply;
    Button btnCancel;

    int OKQ=0;
    int OKR=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setari_test);

        acces = (Spinner) findViewById(R.id.spinnerAccesM);
        parcurgere = (Spinner) findViewById(R.id.spinnerParcurgereM);
        nrRulari = (EditText) findViewById(R.id.etNrRulariM);
        switchQ = (Switch) findViewById(R.id.switchQM);
        switchR = (Switch) findViewById(R.id.switchR);
        btnApply = (Button) findViewById(R.id.buttonApplyM);
        btnCancel = (Button) findViewById(R.id.buttonCancelM);


        switchQ.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    OKQ = 1;
                } else {
                    OKQ = 0;
                }
            }
        });
        switchR.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    OKR = 1;
                } else {
                    OKR = 0;
                }
            }
        });


        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validare()) {
                    SharedPreferences sp = getApplication().getSharedPreferences("setariTest", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("acces", acces.getSelectedItem().toString());
                    editor.putString("parcurgere", parcurgere.getSelectedItem().toString());
                    editor.putInt("nrRulari", parseInt(nrRulari.getText().toString()));


                    if (OKQ == 1) {
                        editor.putInt("switchQ", 1);
                    } else {
                        editor.putInt("switchQ", 0);
                    }
                    if (OKR == 1) {
                        editor.putInt("switchR", 1);
                    } else {
                        editor.putInt("switchR", 0);
                    }

                    System.out.println("?????????????????????????????????????????????");
                    System.out.println("OKQ = " + OKQ + " OKR = " + OKR);

                    editor.apply();

                    Intent intent = new Intent(SetariTest.this, CreareTest.class);
                    finish();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                finish();
            }
        });
    }

    public boolean validare(){
        boolean ok= true;
        if(nrRulari.getText().toString().isEmpty()) {
            nrRulari.setError("Introduceti numarul de rulari!");
            ok = false;
        }

        return ok;
    }

}
