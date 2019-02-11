package com.example.irina.myproject;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.irina.myproject.contracts.DatabaseContract;
import com.example.irina.myproject.helpers.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.lang.Integer.parseInt;


public class CreareTest extends AppCompatActivity {

    private ArrayList<String> listaQ ;

    public static int id;

    ListView lv;
    Spinner categorii;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter2;
    private ArrayList<String> listaCategorii = new ArrayList<>();

    ClasaTest test;
    ImageButton btnSave;
    EditText titlu;
    EditText timpTest;
    Spinner categorie;

    ImageButton btnCancel;


    List<String> listaPrefDinCreare;
    List<String> listaPrefDinView;

    int idTestCurent=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creare_test);

        btnSave = (ImageButton) findViewById(R.id.buttonSave);
        titlu = (EditText) findViewById(R.id.editTextTitlu);
        //    timpTest = (EditText) findViewById(R.id.editTextTimpTest);
        categorie = (Spinner) findViewById(R.id.spinnerCateg);
        timpTest = (EditText) findViewById(R.id.editTextTimpTest);
        btnCancel = (ImageButton) findViewById(R.id.cancel_btn) ;


        categorii = (Spinner) findViewById(R.id.spinnerCateg);

        listaPrefDinCreare = new ArrayList<String>();
        listaPrefDinView = new ArrayList<String>();



        SharedPreferences sp3 = getApplication().getSharedPreferences("emaillogareprof", MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sp3.edit();
        final String email_logare = sp3.getString("pref_emailprof", "");



        DatabaseHelper helper2 = new DatabaseHelper(CreareTest.this);
        SQLiteDatabase db2 = helper2.getReadableDatabase();

        Cursor cursor2 = db2.rawQuery("SELECT * FROM " + DatabaseContract.RandMaterieTable.TABLE_NAME + " WHERE " + DatabaseContract.RandMaterieTable.COLUMN_NAME_EMAIL + " like '" + email_logare + "'", null);


        while (cursor2.moveToNext()) {
            int index2 = cursor2.getColumnIndex(DatabaseContract.RandMaterieTable.COLUMN_NAME_DESCRIERE);
            listaCategorii.add(cursor2.getString(index2));
        }


        adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item,listaCategorii);
        categorii.setAdapter(adapter);
        categorii.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView)adapterView.getChildAt(0)).setTextColor(Color.BLACK);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //PUN INTR-O LISTA TITLURILE INTREBARILOR DIN BD
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        SharedPreferences sp21 = getApplication().getSharedPreferences("idpastrat", MODE_PRIVATE);
        SharedPreferences.Editor editor21 = sp21.edit();
        final int idTestCurent1 = sp21.getInt("id_pastrat", 0);


        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&  " + idTestCurent1 + "   &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");

        DatabaseHelper hp = new DatabaseHelper(CreareTest.this);
        SQLiteDatabase dbq = hp.getReadableDatabase();

        Cursor cursorq = dbq.rawQuery("SELECT " + DatabaseContract.IntrebareTable.COLUMN_NAME_TEXT + " FROM " + DatabaseContract.IntrebareTable.TABLE_NAME + " WHERE " +
                DatabaseContract.IntrebareTable.COLUMN_NAME_IDTEST + " = " + idTestCurent1,null);

        listaQ = new ArrayList<>();
        while(cursorq.moveToNext()){
            int index = cursorq.getColumnIndex(DatabaseContract.IntrebareTable.COLUMN_NAME_TEXT);
            listaQ.add(cursorq.getString(index));
        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println(idTestCurent);
        for(int i=0;i<listaQ.size();i++){
            System.out.println(listaQ.get(i));
        }
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");

        lv = (ListView) findViewById(R.id.listViewIntrebari);

        adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listaQ);
        lv.setAdapter(adapter2);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CreareTest.this,ModificaIntrebare.class);
                intent.putExtra("Q_selectat",listaQ.get(position).toString());
                intent.putExtra("idTestMSelectat",idTestCurent1);
                startActivityForResult(intent,2);
                //  startActivityForResult(intent,3);
            }
        });




        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sp = getApplication().getSharedPreferences("setariTest",MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();

                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                System.out.println( sp.getString("acces",""));
                System.out.println(sp.getString("parcurgere",""));
                System.out.println(sp.getInt("nrRulari",0));
                System.out.println(sp.getInt("switchQ",0));
                System.out.println(sp.getInt("switchR",0));
                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");


                if(validare()){

                    SharedPreferences sp9 = getApplication().getSharedPreferences("idpastrat", MODE_PRIVATE);
                    SharedPreferences.Editor editor9 = sp9.edit();
                     id = sp9.getInt("id_pastrat", 0);

                     idTestCurent =id;
                    DatabaseHelper helper = new DatabaseHelper(CreareTest.this);
                    SQLiteDatabase db = helper.getWritableDatabase();

                    ContentValues inregistrare = new ContentValues();

                    inregistrare.put(DatabaseContract.TestTable.COLUMN_NAME_ID,id);
                    inregistrare.put(DatabaseContract.TestTable.COLUMN_NAME_TITLU, titlu.getText().toString());
                    inregistrare.put(DatabaseContract.TestTable.COLUMN_NAME_CATEGORIE, categorie.getSelectedItem().toString());
                    inregistrare.put(DatabaseContract.TestTable.COLUMN_NAME_TIMP, timpTest.getText().toString());
                    inregistrare.put(DatabaseContract.TestTable.COLUMN_NAME_ACCES, sp.getString("acces",""));
                    inregistrare.put(DatabaseContract.TestTable.COLUMN_NAME_PARCURGERE, sp.getString("parcurgere",""));
                    inregistrare.put(DatabaseContract.TestTable.COLUMN_NAME_NRRULARI, sp.getInt("nrRulari",1));
                    inregistrare.put(DatabaseContract.TestTable.COLUMN_NAME_AMESTECAREQ, sp.getInt("switchQ",0));
                    inregistrare.put(DatabaseContract.TestTable.COLUMN_NAME_AMESTECARER, sp.getInt("switchR",0));
                    inregistrare.put(DatabaseContract.TestTable.COLUMN_NAME_EMAILPROF, email_logare);
                    inregistrare.put(DatabaseContract.TestTable.COLUMN_NAME_ORIGIN, "manual");


                    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                    System.out.println("ID: " + id);
                    System.out.println("TITLU: " + titlu.getText().toString());
                    System.out.println("CATEGRIE: " + categorie.getSelectedItem().toString());
                    System.out.println("TIMP: " +  timpTest.getText().toString());
                    System.out.println("ACCES: " + sp.getString("acces",""));
                    System.out.println("PARCURGERE: " + sp.getString("parcurgere",""));
                    System.out.println("NR RULARI: " +  sp.getInt("nrRulari",1));
                    System.out.println("AMESTECARE Q: " + sp.getInt("switchQ",0));
                    System.out.println("AMESTECARE R: " + sp.getInt("switchR",0));
                    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

                    db.insert(DatabaseContract.TestTable.TABLE_NAME,null,inregistrare);

                    id=id+1;

                    SharedPreferences sp8 = getApplication().getSharedPreferences("idpastrat", MODE_PRIVATE);
                    SharedPreferences.Editor editor8 = sp8.edit();
                    editor8.putInt("id_pastrat", id);
                    editor8.apply();

                    test = new ClasaTest(titlu.getText().toString(), categorie.getSelectedItem().toString());
                    Intent intent = new Intent();
                    intent.putExtra("ts",test);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseHelper helper8 = new DatabaseHelper(CreareTest.this);
                SQLiteDatabase db8 = helper8.getReadableDatabase();

                //SALVEZ ID-URILE INTREBARILOR CARE AU ID_TEST = idTestSelectat
                Cursor cursor8 = db8.rawQuery("SELECT * FROM " + DatabaseContract.IntrebareTable.TABLE_NAME + " WHERE " +
                        DatabaseContract.IntrebareTable.COLUMN_NAME_IDTEST + " = " + idTestCurent,null);

                ArrayList<Integer> listaIdQdeSters = new ArrayList<>();

                while(cursor8.moveToNext()){
                    int index = cursor8.getColumnIndex(DatabaseContract.IntrebareTable.COLUMN_NAME_IDQ);
                    listaIdQdeSters.add(cursor8.getInt(index));
                }

                //STERG RASPUNSURILE LEGATE LA INTREBAREA CARE ARE ID-UL IN LISTA listaIdQdeSters


                for(int i =0;i<listaIdQdeSters.size();i++){
                    db8.delete(DatabaseContract.RaspunsTable.TABLE_NAME,DatabaseContract.RaspunsTable.COLUMN_NAME_IDQ + " = " + listaIdQdeSters.get(i),null);
                }

                //STERG INTRENARILE AFERENTE TESTULUI SELECTAT

                db8.delete(DatabaseContract.IntrebareTable.TABLE_NAME, DatabaseContract.IntrebareTable.COLUMN_NAME_IDTEST + " = " + idTestCurent,null);

                finish();
            }
        });
    }


  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if((requestCode == 1 || requestCode ==2 ) && resultCode == RESULT_OK) {
         incarcareIntrebari();
        }
    }



    public void incarcareIntrebari(){


        //PUN INTR-O LISTA TITLURILE INTREBARILOR DIN BD
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        SharedPreferences sp21 = getApplication().getSharedPreferences("idpastrat", MODE_PRIVATE);
        SharedPreferences.Editor editor21 = sp21.edit();
        final int idTestCurent = sp21.getInt("id_pastrat", 0);


        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&  " + idTestCurent + "   &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");

        DatabaseHelper hp = new DatabaseHelper(CreareTest.this);
        SQLiteDatabase dbq = hp.getReadableDatabase();

        Cursor cursorq = dbq.rawQuery("SELECT " + DatabaseContract.IntrebareTable.COLUMN_NAME_TEXT + " FROM " + DatabaseContract.IntrebareTable.TABLE_NAME + " WHERE " +
                DatabaseContract.IntrebareTable.COLUMN_NAME_IDTEST + " = " + idTestCurent,null);

        listaQ = new ArrayList<>();
        while(cursorq.moveToNext()){
            int index = cursorq.getColumnIndex(DatabaseContract.IntrebareTable.COLUMN_NAME_TEXT);
            listaQ.add(cursorq.getString(index));
        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println(idTestCurent);
        for(int i=0;i<listaQ.size();i++){
            System.out.println(listaQ.get(i));
        }
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");

        lv = (ListView) findViewById(R.id.listViewIntrebari);

        adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listaQ);
        lv.setAdapter(adapter2);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CreareTest.this,ModificaIntrebare.class);
                intent.putExtra("Q_selectat",listaQ.get(position).toString());
                intent.putExtra("idTestMSelectat",idTestCurent);
                startActivityForResult(intent,2);
              //  startActivityForResult(intent,3);
            }
        });


    }

    public void navigateToSetariTest(View view){
        Intent intent = new Intent(CreareTest.this, SetariTest.class);
       // startActivityForResult(intent,3);
        startActivity(intent);
    }

    public void AdaugaIntrebare(View view){
        Intent intent = new Intent(CreareTest.this, CreareIntrebare.class);
        intent.putExtra("idTestMSelectat",idTestCurent);
        startActivityForResult(intent,1);
    }


    public boolean validare(){
        boolean valid = true;
        if(titlu.getText().toString().isEmpty()){
            titlu.setError("Introduceti un titlu!");
            valid= false;
        }
        return valid;
    }

}
