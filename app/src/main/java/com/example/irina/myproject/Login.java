package com.example.irina.myproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.irina.myproject.contracts.DatabaseContract;
import com.example.irina.myproject.helpers.DatabaseHelper;
import com.example.irina.myproject.workers.IntrebareWorker;
import com.example.irina.myproject.workers.ProfesorWorker;
import com.example.irina.myproject.workers.RandMaterieWorker;
import com.example.irina.myproject.workers.RaspunsWorker;
import com.example.irina.myproject.workers.TestStudentWorker;
import com.example.irina.myproject.workers.TestWorker;
import com.example.irina.myproject.workers.UserWorker;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Login extends AppCompatActivity {

    RadioButton rbprof;
    RadioButton rbstud;
    RadioGroup rgroup;
    Button loginbtn;
    EditText user, pass;

    // accesare firebase
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();

    // scriere in firebase
    public void writeNewData(String username, String data){
        String valid = username.replace(".","");
        databaseReference.child("date_logare").child("student").child(valid).child("data_student").setValue(data);
        databaseReference.child("date_logare").child("student").child(valid).child("username_student").setValue(username);
    }

    public void writeNewDataProfesor(String username, String data){
        String valid = username.replace(".","");
        databaseReference.child("date_logare").child("profesor").child(valid).child("data_profesor").setValue(data);
        databaseReference.child("date_logare").child("profesor").child(valid).child("username_profesor").setValue(username);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //workers
        UserWorker userWorker = new UserWorker(Login.this);
        userWorker.execute();
        ProfesorWorker profesorWorker = new ProfesorWorker(Login.this);
        profesorWorker.execute();
        TestWorker worker = new TestWorker(Login.this);
        worker.execute();

        IntrebareWorker intrebareWorker = new IntrebareWorker(Login.this);
        intrebareWorker.execute();

        RaspunsWorker raspunsWorker = new RaspunsWorker(Login.this);
        raspunsWorker.execute();

        RandMaterieWorker randMaterieWorker = new RandMaterieWorker(Login.this);
        randMaterieWorker.execute();

        TestStudentWorker testStudentWorker = new TestStudentWorker(Login.this);
        testStudentWorker.execute();

        rbprof = (RadioButton) findViewById(R.id.radioButtonProf);
        rbstud = (RadioButton) findViewById(R.id.radioButtonStud);
        rgroup = (RadioGroup) findViewById(R.id.radioGroup);
        loginbtn = (Button) findViewById(R.id.loginButton);
        user = (EditText) findViewById(R.id.user_text);
        pass = (EditText) findViewById(R.id.pass_text);


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseHelper helper = new DatabaseHelper(Login.this);
                SQLiteDatabase db = helper.getReadableDatabase();

                if (rbstud.isChecked()) {
                    Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseContract.StudentTable.TABLE_NAME, null);

                    if(cursor.getCount() == 0) {
                        Toast.makeText(getBaseContext(), "Nu aveti cont! ", Toast.LENGTH_SHORT).show();

                    }
                    else{
                    int OK = 0;

                    while (cursor.moveToNext()) {
                        int index = cursor.getColumnIndex(DatabaseContract.StudentTable.COLUMN_NAME_EMAIL);
                        int index2 = cursor.getColumnIndex(DatabaseContract.StudentTable.COLUMN_NAME_PAROLA);

                        System.out.println("*******************************************");
                        System.out.println(cursor.getString(index));
                        System.out.println(cursor.getString(index2));
                        System.out.println(OK);
                        System.out.println("*******************************************");

                        if (validare() == true) {
                            if (user.getText().toString().equals(cursor.getString(index)) && pass.getText().toString().equals(cursor.getString(index2))) {
                                OK = 1;

                                // get current date
                                Date currentTime = Calendar.getInstance().getTime();
                                SimpleDateFormat df = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss ");
                                String formattedDate = df.format(currentTime);

                                // write to firebase
                                writeNewData(user.getText().toString(),formattedDate);

                                Intent explicitintent = new Intent(Login.this, HomeStudent.class);
                                // send to home student in order to check in firebase
                                explicitintent.putExtra("username",user.getText().toString());

                                startActivity(explicitintent);
                            }
                        }

                        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                        System.out.println(OK);
                        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

                        if (OK == 1) {
                            SharedPreferences sp = getApplication().getSharedPreferences("emaillogare", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("pref_email", user.getText().toString());
                            editor.apply();

                            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                            System.out.println(user.getText().toString());
                            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

                            break;
                        }
                    }

                    if (OK == 0) {
                        Toast.makeText(getBaseContext(), "Adresa de email sau parola au fost introduse gresit", Toast.LENGTH_SHORT).show();
                    }
                }
                }
                else if(rbprof.isChecked()) {

                    Cursor cursor2 = db.rawQuery("SELECT EMAIL,PAROLA FROM " + DatabaseContract.ProfesorTable.TABLE_NAME, null);

                    if (cursor2.getCount() == 0) {
                        Toast.makeText(getBaseContext(), "Nu aveti cont! ", Toast.LENGTH_SHORT).show();
                    }
                    else{
                    int OK2 = 0;

                    while (cursor2.moveToNext()) {
                        int index3 = cursor2.getColumnIndex(DatabaseContract.ProfesorTable.COLUMN_NAME_EMAIL);
                        int index4 = cursor2.getColumnIndex(DatabaseContract.ProfesorTable.COLUMN_NAME_PAROLA);

                        System.out.println("*******************************************");
                        System.out.println(cursor2.getString(index3));
                        System.out.println(cursor2.getString(index4));
                        System.out.println(OK2);
                        System.out.println("*******************************************");

                        if (validare() == true) {
                            if (user.getText().toString().equals(cursor2.getString(index3)) && pass.getText().toString().equals(cursor2.getString(index4))) {
                                OK2 = 1;

                                // get current date

                                Date currentTime = Calendar.getInstance().getTime();
                                SimpleDateFormat df = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss ");
                                String formattedDate = df.format(currentTime);

                                // write to firebase
                                writeNewDataProfesor(user.getText().toString(),formattedDate);

                                Intent explicitintent = new Intent(Login.this, HomeProfesor.class);
                                // send to home student in order to check in firebase
                                explicitintent.putExtra("username",user.getText().toString());
                                startActivity(explicitintent);
                            }
                        }

                        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                        System.out.println(OK2);
                        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

                        if (OK2 == 1) {

                            SharedPreferences sp = getApplication().getSharedPreferences("emaillogareprof", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("pref_emailprof", user.getText().toString());
                            editor.apply();
                            break;
                        }
                    }

                    if (OK2 == 0) {
                        Toast.makeText(getBaseContext(), "Adresa de email sau parola au fost introduse gresit", Toast.LENGTH_SHORT).show();
                    }
                }
                }
                else {
                    Toast.makeText(getBaseContext(), "Selectati profilul!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public boolean validare(){
        boolean valid = true;

        if(user.getText().toString().isEmpty()){
            user.setError("Introduceti adresa de email!");
            valid = false;
        }
        if(pass.getText().toString().isEmpty()){
            pass.setError("Introduceti parola!");
            valid = false;
        }
        return valid;
    }

    public void navigateToSignUp(View view) {
        Intent explicitIntent = new Intent(Login.this, AlegeProfil.class);
        startActivity(explicitIntent);
    }
}
