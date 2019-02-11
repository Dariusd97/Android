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
import android.widget.TextView;

import com.example.irina.myproject.contracts.DatabaseContract;
import com.example.irina.myproject.helpers.DatabaseHelper;

public class ViewProfilStudent extends AppCompatActivity {

    EditText username, email, tv_grupa ,seria;
    Spinner anStudiu, limba;
    Button btnSave;
    Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profil_student);

        username = (EditText) findViewById(R.id.tv_username);
        email = (EditText) findViewById(R.id.tv_email);
        limba =(Spinner) findViewById(R.id.spinnerLimba);
        tv_grupa = (EditText) findViewById(R.id.tv_grupa);
        seria = (EditText) findViewById(R.id.tv_serie);
        anStudiu = (Spinner) findViewById(R.id.spinnerAn);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnCancel = (Button) findViewById(R.id.btnCancel);


     /*  SharedPreferences sp = getApplication().getSharedPreferences("profilStudent", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        username.setText(sp.getString("pref_user",""));
        email.setText(sp.getString("pref_email",""));
        tv_grupa.setText(sp.getString("pref_grupa",""));
        seria.setText(sp.getString("pref_serie","")); */



       //*******************************************************************************************

        SharedPreferences sp2 = getApplication().getSharedPreferences("emaillogare", MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sp2.edit();
       String email_logare = sp2.getString("pref_email","");

        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        System.out.println(email_logare);
        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");

        DatabaseHelper helper = new DatabaseHelper(ViewProfilStudent.this);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT USERNAME,EMAIL,AN,GRUPA,SERIE FROM " + DatabaseContract.StudentTable.TABLE_NAME + " WHERE EMAIL like '" + email_logare + "'" , null);

        while(cursor.moveToNext()){
            User student = new User();
            int index = cursor.getColumnIndex(DatabaseContract.StudentTable.COLUMN_NAME_USERNAME);
            student.setUsername(cursor.getString(index));

            username.setText(student.getUsername());

            index = cursor.getColumnIndex(DatabaseContract.StudentTable.COLUMN_NAME_EMAIL);
            student.setEmail(cursor.getString(index));

            email.setText(student.getEmail());

            index = cursor.getColumnIndex(DatabaseContract.StudentTable.COLUMN_NAME_AN);
            student.setAnStudiu(cursor.getString(index));

            index = cursor.getColumnIndex(DatabaseContract.StudentTable.COLUMN_NAME_GRUPA);
            student.setGrupa(cursor.getInt(index));

            StringBuilder sb = new StringBuilder();
            sb.append(student.getGrupa());
            tv_grupa.setText(sb.toString());

            index = cursor.getColumnIndex(DatabaseContract.StudentTable.COLUMN_NAME_SERIE);
            student.setSerie(cursor.getString(index));

            seria.setText(student.getSerie());



        }

        //*******************************************************************************************

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validare()){
                    SharedPreferences sp3 = getApplication().getSharedPreferences("emaillogare", MODE_PRIVATE);
                    SharedPreferences.Editor editor2 = sp3.edit();
                    String email_logare = sp3.getString("pref_email", "");

                    DatabaseHelper helper = new DatabaseHelper(ViewProfilStudent.this);
                    SQLiteDatabase db3 = helper.getWritableDatabase();

                    ContentValues valori = new ContentValues();

                    valori.put(DatabaseContract.StudentTable.COLUMN_NAME_USERNAME,username.getText().toString());
                    valori.put(DatabaseContract.StudentTable.COLUMN_NAME_EMAIL,email.getText().toString());
                    valori.put(DatabaseContract.StudentTable.COLUMN_NAME_GRUPA,tv_grupa.getText().toString());
                    valori.put(DatabaseContract.StudentTable.COLUMN_NAME_SERIE,seria.getText().toString());

                    db3.update(DatabaseContract.StudentTable.TABLE_NAME,valori,DatabaseContract.StudentTable.COLUMN_NAME_EMAIL + " like '" + email_logare + "'",null);

                    SharedPreferences sp4 = getApplication().getSharedPreferences("emaillogare", MODE_PRIVATE);
                    SharedPreferences.Editor editor4 = sp4.edit();
                    editor4.putString("pref_email",email.getText().toString());
                    editor4.apply();

                    Intent intent = new Intent();
                    finish();
                }

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public boolean validare(){
        boolean valid = true;

        if(username.getText().toString().isEmpty()){
            username.setError("Introduceti un username!");
            valid = false;
        }
        if(email.getText().toString().isEmpty()){
            email.setError("Introduceti adresa de email");
            valid = false;
        }
        if(tv_grupa.getText().toString().isEmpty()){
            tv_grupa.setError("Introduceti grupa!");
            valid = false;
        }
        if(seria.getText().toString().isEmpty()){
            seria.setError("Introduceti seria!");
            valid= false;
        }
        return valid;

    }

    public void LogOut(View view){
        Intent intent = new Intent(ViewProfilStudent.this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        this.finish();
    }

}
