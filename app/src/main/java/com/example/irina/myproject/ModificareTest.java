package com.example.irina.myproject;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.irina.myproject.contracts.DatabaseContract;
import com.example.irina.myproject.helpers.DatabaseHelper;

import java.sql.SQLData;
import java.util.ArrayList;

public class ModificareTest extends AppCompatActivity {

    ListView lv;
    Spinner categorii;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter2;

    ArrayList<String> listaQ;

    EditText titlu;
    EditText timpTest;
    TextView categorie;
    ImageButton btnSave;
    ImageButton btnCancel;
    ImageButton btnSetari;
    Button btnAddQ;

    int idTestSelectat =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificare_test);

        titlu = (EditText) findViewById(R.id.editTextTitluModificareTest);
        timpTest = (EditText) findViewById(R.id.editTextTimpTestModificareTest);
        categorie = (TextView) findViewById(R.id.tv_Categorie);
        btnSave = (ImageButton) findViewById(R.id.buttonSaveModificareTest);
        btnCancel = (ImageButton) findViewById(R.id.cancel_btnModificareTest);
        btnSetari = (ImageButton) findViewById(R.id.system_btnModificareTest);
        btnAddQ = (Button) findViewById(R.id.buttonModificareTest);

        DatabaseHelper helper = new DatabaseHelper(ModificareTest.this);
        SQLiteDatabase db = helper.getReadableDatabase();

        SharedPreferences sp = getApplication().getSharedPreferences("idTestM", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        idTestSelectat = sp.getInt("idTM", 0);


        //PREIAU DATELE DESPRE TESTUL SELECTAT
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseContract.TestTable.TABLE_NAME + " WHERE " + DatabaseContract.TestTable.COLUMN_NAME_ID + " = " + idTestSelectat, null);

        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(DatabaseContract.TestTable.COLUMN_NAME_TITLU);
            titlu.setText(cursor.getString(index));

            index = cursor.getColumnIndex(DatabaseContract.TestTable.COLUMN_NAME_CATEGORIE);
            categorie.setText(cursor.getString(index));

            index = cursor.getColumnIndex(DatabaseContract.TestTable.COLUMN_NAME_TIMP);
            int nr = cursor.getInt(index);
            StringBuilder sb = new StringBuilder();
            sb.append(nr);
            timpTest.setText(sb.toString());
        }

        //PREIAU INTREBARILE AFERENTE TESTULUI

        SQLiteDatabase dbq = helper.getReadableDatabase();

        Cursor cursorq = dbq.rawQuery("SELECT * FROM " + DatabaseContract.IntrebareTable.TABLE_NAME + " WHERE " + DatabaseContract.IntrebareTable.COLUMN_NAME_IDTEST + " = " + idTestSelectat, null);

        listaQ = new ArrayList<>();
        while (cursorq.moveToNext()) {
            int index = cursorq.getColumnIndex(DatabaseContract.IntrebareTable.COLUMN_NAME_TEXT);
            listaQ.add(cursorq.getString(index));
        }

        System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
        System.out.println("ID TEST SELECTAT: " + idTestSelectat);
        System.out.println(listaQ.size());
        for(int i=0;i<listaQ.size();i++){
            System.out.println(listaQ.get(i));
        }
        System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");

        lv = (ListView) findViewById(R.id.listViewIntrebariModificareTest);

        adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listaQ);
        lv.setAdapter(adapter2);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ModificareTest.this, ModificaIntrebare.class);
                intent.putExtra("Q_selectat", listaQ.get(position).toString());
                intent.putExtra("idTestMSelectat",idTestSelectat);
                startActivityForResult(intent,3);
            }
        });

        btnSetari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModificareTest.this,ModificareSetariTest.class);
                intent.putExtra("idTestMSelectat",idTestSelectat);
                startActivity(intent);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sp = getApplication().getSharedPreferences("setariTestModif",MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();

                DatabaseHelper helper3 = new DatabaseHelper(ModificareTest.this);
                SQLiteDatabase db3 = helper3.getWritableDatabase();

                ContentValues valori = new ContentValues();

                valori.put(DatabaseContract.TestTable.COLUMN_NAME_TITLU,titlu.getText().toString());
                valori.put(DatabaseContract.TestTable.COLUMN_NAME_TIMP, timpTest.getText().toString());
                valori.put(DatabaseContract.TestTable.COLUMN_NAME_NRRULARI,sp.getInt("nrRulariModif",1));

                db3.update(DatabaseContract.TestTable.TABLE_NAME,valori,DatabaseContract.TestTable.COLUMN_NAME_ID + " = " + idTestSelectat,null);

                Intent intent = new Intent();
                finish();
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


   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if((requestCode == 1 || requestCode == 3) && resultCode == RESULT_OK) {
            System.out.println("REQUEST CODE= " + requestCode);
            System.out.println("DAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAa");
            incarcareIntrebari();
        }
    }

   public void incarcareIntrebari() {

        //PUN INTR-O LISTA TITLURILE INTREBARILOR DIN BD
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        DatabaseHelper hp = new DatabaseHelper(ModificareTest.this);
        SQLiteDatabase dbq = hp.getReadableDatabase();

       Cursor cursorq = dbq.rawQuery("SELECT * FROM " + DatabaseContract.IntrebareTable.TABLE_NAME + " WHERE " + DatabaseContract.IntrebareTable.COLUMN_NAME_IDTEST + " = " + idTestSelectat, null);

       listaQ = new ArrayList<>();
       while (cursorq.moveToNext()) {
           int index = cursorq.getColumnIndex(DatabaseContract.IntrebareTable.COLUMN_NAME_TEXT);
           listaQ.add(cursorq.getString(index));
       }

       System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
       System.out.println("ID TEST SELECTAT: " + idTestSelectat);
       System.out.println(listaQ.size());
       for(int i=0;i<listaQ.size();i++){
           System.out.println(listaQ.get(i));
       }
       System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");

       adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listaQ);
       lv.setAdapter(adapter2);

       lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Intent intent = new Intent(ModificareTest.this, ModificaIntrebare.class);
               intent.putExtra("Q_selectat", listaQ.get(position).toString());
               intent.putExtra("idTestMSelectat",idTestSelectat);
               startActivityForResult(intent,3);
           }
       });
    }

    public void navigateToAddQ(View view){
        Intent intent = new Intent(ModificareTest.this, CreareIntrebare.class);
        intent.putExtra("idTestMSelectat",idTestSelectat);
        startActivityForResult(intent,1);
    }

}
