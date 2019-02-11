package com.example.irina.myproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.irina.myproject.adaptor.AdaptorRaport;
import com.example.irina.myproject.contracts.DatabaseContract;
import com.example.irina.myproject.helpers.DatabaseHelper;

import java.util.ArrayList;

public class RapoarteProfesor extends AppCompatActivity {

    private ListView listView;
    private static ArrayList<ClasaTest> listaTeste = new ArrayList<>();
    private static AdaptorRaport adaptorRaport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rapoarte_profesor);

        listView = findViewById(R.id.listViewTeste);
        final ArrayList<ClasaTest> listaTeste = new ArrayList<>();

        DatabaseHelper helper = new DatabaseHelper(RapoarteProfesor.this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseContract.TestTable.TABLE_NAME + " WHERE "+ DatabaseContract.TestTable.COLUMN_NAME_ORIGIN + " like 'json'" , null);

        while(cursor.moveToNext()){
            int index = cursor.getColumnIndex(DatabaseContract.TestTable.COLUMN_NAME_TITLU);
            ClasaTest test = new ClasaTest();
            test.titlu=cursor.getString(index);
            listaTeste.add(test);
        }

        AdaptorRaport adaptorRaport = new AdaptorRaport(getApplicationContext(),R.layout.activity_afisare_teste,listaTeste);
        listView.setAdapter(adaptorRaport);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(RapoarteProfesor.this,RaportPeTest.class);
                intent.putExtra("id_test",listaTeste.get(position).titlu);
                startActivity(intent);
            }
        });
    }
}

