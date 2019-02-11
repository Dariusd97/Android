package com.example.irina.myproject;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.irina.myproject.adaptor.AdaptorRaport;
import com.example.irina.myproject.adaptor.AdaptorRaportPeTest;
import com.example.irina.myproject.contracts.DatabaseContract;
import com.example.irina.myproject.helpers.DatabaseHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class RaportPeTest extends AppCompatActivity {

    private ListView listView;
    TextView nume_test;
    private static AdaptorRaportPeTest adaptorRaport;
    Button btn_salvare_text;
    Button btn_salvare_csv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raport_pe_test);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_DENIED){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1000);
        }


        listView = findViewById(R.id.listviewRezultatePeTeste);
        nume_test=findViewById(R.id.textView_nume_test_raport_test);
        // receive the title of the selected test
        String title = getIntent().getStringExtra("id_test");
        nume_test.setText(title);

        DatabaseHelper helper = new DatabaseHelper(RaportPeTest.this);
        SQLiteDatabase db = helper.getReadableDatabase();

        //RETIN ID-UL TESTULUI SELECTAT
        Cursor cursorPunctaje = db.rawQuery("SELECT * FROM " + DatabaseContract.TestTable.TABLE_NAME + " WHERE " + DatabaseContract.TestTable.COLUMN_NAME_TITLU + " like '" + title + "'",null);

        int idTestSelectat=0;
        while(cursorPunctaje.moveToNext()){
            int index = cursorPunctaje.getColumnIndex(DatabaseContract.TestTable.COLUMN_NAME_ID);
            idTestSelectat = cursorPunctaje.getInt(index);
        }

        //IAU DIN BAZA PUNCTAJUL SI USERNAME_STUDENT

        Cursor cursor2 = db.rawQuery("SELECT " + DatabaseContract.TestStudentTable.COLUMN_NAME_PUNCTAJ + ", " + DatabaseContract.StudentTable.COLUMN_NAME_USERNAME + " FROM "
                + DatabaseContract.TestStudentTable.TABLE_NAME + ", " + DatabaseContract.StudentTable.TABLE_NAME + " WHERE " + DatabaseContract.TestStudentTable.COLUMN_NAME_EMAIL_STUDENT +
        " = " + DatabaseContract.StudentTable.COLUMN_NAME_EMAIL + " AND " + DatabaseContract.TestStudentTable.COLUMN_NAME_ID_TEST + " = " + idTestSelectat,null);

        ArrayList<Integer> punctaje = new ArrayList<>();
        ArrayList<String> studenti = new ArrayList<>();

        while(cursor2.moveToNext()){
            int index = cursor2.getColumnIndex(DatabaseContract.TestStudentTable.COLUMN_NAME_PUNCTAJ);
            punctaje.add(cursor2.getInt(index));

            int index2 = cursor2.getColumnIndex(DatabaseContract.StudentTable.COLUMN_NAME_USERNAME);
            studenti.add(cursor2.getString(index2));
        }

        final ArrayList<String> listaFinala = new ArrayList<>();

        for(int i=0; i<studenti.size();i++){
            listaFinala.add(studenti.get(i)+","+punctaje.get(i));
        }

        AdaptorRaportPeTest adaptorRaport = new AdaptorRaportPeTest(getApplicationContext(),R.layout.activity_raport_pe_test,listaFinala);
        listView.setAdapter(adaptorRaport);

        btn_salvare_text = (Button)findViewById(R.id.btn_salvare_text_profesor);
        btn_salvare_csv=(Button)findViewById(R.id.btn_salvare_csv_profesor);
        btn_salvare_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"raportPeTest.txt");
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    for(String test : listaFinala){
                        test+="\n";
                        fileOutputStream.write(test.getBytes());
                    }
                    fileOutputStream.close();
                    Toast.makeText(getApplicationContext(),"File saved to "+getFilesDir()+"/"+"RaportPeTest",Toast.LENGTH_LONG).show();
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

                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"raportPeTest.csv");
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    for(String test : listaFinala){
                        test+="\n";
                        fileOutputStream.write(test.getBytes());
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
}

