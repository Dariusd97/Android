package com.example.irina.myproject;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Environment;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.irina.myproject.adaptor.AdaptorRezultate;
import com.example.irina.myproject.contracts.DatabaseContract;
import com.example.irina.myproject.helpers.DatabaseHelper;
import com.github.mikephil.charting.renderer.scatter.ChevronUpShapeRenderer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class RezultateTeste extends AppCompatActivity {

    Button btn_salvare_text;
    Button btn_salvare_csv;
    Button btn_grafic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezultate_teste);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_DENIED){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1000);
        }

        ListView mListView = (ListView) findViewById(R.id.listviewRezultate);

        DatabaseHelper helper1 = new DatabaseHelper(RezultateTeste.this);
        SQLiteDatabase db1 = helper1.getReadableDatabase();

        SharedPreferences sp2 = getApplication().getSharedPreferences("emaillogare", MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sp2.edit();
        String email_logare = sp2.getString("pref_email","");

        Cursor cursor2 = db1.rawQuery("SELECT " + DatabaseContract.TestStudentTable.COLUMN_NAME_PUNCTAJ + ", " + DatabaseContract.TestTable.COLUMN_NAME_TITLU + " FROM "
                + DatabaseContract.TestStudentTable.TABLE_NAME + ", " + DatabaseContract.TestTable.TABLE_NAME + " WHERE " + DatabaseContract.TestStudentTable.COLUMN_NAME_ID_TEST +
                " = " + DatabaseContract.TestTable.COLUMN_NAME_ID + " AND " + DatabaseContract.TestStudentTable.COLUMN_NAME_EMAIL_STUDENT + " like '" + email_logare + "'",null);

        final ArrayList<ClasaTest> listaTeteEfectuate = new ArrayList<>();

        ClasaTest test;
        while(cursor2.moveToNext()){
            int index = cursor2.getColumnIndex(DatabaseContract.TestStudentTable.COLUMN_NAME_PUNCTAJ);
            int index2 = cursor2.getColumnIndex(DatabaseContract.TestTable.COLUMN_NAME_TITLU);
            test = new ClasaTest(cursor2.getString(index2),cursor2.getInt(index));
            listaTeteEfectuate.add(test);
        }


        AdaptorRezultate adaptorRezultate = new AdaptorRezultate(this, R.layout.adapator_rezultate,listaTeteEfectuate);
        mListView.setAdapter(adaptorRezultate);

        btn_grafic = findViewById(R.id.btn_grafic_student);
        btn_grafic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RezultateTeste.this, ChartActivity.class);

                startActivity(intent);
            }
        });



        //
        btn_salvare_text = (Button)findViewById(R.id.btn_salvare_text_student);
        btn_salvare_csv=(Button)findViewById(R.id.btn_salvare_csv_student);

        btn_salvare_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"raportTest.txt");
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    for(ClasaTest test : listaTeteEfectuate){
                        fileOutputStream.write(test.afisare().getBytes());
                    }
                    fileOutputStream.close();
                    Toast.makeText(getApplicationContext(),"File saved to "+getFilesDir()+"/"+"RaportTest",Toast.LENGTH_LONG).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"File not found",Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Error saving",Toast.LENGTH_SHORT).show();
                }


            }
        });

        btn_salvare_csv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"raportTest.csv");
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    for(ClasaTest test : listaTeteEfectuate){
                        fileOutputStream.write(test.afisare().getBytes());
                    }
                    fileOutputStream.close();
                    Toast.makeText(getApplicationContext(),"File saved to "+getFilesDir()+"/"+"RaportTest",Toast.LENGTH_LONG).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"File not found",Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Error saving",Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case 1000:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"Permission granted",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this,"Permission not granted",Toast.LENGTH_SHORT).show();
                    finish();
                }


        }
    }
/*
    public void navigateToGrafic(View view){

        Intent intent = new Intent(RezultateTeste.this, ChartActivity.class);
        startActivity(intent);
    }
    */
}
