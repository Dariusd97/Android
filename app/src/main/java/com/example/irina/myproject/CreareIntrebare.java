package com.example.irina.myproject;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.irina.myproject.contracts.DatabaseContract;
import com.example.irina.myproject.helpers.DatabaseHelper;

import java.util.ArrayList;

public class CreareIntrebare extends AppCompatActivity {

    Intrebare intr;

    public static int idQ;
    public static int codR;
    public static int idTest;

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
    ImageView img;

    ArrayList<String> variante = new ArrayList<String>();
    int[] raspCorecte = new int[4];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creare_intrebare);

        img= (ImageView) findViewById(R.id.coverImage);
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


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int nrRaspCorecte =0;

                variante.add(R1.getText().toString());
                variante.add(R2.getText().toString());
                variante.add(R3.getText().toString());
                variante.add(R4.getText().toString());

                for(int i=0;i<4;i++){
                    raspCorecte[i] = 0;
                }

                if(c1.isChecked()){
                    raspCorecte[0] =1;
                    nrRaspCorecte++;
                }
                if(c2.isChecked()){
                    raspCorecte[1] =1;
                    nrRaspCorecte++;
                }
                if(c3.isChecked()){
                    raspCorecte[2] =1;
                    nrRaspCorecte++;
                }
                if(c4.isChecked()){
                    raspCorecte[3] =1;
                    nrRaspCorecte++;
                }

                //PREIA CODUL TESTULUI CARE CONTINE INTREBARILE DE MAI JOS

                SharedPreferences sp13 = getApplication().getSharedPreferences("idpastrat", MODE_PRIVATE);
                SharedPreferences.Editor editor13 = sp13.edit();
              //  idTest = sp13.getInt("id_pastrat", 0);

                idTest = getIntent().getIntExtra("idTestMSelectat",0);

                // ADAUGA INTREBARI IN TABELA INTREBARE

                if(validare(nrRaspCorecte)){

                    SharedPreferences sp9 = getApplication().getSharedPreferences("idpastratQ", MODE_PRIVATE);
                    SharedPreferences.Editor editor9 = sp9.edit();
                    idQ = sp9.getInt("id_pastratQ", 0);

                    DatabaseHelper helper = new DatabaseHelper(CreareIntrebare.this);
                    SQLiteDatabase db = helper.getWritableDatabase();

                    ContentValues inregistrare = new ContentValues();
                    ContentValues raspunsuri = new ContentValues();

                    idQ=idQ+1;

                    inregistrare.put(DatabaseContract.IntrebareTable.COLUMN_NAME_IDQ, idQ);
                    inregistrare.put(DatabaseContract.IntrebareTable.COLUMN_NAME_TEXT,intrebare.getText().toString());
                    inregistrare.put(DatabaseContract.IntrebareTable.COLUMN_NAME_TIMP,spTimp.getSelectedItem().toString());
                    inregistrare.put(DatabaseContract.IntrebareTable.COLUMN_NAME_TIP,spTip.getSelectedItem().toString());
                    inregistrare.put(DatabaseContract.IntrebareTable.COLUMN_NAME_IDTEST,idTest);
                    inregistrare.put(DatabaseContract.IntrebareTable.COLUMN_NAME_PUNCTAJ,20);
                    inregistrare.put(DatabaseContract.IntrebareTable.COLUMN_NAME_ORIGIN, "manual");

                    db.insert(DatabaseContract.IntrebareTable.TABLE_NAME,null,inregistrare);

                    System.out.println("((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((");
                    System.out.println("ID: " + idQ);
                    System.out.println("TEXT: " + intrebare.getText().toString());
                    System.out.println("Timp: " + spTimp.getSelectedItem().toString());
                    System.out.println("TIP: " + spTip.getSelectedItem().toString());
                    System.out.println("ID TEST: " + idTest);
                    System.out.println("PUNCTAJ: " + 20);
                    System.out.println("((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((");

                    SharedPreferences sp10 = getApplication().getSharedPreferences("idpastratQ", MODE_PRIVATE);
                    SharedPreferences.Editor editor10 = sp10.edit();
                    editor10.putInt("id_pastratQ", idQ);
                    editor10.apply();


                    //ADAUGA RASPUNSURI IN TABELA RASPUNS
                    for(int i=0;i<4;i++) {

                        SharedPreferences sp11 = getApplication().getSharedPreferences("codpastrat", MODE_PRIVATE);
                        SharedPreferences.Editor editor11 = sp11.edit();
                        codR = sp11.getInt("cod_pastrat", 0);

                        codR=codR+1;
                        raspunsuri.put(DatabaseContract.RaspunsTable.COLUMN_NAME_COD,codR);

                        raspunsuri.put(DatabaseContract.RaspunsTable.COLUMN_NAME_TEXT, variante.get(i));

                        if(raspCorecte[i]==1) {
                            raspunsuri.put(DatabaseContract.RaspunsTable.COLUMN_NAME_VERIFICARE, 1);
                        }else{
                            raspunsuri.put(DatabaseContract.RaspunsTable.COLUMN_NAME_VERIFICARE, 0);
                        }
                        raspunsuri.put(DatabaseContract.RaspunsTable.COLUMN_NAME_IDQ, idQ);
                        raspunsuri.put(DatabaseContract.RaspunsTable.COLUMN_NAME_ORIGIN, "origin");

                        db.insert(DatabaseContract.RaspunsTable.TABLE_NAME,null,raspunsuri);

                        System.out.println("-+-----+-+-+-+-+-+-+-+-+-+-++-+-+-+-+-+-+-+-+-+-+-+-+-+--+-+-+-+--+-+-+-+-+-+-+");
                        System.out.println("COD: " + codR);
                        System.out.println("Text_R: " + variante.get(i));
                        System.out.println("Verificare: " + raspCorecte[i]);
                        System.out.println("ID_Q:" + idQ);
                        System.out.println("-+-----+-+-+-+-+-+-+-+-+-+-++-+-+-+-+-+-+-+-+-+-+-+-+-+--+-+-+-+--+-+-+-+-+-+-+");


                        SharedPreferences sp12 = getApplication().getSharedPreferences("codpastrat", MODE_PRIVATE);
                        SharedPreferences.Editor editor12 = sp12.edit();
                        editor12.putInt("cod_pastrat", codR);
                        editor12.apply();

                    }



                    SharedPreferences sp = getApplication().getSharedPreferences("intrebare", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("pref_intrebare", intrebare.getText().toString());
                    editor.putString("pref_R1", R1.getText().toString());
                    editor.putString("pref_R2", R2.getText().toString());
                    editor.putString("pref_R3", R3.getText().toString());
                    editor.putString("pref_R4", R4.getText().toString());
                    editor.putString("pref_tip",spTip.getSelectedItem().toString());
                    editor.putString("pref_timp",spTimp.getSelectedItem().toString());
                    editor.apply();

                    intr = new Intrebare(intrebare.getText().toString(),raspCorecte,variante,spTip.getSelectedItem().toString());
                    Intent intent = new Intent();
                    intent.putExtra("intrebare",intr);

                    intent.putExtra("checked",raspCorecte);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }


    public boolean validare(int nrRaspCorecte){

        boolean valid = true;

        if(intrebare.getText().toString().isEmpty()){
            intrebare.setError("Introduceti o intrebare!");
            valid =false;
        }
        if(R1.getText().toString().isEmpty()){
            R1.setError("Intrpduceti un raspuns!");
            valid= false;
        }
        if(R2.getText().toString().isEmpty()){
            R2.setError("Intrpduceti un raspuns!");
            valid= false;
        }
        if(R3.getText().toString().isEmpty()){
            R3.setError("Intrpduceti un raspuns!");
            valid= false;
        }
        if(R4.getText().toString().isEmpty()){
            R4.setError("Intrpduceti un raspuns!");
            valid= false;
        }
        if(nrRaspCorecte==0){
            Toast.makeText(this,"Setati un raspuns ca varianta corecta!",Toast.LENGTH_LONG).show();
            valid = false;
        }
        if(spTip.getSelectedItem().toString().equals("Raspuns unic") && (nrRaspCorecte>1 || nrRaspCorecte==0)){
            Toast.makeText(this,"Setati un singur raspuns ca varianta corecta!",Toast.LENGTH_LONG).show();
            valid = false;
        }
        if(spTip.getSelectedItem().toString().equals("Raspuns multiplu") && (nrRaspCorecte <=1)){
            Toast.makeText(this,"Setati cel putin doua raspunsuri ca variante corecte!",Toast.LENGTH_LONG).show();
            valid = false;
        }
        return valid;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1 && resultCode==RESULT_OK);

        byte[] galerie =data.getByteArrayExtra("poza");
        Bitmap decodeByte = BitmapFactory.decodeByteArray(galerie,0,galerie.length);
        img.setImageBitmap(decodeByte);
    }

    public void navigateToGallery(View view){
        Intent intent = new Intent(CreareIntrebare.this, GaleriePoze.class);
        startActivityForResult(intent,1);
       // startActivity(intent);
    }

}
