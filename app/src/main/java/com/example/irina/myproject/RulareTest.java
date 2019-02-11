package com.example.irina.myproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.irina.myproject.contracts.DatabaseContract;
import com.example.irina.myproject.helpers.DatabaseHelper;

import java.util.ArrayList;

public class RulareTest extends AppCompatActivity {

    TextView R1, R2, R3, R4 , intrbare;
    CheckBox c1,c2,c3,c4;

    Button btnNext;

    int pin;
    int OK=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rulare_test);

       String ceva = getIntent().getStringExtra("pinTest");
        pin = Integer.parseInt(ceva);

        intrbare = (TextView) findViewById(R.id.editText_intrebareR);
        R1 = (TextView) findViewById(R.id.editText_R1_R);
        R2 = (TextView) findViewById(R.id.editText_R2_R);
        R3 = (TextView) findViewById(R.id.editText_R3_R);
        R4 = (TextView) findViewById(R.id.editText_R4_R);
        btnNext = (Button) findViewById(R.id.buttonRulareNext);
        c1 =(CheckBox) findViewById(R.id.checkBox1R);
        c2 =(CheckBox) findViewById(R.id.checkBox2R);
        c3 =(CheckBox) findViewById(R.id.checkBox3R);
        c4 =(CheckBox) findViewById(R.id.checkBox4R);

        DatabaseHelper helper = new DatabaseHelper(RulareTest.this);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursorQ = db.rawQuery("SELECT " + DatabaseContract.IntrebareTable.COLUMN_NAME_TEXT + ", " + DatabaseContract.IntrebareTable.COLUMN_NAME_IDQ + " FROM " + DatabaseContract.IntrebareTable.TABLE_NAME +
        " WHERE " + DatabaseContract.IntrebareTable.COLUMN_NAME_IDTEST + " = " + pin,null);



        while(cursorQ.moveToNext() && OK == 1){

            OK=0;

            int index = cursorQ.getColumnIndex(DatabaseContract.IntrebareTable.COLUMN_NAME_TEXT);
            intrbare.setText(cursorQ.getString(index)); // setez intrebarea

            index = cursorQ.getColumnIndex(DatabaseContract.IntrebareTable.COLUMN_NAME_IDQ);
            int idQ = cursorQ.getInt(index);

            ArrayList<String> listaR = new ArrayList<>();
            int[] raspCorecte = new int[4];
            for(int i=0;i<4;i++){
                raspCorecte[i]=0;
            }

            SQLiteDatabase dbR = helper.getReadableDatabase();

            Cursor cursorR = dbR.rawQuery("SELECT " + DatabaseContract.RaspunsTable.COLUMN_NAME_TEXT + ", " + DatabaseContract.RaspunsTable.COLUMN_NAME_VERIFICARE + " FROM " +
                    DatabaseContract.RaspunsTable.TABLE_NAME + " WHERE " + DatabaseContract.RaspunsTable.COLUMN_NAME_IDQ + " = " + idQ,null);

            int nr=0;
            while(cursorR.moveToNext()){
                int indexR = cursorR.getColumnIndex(DatabaseContract.RaspunsTable.COLUMN_NAME_TEXT);
                listaR.add(cursorR.getString(indexR));

                int indexR1 = cursorR.getColumnIndex(DatabaseContract.RaspunsTable.COLUMN_NAME_VERIFICARE);
                int verif = cursorR.getInt(indexR1);
                if(verif==1){
                    raspCorecte[nr] = 1;
                }
                else{
                    raspCorecte[nr] = 0;
                }

                nr++;
            }
            //dupa ultimul while am lista de rasp si vectorul de verificari pt intrebari pt o intrebare din cursor

            R1.setText(listaR.get(0));
            R2.setText(listaR.get(1));
            R3.setText(listaR.get(2));
            R4.setText(listaR.get(3));

            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        Intent intent = new Intent(RulareTest.this, Corect.class);
                        startActivity(intent);
                }
            });
        }
    }
}
