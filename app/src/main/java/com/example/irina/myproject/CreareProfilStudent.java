package com.example.irina.myproject;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.irina.myproject.contracts.DatabaseContract;
import com.example.irina.myproject.helpers.DatabaseHelper;

import static java.lang.Integer.parseInt;

public class CreareProfilStudent extends AppCompatActivity {

    EditText username, email,parola,confirmaParola, tv_grupa ,seria;
    Spinner anStudiu;
    Button loginBtn;

    private Context _context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creare_profil_student);

        username = (EditText) findViewById(R.id.tv_username);
        email = (EditText) findViewById(R.id.tv_email);
        parola = (EditText) findViewById(R.id.tv_parola);
        confirmaParola =(EditText) findViewById(R.id.tv_confirma_pass);
        tv_grupa = (EditText) findViewById(R.id.tv_grupa);
        seria = (EditText) findViewById(R.id.tv_seria);
        anStudiu = (Spinner) findViewById(R.id.spinnerAn);
        loginBtn = (Button) findViewById(R.id.btnLogin);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              /*  SharedPreferences sp = getApplication().getSharedPreferences("profilStudent", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("pref_user", username.getText().toString());
                editor.putString("pref_email", email.getText().toString());
                editor.putString("pref_grupa", tv_grupa.getText().toString());
                editor.putString("pref_serie", seria.getText().toString());
                editor.apply();
*/
               //***********************************************************************************

                    DatabaseHelper helper = new DatabaseHelper(CreareProfilStudent.this);
                    SQLiteDatabase db = helper.getWritableDatabase();

                    ContentValues inregistrare = new ContentValues();

                    inregistrare.put(DatabaseContract.StudentTable.COLUMN_NAME_USERNAME, username.getText().toString());
                    inregistrare.put(DatabaseContract.StudentTable.COLUMN_NAME_EMAIL, email.getText().toString());
                    inregistrare.put(DatabaseContract.StudentTable.COLUMN_NAME_PAROLA, parola.getText().toString());
                    inregistrare.put(DatabaseContract.StudentTable.COLUMN_NAME_AN, anStudiu.getSelectedItem().toString());
                    inregistrare.put(DatabaseContract.StudentTable.COLUMN_NAME_GRUPA, parseInt( tv_grupa.getText().toString()));
                    inregistrare.put(DatabaseContract.StudentTable.COLUMN_NAME_SERIE, seria.getText().toString());

                    db.insert(DatabaseContract.StudentTable.TABLE_NAME,null,inregistrare);

                // *********************************************************************************

                if(validare()){
                    if(parola.getText().toString().equals(confirmaParola.getText().toString())){
                        Intent intent = new Intent(CreareProfilStudent.this, Login.class);
                        startActivity(intent);
                        finish();
                    }else{
                        parola.setError("Introduceti corect parola!");
                        confirmaParola.setText("");
                    }
                }
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
        if(parola.getText().toString().isEmpty()){
            parola.setError("Introduceti o parla!");
            valid = false;
        }
        if(confirmaParola.getText().toString().isEmpty()){
            confirmaParola.setError("Confirmati parola!");
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

}
