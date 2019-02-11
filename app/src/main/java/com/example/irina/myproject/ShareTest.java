package com.example.irina.myproject;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.irina.myproject.contracts.DatabaseContract;
import com.example.irina.myproject.helpers.DatabaseHelper;

public class ShareTest extends AppCompatActivity {

    EditText email_prof;
    Spinner spDrepturi;
    Button btnTrimiteTest;
    EditText titlu;

    int nrTrimiteri =0;

    int idTestDeTrimis = 0;
    String titluTestDeTrimis = "";
    String categorieTestDeTrimis ="";
    int timpTestDeTrimis = 0;
    String accesTestdeTrimis = "";
    String parcurgereTestDeTrimis ="";
    int nrRulariTestDeTrimis = 0;
    int amestecQTestDeTrimis = 0;
    int amestecRTestDeTrimis = 0;
    String emailProfDetinator = "";
    String origine = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_test);

        email_prof = (EditText) findViewById(R.id.et_prof);
        spDrepturi = (Spinner) findViewById(R.id.spinnerDrepturiAcces);
        btnTrimiteTest = (Button) findViewById(R.id.buttonTrimiteTest);
        titlu = (EditText) findViewById(R.id.etTitlu);



        btnTrimiteTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validare()) {

                    SharedPreferences sp = getApplication().getSharedPreferences("idShareTest", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                   int idTestSelectat = sp.getInt("idShareT",0);

                    DatabaseHelper helper = new DatabaseHelper(ShareTest.this);
                    SQLiteDatabase db1 = helper.getReadableDatabase();

                    //PRELUAM DIN BD DATELE TESTULUI SELECTAT PE CARE VREM SA IL PARTAJAM
                    Cursor cursor = db1.rawQuery("SELECT * FROM " + DatabaseContract.TestTable.TABLE_NAME + " WHERE " +
                            DatabaseContract.TestTable.COLUMN_NAME_ID + " = " + idTestSelectat, null);

                    SharedPreferences sp2 = getApplication().getSharedPreferences("nr", MODE_PRIVATE);
                    SharedPreferences.Editor editor2 = sp2.edit();
                    nrTrimiteri = sp.getInt("nr",0);

                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~```");

                    System.out.println("ID SELECTAT: " + idTestSelectat);

                    while(cursor.moveToNext()){

                        int index = cursor.getColumnIndex(DatabaseContract.TestTable.COLUMN_NAME_ID);
                        idTestDeTrimis = cursor.getInt(index) + 15 + nrTrimiteri;

                        nrTrimiteri ++;

                        System.out.println("ID DE TRIMIS: " + idTestDeTrimis);

                        index = cursor.getColumnIndex(DatabaseContract.TestTable.COLUMN_NAME_TITLU);
                        titluTestDeTrimis = cursor.getString(index);

                        System.out.println("TITLU DE TRIMIS: " + titluTestDeTrimis);

                        index = cursor.getColumnIndex(DatabaseContract.TestTable.COLUMN_NAME_CATEGORIE);
                        categorieTestDeTrimis = cursor.getString(index);

                        System.out.println("CATEGORIE DE TRIMIS: " + categorieTestDeTrimis);

                        index = cursor.getColumnIndex(DatabaseContract.TestTable.COLUMN_NAME_TIMP);
                        timpTestDeTrimis = cursor.getInt(index);

                        System.out.println("TIMP DE TRIMIS: " + timpTestDeTrimis);

                        index = cursor.getColumnIndex(DatabaseContract.TestTable.COLUMN_NAME_ACCES);
                        accesTestdeTrimis = cursor.getString(index);

                        index = cursor.getColumnIndex(DatabaseContract.TestTable.COLUMN_NAME_PARCURGERE);
                        parcurgereTestDeTrimis = cursor.getString(index);

                        index = cursor.getColumnIndex(DatabaseContract.TestTable.COLUMN_NAME_NRRULARI);
                        nrRulariTestDeTrimis = cursor.getInt(index);

                        index = cursor.getColumnIndex(DatabaseContract.TestTable.COLUMN_NAME_AMESTECAREQ);
                        amestecQTestDeTrimis = cursor.getInt(index);

                        index = cursor.getColumnIndex(DatabaseContract.TestTable.COLUMN_NAME_AMESTECARER);
                        amestecRTestDeTrimis = cursor.getInt(index);

                        index = cursor.getColumnIndex(DatabaseContract.TestTable.COLUMN_NAME_EMAILPROF);
                        emailProfDetinator = cursor.getString(index);

                        System.out.println("EMAIL PROF DETINATOR: " + emailProfDetinator);

                        index = cursor.getColumnIndex(DatabaseContract.TestTable.COLUMN_NAME_ORIGIN);
                        origine = cursor.getString(index);
                    }

                    SharedPreferences sp3 = getApplication().getSharedPreferences("nr", MODE_PRIVATE);
                    SharedPreferences.Editor editor3 = sp3.edit();
                    editor3.putInt("nr",nrTrimiteri);
                    editor3.apply();

                    System.out.println("EMAIL PROF CARUIA TRIMIT: " + email_prof.getText().toString());

                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~```");

                    SQLiteDatabase db = helper.getWritableDatabase();
                    ContentValues val = new ContentValues();

                    val.put(DatabaseContract.TestTable.COLUMN_NAME_ID,idTestDeTrimis);
                    if(titlu.getText().toString().isEmpty()) {
                        val.put(DatabaseContract.TestTable.COLUMN_NAME_TITLU,titluTestDeTrimis);
                    }else {
                        val.put(DatabaseContract.TestTable.COLUMN_NAME_TITLU, titlu.getText().toString());
                    }
                    val.put(DatabaseContract.TestTable.COLUMN_NAME_CATEGORIE,categorieTestDeTrimis);
                    val.put(DatabaseContract.TestTable.COLUMN_NAME_TIMP,timpTestDeTrimis);
                    val.put(DatabaseContract.TestTable.COLUMN_NAME_ACCES,accesTestdeTrimis);
                    val.put(DatabaseContract.TestTable.COLUMN_NAME_PARCURGERE,parcurgereTestDeTrimis);
                    val.put(DatabaseContract.TestTable.COLUMN_NAME_NRRULARI,nrRulariTestDeTrimis);
                    val.put(DatabaseContract.TestTable.COLUMN_NAME_AMESTECAREQ,amestecQTestDeTrimis);
                    val.put(DatabaseContract.TestTable.COLUMN_NAME_AMESTECARER,amestecRTestDeTrimis);
                    val.put(DatabaseContract.TestTable.COLUMN_NAME_EMAILPROF,email_prof.getText().toString());
                    val.put(DatabaseContract.TestTable.COLUMN_NAME_ORIGIN,origine);

                    db.insert(DatabaseContract.TestTable.TABLE_NAME,null,val);

                    Toast.makeText(getApplicationContext(), titluTestDeTrimis + "  a fost trimis cu SUCCES!", Toast.LENGTH_SHORT).show();


                    Intent intent = new Intent();
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });

    }

    public boolean validare(){
        boolean valid = true;
        if(email_prof.getText().toString().isEmpty()){
            email_prof.setError("Introduceti email-ul profesorului caruia doriti sa ii partajati testul!");
            valid =false;
        }
        DatabaseHelper helper = new DatabaseHelper(ShareTest.this);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor2 = db.rawQuery("SELECT * FROM " + DatabaseContract.ProfesorTable.TABLE_NAME, null);

        int OK=0;
        while (cursor2.moveToNext()) {
            int index3 = cursor2.getColumnIndex(DatabaseContract.ProfesorTable.COLUMN_NAME_EMAIL);

            if (email_prof.getText().toString().equals(cursor2.getString(index3))) {
                OK = 1;
                break;
            }
        }

            if(OK==0){
                valid =false;
                email_prof.setError("Introduceti un email corect!");
            }
        return valid;
    }
}
