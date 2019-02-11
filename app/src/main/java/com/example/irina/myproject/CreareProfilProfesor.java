package com.example.irina.myproject;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.irina.myproject.contracts.DatabaseContract;
import com.example.irina.myproject.helpers.DatabaseHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CreareProfilProfesor extends AppCompatActivity {

    int OK=1;
    EditText user, email,parola, confParola;
    Button btnLogin;
    ArrayList<String> listaMaterii;
    Button mSelect;

    TextView mMaterieSelected;
    String[] listItems; //lista initiala cu toate materiile
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>(); // lista cu ce am selectat, pozitia din interiorul listei care a fost selectata

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creare_profil_profesor);

        user = (EditText) findViewById(R.id.tv_username);
        email = (EditText) findViewById(R.id.tv_email);
        parola = (EditText) findViewById(R.id.tv_parola);
        confParola = (EditText) findViewById(R.id.tv_confirma_pass);
        btnLogin = (Button) findViewById(R.id.buttonLogin);

        mSelect =(Button) findViewById(R.id.BtnSelect);
        mMaterieSelected =(TextView) findViewById(R.id.materieSelected);

        listItems = getResources().getStringArray(R.array.materii_item);
        checkedItems = new boolean[listItems.length]; //cate items avem in listItems


        mSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mbuilder = new AlertDialog.Builder(CreareProfilProfesor.this);
                mbuilder.setTitle(R.string.dialog_titlu);
                mbuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                        if(isChecked){
                            if(!mUserItems.contains(position)){
                                mUserItems.add(position);
                            }
                        }
                        else if(mUserItems.contains(position)){
                            mUserItems.remove((Integer)position);
                        }

                    }
                });
                mbuilder.setCancelable(false);
                mbuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int a) {
                        String item="";
                        for(int i=0;i<mUserItems.size();i++){ // trecem prin arraylist
                            item = item + listItems[mUserItems.get(i)]; //se retine materia care a fost selelctata (parcurg lista originala din care extrag numele de la pozitia din lista mUserItems
                            if(i != mUserItems.size()-1)
                                item =item + ", ";
                        }

                        mMaterieSelected.setText(item);

                        listaMaterii = new ArrayList<String>(Arrays.asList(item.split(", ")));


 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                        DatabaseHelper helperm = new DatabaseHelper(CreareProfilProfesor.this);
                        SQLiteDatabase db2 = helperm.getWritableDatabase();

                        ContentValues cv = new ContentValues();

                        for(int m=0;m<listaMaterii.size();m++){
                           cv.put(DatabaseContract.RandMaterieTable.COLUMN_NAME_EMAIL,email.getText().toString());
                           cv.put(DatabaseContract.RandMaterieTable.COLUMN_NAME_DESCRIERE,listaMaterii.get(m));
                           cv.put(DatabaseContract.RandMaterieTable.COLUMN_NAME_ORIGINE,"manual");

                           db2.insert(DatabaseContract.RandMaterieTable.TABLE_NAME,null,cv);
                        }



///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                        System.out.println("***************************************************************");
                        for(int m=0;m<listaMaterii.size();m++){
                            System.out.println(listaMaterii.get(m));
                        }
                        System.out.println("***************************************************************");


                        //##########################################################################################

                        SharedPreferences sp = getApplication().getSharedPreferences("materii", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        Set<String> set = new HashSet<>();
                        for(String s: listaMaterii){
                            if(!set.contains(s)){
                                set.add(s);
                            }
                        }
                        editor.putStringSet("mat",set);
                        editor.commit();

                        //########################################################################################
                    }


                });

                mbuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                mbuilder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int a) {
                        for(int i=0;i<checkedItems.length;i++){
                            checkedItems[i] = false; // false inseamna unchecked tot
                            mUserItems.clear();
                            mMaterieSelected.setText("");
                            OK=0;
                        }
                    }
                });

                AlertDialog mDialog = mbuilder.create();
                mDialog.show();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseHelper helper = new DatabaseHelper(CreareProfilProfesor.this);
                SQLiteDatabase db = helper.getWritableDatabase();

                ContentValues inregistrare = new ContentValues();

                inregistrare.put(DatabaseContract.ProfesorTable.COLUMN_NAME_USERNAME, user.getText().toString());
                inregistrare.put(DatabaseContract.ProfesorTable.COLUMN_NAME_EMAIL, email.getText().toString());
                inregistrare.put(DatabaseContract.ProfesorTable.COLUMN_NAME_PAROLA, parola.getText().toString());
                inregistrare.put(DatabaseContract.ProfesorTable.COLUMN_NAME_ORIGIN, "manual");

                db.insert(DatabaseContract.ProfesorTable.TABLE_NAME,null,inregistrare);

                if(validare()){
                    if(parola.getText().toString().equals(confParola.getText().toString())){

                        Intent intent1 = new Intent(CreareProfilProfesor.this, Login.class);
                        intent1.putExtra("checked",checkedItems);
                        startActivity(intent1);
                        finish();
                    }
                    else{
                        parola.setText("");
                        parola.setError("Introduceti corect parola!");
                        confParola.setText("");
                    }
                }
            }
        });

    }

    public boolean validare(){
        boolean valid = true;
        if(user.getText().toString().isEmpty()){
            user.setError("Introduceti un username!");
            valid = false;
        }
        if(email.getText().toString().isEmpty()){
            email.setError("Inroduceti adresa de email!");
            valid = false;
        }
        if(parola.getText().toString().isEmpty()){
            parola.setError("Introduceti o parola!");
            valid = false;
        }
        if(confParola.getText().toString().isEmpty()){
            confParola.setError("Confirmati parola!");
            valid= false;
        }
        if(mMaterieSelected.getText().toString().isEmpty() || OK==0){
            mMaterieSelected.setFocusableInTouchMode(true);
            mMaterieSelected.requestFocus();
            mMaterieSelected.setError("Selectati cel putin o materie");
            valid =false;
            OK=1;
        }
        else{
            mMaterieSelected.setError(null);
        }

        return valid;
    }
}
