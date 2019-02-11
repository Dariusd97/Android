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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.irina.myproject.contracts.DatabaseContract;
import com.example.irina.myproject.helpers.DatabaseHelper;

import java.util.ArrayList;

public class ModificaIntrebare extends AppCompatActivity {

    EditText intrebare;
    EditText R1;
    EditText R2;
    EditText R3;
    EditText R4;
    CheckBox c1;
    CheckBox c2;
    CheckBox c3;
    CheckBox c4;
    Spinner spTimp;
    Spinner spTip;
    Button btnSave;
    Button btnCancel;
    Button btnDelete;

    int idQSelectat =0;
    int idTest = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifica_intrebare);

        intrebare = (EditText) findViewById(R.id.editText_intrebareR);
        R1 = (EditText) findViewById(R.id.editText_R1_R);
        R2 = (EditText) findViewById(R.id.editText_R2_R);
        R3 = (EditText) findViewById(R.id.editText_R3_R);
        R4 = (EditText) findViewById(R.id.editText_R4_R);
        c1 = (CheckBox) findViewById(R.id.checkBox1R);
        c2 = (CheckBox) findViewById(R.id.checkBox2R);
        c3 = (CheckBox) findViewById(R.id.checkBox3);
        c4 = (CheckBox) findViewById(R.id.checkBox4);
        spTimp = (Spinner) findViewById(R.id.spinner_timp);
        spTip = (Spinner) findViewById(R.id.spinner_tip);
        btnSave = (Button) findViewById(R.id.buttonSave);
        btnCancel = (Button) findViewById(R.id.buttonCancelM);
        btnDelete = (Button) findViewById(R.id.button_del) ;

       final String Q_selectat = getIntent().getStringExtra("Q_selectat");
        idTest = getIntent().getIntExtra("idTestMSelectat",0);

       intrebare.setText(Q_selectat);

        SharedPreferences sp9 = getApplication().getSharedPreferences("idpastrat", MODE_PRIVATE);
        SharedPreferences.Editor editor9 = sp9.edit();
       // final int idTest = sp9.getInt("id_pastrat", 0);

        //PRELUAREA RASPUNSURILOR PT INTREBAREA SELECTATA DIN BAZA

        DatabaseHelper helper11 = new DatabaseHelper(ModificaIntrebare.this);
        SQLiteDatabase db11 =helper11.getReadableDatabase();

        Cursor cursor11 = db11.rawQuery("SELECT * FROM " + DatabaseContract.IntrebareTable.TABLE_NAME + " WHERE " + DatabaseContract.IntrebareTable.COLUMN_NAME_TEXT + " like '" + Q_selectat + "' AND " +
                DatabaseContract.IntrebareTable.COLUMN_NAME_IDTEST + " = " + idTest,null);


        while(cursor11.moveToNext()){
            int index = cursor11.getColumnIndex(DatabaseContract.IntrebareTable.COLUMN_NAME_IDQ);
            idQSelectat = cursor11.getInt(index);
        }

        DatabaseHelper helper12 = new DatabaseHelper(ModificaIntrebare.this);
        SQLiteDatabase db12 =helper12.getReadableDatabase();

        Cursor cursor12 = db12.rawQuery("SELECT * FROM " + DatabaseContract.RaspunsTable.TABLE_NAME + " WHERE " + DatabaseContract.RaspunsTable.COLUMN_NAME_IDQ + " = " + idQSelectat,null);

        ArrayList<String> listaRaspunsuri = new ArrayList<>();
        while(cursor12.moveToNext()){
            int index = cursor12.getColumnIndex(DatabaseContract.RaspunsTable.COLUMN_NAME_TEXT);
            listaRaspunsuri.add(cursor12.getString(index));
        }

        System.out.println("WWWWWWWWWWWWWWWWWWWWW     Lista raspunsuri    WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
        System.out.println(idTest);
        System.out.println(Q_selectat);
        System.out.println(idQSelectat);
        for(int i=0;i<listaRaspunsuri.size();i++){
            System.out.println(listaRaspunsuri.get(i));
        }
        System.out.println("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");

        if(R1.getText().toString().isEmpty()) {
            R1.setText(listaRaspunsuri.get(0));
        }
        if(R2.getText().toString().isEmpty()) {
            R2.setText(listaRaspunsuri.get(1));
        }
        if(R3.getText().toString().isEmpty()) {
            R3.setText(listaRaspunsuri.get(2));
        }
        if(R4.getText().toString().isEmpty()) {
            R4.setText(listaRaspunsuri.get(3));
        }


        DatabaseHelper helper = new DatabaseHelper(ModificaIntrebare.this);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT r.* FROM " + DatabaseContract.RaspunsTable.TABLE_NAME + " r," + DatabaseContract.IntrebareTable.TABLE_NAME + " q WHERE r." + DatabaseContract.RaspunsTable.COLUMN_NAME_IDQ +
                " = q." + DatabaseContract.IntrebareTable.COLUMN_NAME_IDQ + " AND " + DatabaseContract.IntrebareTable.COLUMN_NAME_TEXT + " like '" + Q_selectat + "'", null);

        final ArrayList<String> listaRasp = new ArrayList<>();
        ArrayList<Integer> raspCorecte = new ArrayList<>();

        while(cursor.moveToNext()){
            int index1 = cursor.getColumnIndex(DatabaseContract.RaspunsTable.COLUMN_NAME_TEXT);
            int index2 = cursor.getColumnIndex(DatabaseContract.RaspunsTable.COLUMN_NAME_VERIFICARE);
            listaRasp.add(cursor.getString(index1));
            raspCorecte.add(cursor.getInt(index2));
        }

        System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
        for(int i=0;i<listaRasp.size();i++){
            System.out.println(listaRasp.get(i));
        }
        System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");

        System.out.println("======================= " + raspCorecte.size() + " =====================");

        for(int i=0;i<raspCorecte.size();i++) {
            if (raspCorecte.get(0) == 1) {
                c1.setChecked(true);
            }
            if (raspCorecte.get(1) == 1) {
                c2.setChecked(true);
            }
            if (raspCorecte.get(2) == 1) {
                c3.setChecked(true);
            }
            if (raspCorecte.get(3) == 1) {
                c4.setChecked(true);
            }
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseHelper helper = new DatabaseHelper(ModificaIntrebare.this);
                SQLiteDatabase db = helper.getWritableDatabase();

                ArrayList<String> listaR = new ArrayList<>();
                listaR.add(R1.getText().toString());
                listaR.add(R2.getText().toString());
                listaR.add(R3.getText().toString());
                listaR.add(R4.getText().toString());

                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++==");
                for(int i=0;i<listaR.size();i++){
                    System.out.println(listaR.get(i));
                }
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++==");

                int varianteCorecte [] = new int[4];
                for(int i=0;i<4;i++){
                    varianteCorecte[i] = 0;
                }
                if(c1.isChecked()){
                    varianteCorecte[0]=1;
                }
                if(c2.isChecked()){
                    varianteCorecte[1]=1;
                }
                if(c3.isChecked()){
                    varianteCorecte[2]=1;
                }
                if(c4.isChecked()){
                    varianteCorecte[3]=1;
                }


                ContentValues valori = new ContentValues();
                ContentValues rasp = new ContentValues();

                valori.put(DatabaseContract.IntrebareTable.COLUMN_NAME_TEXT, intrebare.getText().toString());
                valori.put(DatabaseContract.IntrebareTable.COLUMN_NAME_TIMP, spTimp.getSelectedItem().toString());
                valori.put(DatabaseContract.IntrebareTable.COLUMN_NAME_TIP, spTip.getSelectedItem().toString());

                db.update(DatabaseContract.IntrebareTable.TABLE_NAME,valori,DatabaseContract.IntrebareTable.COLUMN_NAME_IDQ + " = " + idQSelectat,null);

             /*   for(int i=0;i<listaR.size();i++) {
                    rasp.put(DatabaseContract.RaspunsTable.COLUMN_NAME_TEXT,listaR.get(i));
                    System.out.println(" ((((((((((((((((((  " + listaR.get(i) + "    )))))))))))))))))))))))))))))))))");
                    if(varianteCorecte[0]==1) {
                        rasp.put(DatabaseContract.RaspunsTable.COLUMN_NAME_VERIFICARE, 1);
                    }else{
                        rasp.put(DatabaseContract.RaspunsTable.COLUMN_NAME_VERIFICARE, 0);
                    }

                    db.update(DatabaseContract.RaspunsTable.TABLE_NAME,rasp,DatabaseContract.RaspunsTable.COLUMN_NAME_IDQ + " = " + idQSelectat, null);
                }*/

                Intent intent = new Intent();
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper helper2 = new DatabaseHelper(ModificaIntrebare.this);
                SQLiteDatabase db3 = helper2.getWritableDatabase();

                db3.delete(DatabaseContract.RaspunsTable.TABLE_NAME,DatabaseContract.RaspunsTable.COLUMN_NAME_IDQ + " = " + idQSelectat,null);

                db3.delete(DatabaseContract.IntrebareTable.TABLE_NAME, DatabaseContract.IntrebareTable.COLUMN_NAME_TEXT + " like '" + Q_selectat + "' AND " +
                DatabaseContract.IntrebareTable.COLUMN_NAME_IDTEST + " = " + idTest,null);

                Toast.makeText(getApplicationContext(), Q_selectat + " STERS", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent();
                setResult(RESULT_OK,intent);
                finish();

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
