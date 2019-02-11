package com.example.irina.myproject;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.irina.myproject.adaptor.ExpandableListAdapter;
import com.example.irina.myproject.contracts.DatabaseContract;
import com.example.irina.myproject.helpers.DatabaseHelper;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Teste extends AppCompatActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    Button btnDelete;
    Button btnGo;
    Button btnShare;
    Button btnView;

    ArrayAdapter<String> adapter;

    List<String> listaPrefDinCreare;
    List<String> listaPrefDinView;

    HashMap<String,String> map;

    List<String> listaTitluri;


    int idTestSelectat =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste);

        expListView = (ExpandableListView) findViewById(R.id.lvExp); // get the listView

        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        expListView.setAdapter(listAdapter);

        btnDelete = (Button)findViewById(R.id.btnDelete);
        btnGo = (Button) findViewById(R.id.btnGo);
        btnShare =(Button) findViewById(R.id.btnShareTest);
        btnView = (Button) findViewById(R.id.btn_VIEW);
    }


    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        listaPrefDinCreare = new ArrayList<String>();
        listaPrefDinView = new ArrayList<String>();

        //COMPLETEZ LIST DATA HEADER CU MATERIILE PROFULUI LOGAT
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        SharedPreferences sp3 = getApplication().getSharedPreferences("emaillogareprof", MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sp3.edit();
        final String email_logare = sp3.getString("pref_emailprof", "");

        DatabaseHelper helper2 = new DatabaseHelper(Teste.this);
        SQLiteDatabase db2 = helper2.getReadableDatabase();

        Cursor cursor2 = db2.rawQuery("SELECT * FROM " + DatabaseContract.RandMaterieTable.TABLE_NAME + " WHERE " + DatabaseContract.RandMaterieTable.COLUMN_NAME_EMAIL + " like '" + email_logare + "'", null);

        while (cursor2.moveToNext()) {
            int index2 = cursor2.getColumnIndex(DatabaseContract.RandMaterieTable.COLUMN_NAME_DESCRIERE);
            listDataHeader.add(cursor2.getString(index2));
        }

        //POPULARE CHILD LIST CU TITLURILE TESTELOR CREATE DE PROFESORUL LOGAT
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {


                System.out.println(listDataHeader.get(groupPosition));
                String materieSelectata = "";
                materieSelectata = listDataHeader.get(groupPosition);

                listaTitluri = new ArrayList<>();

                DatabaseHelper helper = new DatabaseHelper(Teste.this);
                SQLiteDatabase db = helper.getReadableDatabase();

                Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseContract.TestTable.TABLE_NAME + " WHERE " +
                        DatabaseContract.TestTable.COLUMN_NAME_EMAILPROF + " like '" + email_logare + "' AND " +
                        DatabaseContract.TestTable.COLUMN_NAME_CATEGORIE + " like '" + materieSelectata + "'", null);

                while (cursor.moveToNext()) {
                    int index = cursor.getColumnIndex(DatabaseContract.TestTable.COLUMN_NAME_TITLU);
                    System.out.println(cursor.getString(index));
                    listaTitluri.add(cursor.getString(index));
                }

                listDataChild.put(listDataHeader.get(groupPosition), listaTitluri);
                listAdapter = new ExpandableListAdapter(Teste.this, listDataHeader, listDataChild);
                expListView.setAdapter(listAdapter);

                return false;
            }
        });

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, final int groupPosition, final int childPosition, long id) {
                v.setSelected(true);

                btnView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        DatabaseHelper helper1 = new DatabaseHelper(Teste.this);
                        SQLiteDatabase db1 = helper1.getReadableDatabase();

                        Cursor cursor1 = db1.rawQuery("SELECT " + DatabaseContract.TestTable.COLUMN_NAME_ID + " FROM " + DatabaseContract.TestTable.TABLE_NAME +
                                " WHERE " + DatabaseContract.TestTable.COLUMN_NAME_CATEGORIE + " like '" + listDataHeader.get(groupPosition) + "' AND " +
                                DatabaseContract.TestTable.COLUMN_NAME_TITLU + " like '" + listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition) + "'",null);

                        while(cursor1.moveToNext()){
                            int index = cursor1.getColumnIndex(DatabaseContract.TestTable.COLUMN_NAME_ID);
                            idTestSelectat = cursor1.getInt(index);
                        }


                        SharedPreferences sp111 = getApplication().getSharedPreferences("idTestM", MODE_PRIVATE);
                        SharedPreferences.Editor editor111 = sp111.edit();
                        editor111.putInt("idTM", idTestSelectat );
                        editor111.apply();

                        Intent intent = new Intent(Teste.this, ModificareTest.class);
                        startActivity(intent);
                    }
                });

                btnGo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        DatabaseHelper helper71 = new DatabaseHelper(Teste.this);
                        SQLiteDatabase db71 = helper71.getReadableDatabase();

                        Cursor cursor71 = db71.rawQuery("SELECT " + DatabaseContract.TestTable.COLUMN_NAME_ID + " FROM " + DatabaseContract.TestTable.TABLE_NAME +
                                " WHERE " + DatabaseContract.TestTable.COLUMN_NAME_CATEGORIE + " like '" + listDataHeader.get(groupPosition) + "' AND " +
                                DatabaseContract.TestTable.COLUMN_NAME_TITLU + " like '" + listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition) + "'",null);

                        while(cursor71.moveToNext()){
                            int index = cursor71.getColumnIndex(DatabaseContract.TestTable.COLUMN_NAME_ID);
                            idTestSelectat = cursor71.getInt(index);
                        }


                        SharedPreferences sp81 = getApplication().getSharedPreferences("pinTest", MODE_PRIVATE);
                        SharedPreferences.Editor editor81 = sp81.edit();
                        editor81.putInt("pinT", idTestSelectat );
                        editor81.apply();

                        Intent intent = new Intent(Teste.this, StartIndividual.class);
                        startActivity(intent);
                    }
                });

                btnShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        DatabaseHelper helper72 = new DatabaseHelper(Teste.this);
                        SQLiteDatabase db72 = helper72.getReadableDatabase();

                        Cursor cursor72 = db72.rawQuery("SELECT " + DatabaseContract.TestTable.COLUMN_NAME_ID + " FROM " + DatabaseContract.TestTable.TABLE_NAME +
                                " WHERE " + DatabaseContract.TestTable.COLUMN_NAME_CATEGORIE + " like '" + listDataHeader.get(groupPosition) + "' AND " +
                                DatabaseContract.TestTable.COLUMN_NAME_TITLU + " like '" + listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition) + "'",null);

                        while(cursor72.moveToNext()){
                            int index = cursor72.getColumnIndex(DatabaseContract.TestTable.COLUMN_NAME_ID);
                            idTestSelectat = cursor72.getInt(index);
                        }

                        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
                        System.out.println(idTestSelectat);
                        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");

                        SharedPreferences sp82 = getApplication().getSharedPreferences("idShareTest", MODE_PRIVATE);
                        SharedPreferences.Editor editor82 = sp82.edit();
                        editor82.putInt("idShareT", idTestSelectat );
                        editor82.apply();

                        Intent intent = new Intent(Teste.this, ShareTest.class);
                        startActivity(intent);
                    }
                });

                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //SALVEZ ID-UL TESTULUI SELECTAT CARE URMEAZA SA FIE STERS

                        DatabaseHelper helper7 = new DatabaseHelper(Teste.this);
                        SQLiteDatabase db7 = helper7.getReadableDatabase();

                        Cursor cursor7 = db7.rawQuery("SELECT " + DatabaseContract.TestTable.COLUMN_NAME_ID + " FROM " + DatabaseContract.TestTable.TABLE_NAME +
                                " WHERE " + DatabaseContract.TestTable.COLUMN_NAME_CATEGORIE + " like '" + listDataHeader.get(groupPosition) + "' AND " +
                                DatabaseContract.TestTable.COLUMN_NAME_TITLU + " like '" + listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition) + "'",null);

                        while(cursor7.moveToNext()){
                            int index = cursor7.getColumnIndex(DatabaseContract.TestTable.COLUMN_NAME_ID);
                            idTestSelectat = cursor7.getInt(index);
                        }

                        //SALVEZ ID-URILE INTREBARILOR CARE AU ID_TEST = idTestSelectat

                        DatabaseHelper helper8 = new DatabaseHelper(Teste.this);
                        SQLiteDatabase db8 = helper8.getReadableDatabase();

                        Cursor cursor8 = db8.rawQuery("SELECT * FROM " + DatabaseContract.IntrebareTable.TABLE_NAME + " WHERE " +
                                DatabaseContract.IntrebareTable.COLUMN_NAME_IDTEST + " = " + idTestSelectat,null);

                        ArrayList<Integer> listaIdQdeSters = new ArrayList<>();

                        while(cursor8.moveToNext()){
                            int index = cursor8.getColumnIndex(DatabaseContract.IntrebareTable.COLUMN_NAME_IDQ);
                            listaIdQdeSters.add(cursor8.getInt(index));
                        }

                        //STERG RASPUNSURILE LEGATE LA INTREBAREA CARE ARE ID-UL IN LISTA listaIdQdeSters

                        DatabaseHelper helper9 = new DatabaseHelper(Teste.this);
                        SQLiteDatabase db9 = helper9.getWritableDatabase();

                        for(int i =0;i<listaIdQdeSters.size();i++){
                            db9.delete(DatabaseContract.RaspunsTable.TABLE_NAME,DatabaseContract.RaspunsTable.COLUMN_NAME_IDQ + " = " + listaIdQdeSters.get(i),null);
                        }

                        //STERG INTRENARILE AFERENTE TESTULUI SELECTAT

                        DatabaseHelper helper10 = new DatabaseHelper(Teste.this);
                        SQLiteDatabase db10 = helper9.getWritableDatabase();

                        db10.delete(DatabaseContract.IntrebareTable.TABLE_NAME, DatabaseContract.IntrebareTable.COLUMN_NAME_IDTEST + " = " + idTestSelectat,null);

                        //STERG DIN BAZA TESTUL SELECTAT

                        DatabaseHelper helper = new DatabaseHelper(Teste.this);
                        SQLiteDatabase db3 = helper.getWritableDatabase();

                        db3.delete(DatabaseContract.TestTable.TABLE_NAME, DatabaseContract.TestTable.COLUMN_NAME_TITLU + "= '" + listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition) + "'",null);

                        //pt refresh automat la activitate
                        finish();
                        startActivity(getIntent());

                       // expListView.getSelectedPosition()
                        Toast.makeText(getApplicationContext(), listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition) + " STERS", Toast.LENGTH_SHORT).show();
                    }
                });

                Toast.makeText(getApplicationContext(), listDataHeader.get(groupPosition) + " : " + listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();
                return false;
            }

        });

    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 2 && resultCode == RESULT_OK) {
            ClasaTest ts = (ClasaTest) data.getSerializableExtra("ts");

            listaTitluri = new ArrayList<>();

            map = new HashMap<>();
            map.put(ts.categorie, ts.titlu);
            listaTitluri.add(ts.titlu);

            //    populareChildList();

            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }
    }

    public void navigateToCreareTest(View view){
        Intent intent = new Intent(Teste.this, CreareTest.class);
        startActivityForResult(intent,2);
    }


}

