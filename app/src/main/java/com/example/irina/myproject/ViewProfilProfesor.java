package com.example.irina.myproject;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.irina.myproject.contracts.DatabaseContract;
import com.example.irina.myproject.helpers.DatabaseHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ViewProfilProfesor extends AppCompatActivity {


   // private static int nrModificari = 0;
    EditText user, email;

    Button mSelect;
    TextView mMaterieSelected;
    String[] listItems; //lista initiala cu toate materiile
    boolean[] checkedItems;
    ArrayList<String> listaMaterii;
    ArrayList<Integer> mUserItems = new ArrayList<>(); // lista cu ce am selectat, pozitia din interiorul listei care a fost selectata

    Button btnSave;
    Button btnCancel;

    ArrayList<String> listaMaterii2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profil_profesor);


        user = (EditText) findViewById(R.id.tv_username);
        email = (EditText) findViewById(R.id.tv_email);
        mSelect = (Button) findViewById(R.id.BtnSelect);
        mMaterieSelected = (TextView) findViewById(R.id.materieSelected);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnCancel = (Button) findViewById(R.id.btnCancel);


        listItems = getResources().getStringArray(R.array.materii_item);
        checkedItems = new boolean[listItems.length];
      //  checkedItems = (boolean[]) getIntent().getSerializableExtra("checked");
      //  System.out.println("~~~~~~~~~~~~~~~~~~~~~" + checkedItems.length + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        //==========================================================================================================

        SharedPreferences sp3 = getApplication().getSharedPreferences("emaillogareprof", MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sp3.edit();
        String email_logare = sp3.getString("pref_emailprof", "");

        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        System.out.println(email_logare);
        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");

        DatabaseHelper helper = new DatabaseHelper(ViewProfilProfesor.this);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SElECT * FROM " + DatabaseContract.ProfesorTable.TABLE_NAME + " WHERE EMAIL like '" + email_logare + "'", null);

        while (cursor.moveToNext()) {
            Profesor prof = new Profesor();

            int index = cursor.getColumnIndex(DatabaseContract.ProfesorTable.COLUMN_NAME_USERNAME);
            prof.setUsername(cursor.getString(index));
            user.setText(prof.getUsername());

            index = cursor.getColumnIndex(DatabaseContract.ProfesorTable.COLUMN_NAME_EMAIL);
            prof.setEmail(cursor.getString(index));
            email.setText(prof.getEmail());
        }

        //==========================================================================================================


        DatabaseHelper helper2 = new DatabaseHelper(ViewProfilProfesor.this);
        SQLiteDatabase db2 = helper2.getReadableDatabase();

        Cursor cursor2 = db2.rawQuery("SELECT * FROM " + DatabaseContract.RandMaterieTable.TABLE_NAME + " WHERE " + DatabaseContract.RandMaterieTable.COLUMN_NAME_EMAIL + " like '" + email_logare + "'", null);


        ArrayList<String> lm = new ArrayList<>();
        while (cursor2.moveToNext()) {
            int index2 = cursor2.getColumnIndex(DatabaseContract.RandMaterieTable.COLUMN_NAME_DESCRIERE);
            lm.add(cursor2.getString(index2));

            index2 = cursor2.getColumnIndex(DatabaseContract.RandMaterieTable.COLUMN_NAME_EMAIL);
            System.out.println("------------------  " + cursor2.getString(index2) + "  -------------------------");
        }

        System.out.println("########################################################");
        System.out.println(cursor2.getCount());
        for (int q = 0; q < lm.size(); q++) {
            System.out.println(lm.get(q));
        }
        System.out.println("########################################################");

        String item="";

        for (int q = 0; q < lm.size(); q++) {
            item=item + lm.get(q);

            if (q != lm.size() - 1)
                item = item + ", ";
        }

        mMaterieSelected.setText(item);

        for(int i=0;i<listItems.length;i++){
            for(int j=0;j<lm.size();j++){
                if(listItems[i].equals(lm.get(j))) {
                    checkedItems[i]=true;
                }
            }
        }

            mSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder mbuilder = new AlertDialog.Builder(ViewProfilProfesor.this);
                    mbuilder.setTitle(R.string.dialog_titlu);
                    mbuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {


                        @Override
                        public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                            if (isChecked) {
                                if (!mUserItems.contains(position)) {
                                    mUserItems.add(position);
                                }
                            } else if (mUserItems.contains(position)) {
                                mUserItems.remove((Integer) position);
                            }

                        }
                    });
                    mbuilder.setCancelable(false);
                  /*  mbuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int a) {
                            String item = "";
                            for (int i = 0; i < mUserItems.size(); i++) { // trecem prin arraylist
                                item = item + listItems[mUserItems.get(i)]; //se retine materia care a fost selelctata (parcurg lista originala din care extrag numele de la pozitia din lista mUserItems
                                if (i != mUserItems.size() - 1)
                                    item = item + ", ";
                            }
                            mMaterieSelected.setText(item);
                        }

                    });*/

                    mbuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                 /*   mbuilder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int a) {
                            for (int i = 0; i < checkedItems.length; i++) {
                                checkedItems[i] = false; // false inseamna unchecked tot
                                mUserItems.clear();
                                mMaterieSelected.setText(" ");
                            }
                        }
                    });*/

                    AlertDialog mDialog = mbuilder.create();
                    mDialog.show();
                }
            });

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (validare()) {

                        SharedPreferences sp3 = getApplication().getSharedPreferences("emaillogareprof", MODE_PRIVATE);
                        SharedPreferences.Editor editor2 = sp3.edit();
                        String email_logare = sp3.getString("pref_emailprof", "");


                        //SALVARE MODIFICARI IN BAZA DE DATE

                        DatabaseHelper helper = new DatabaseHelper(ViewProfilProfesor.this);
                        SQLiteDatabase db3 = helper.getWritableDatabase();

                        ContentValues valori = new ContentValues();
                        ContentValues val = new ContentValues();
                       val.put(DatabaseContract.RandMaterieTable.COLUMN_NAME_EMAIL,email.getText().toString());
                       valori.put(DatabaseContract.ProfesorTable.COLUMN_NAME_EMAIL,email.getText().toString());
                       valori.put(DatabaseContract.ProfesorTable.COLUMN_NAME_USERNAME,user.getText().toString());

                        db3.update(DatabaseContract.ProfesorTable.TABLE_NAME,valori,DatabaseContract.ProfesorTable.COLUMN_NAME_EMAIL + " like '" + email_logare + "'",null);
                        db3.update(DatabaseContract.RandMaterieTable.TABLE_NAME,val,DatabaseContract.RandMaterieTable.COLUMN_NAME_EMAIL + " like '" + email_logare + "'",null);

                        SharedPreferences sp4 = getApplication().getSharedPreferences("emaillogareprof", MODE_PRIVATE);
                        SharedPreferences.Editor editor4 = sp4.edit();
                        editor4.putString("pref_emailprof", email.getText().toString());
                        editor4.apply();


                        listaMaterii = new ArrayList<String>(Arrays.asList(mMaterieSelected.getText().toString().split(", ")));

                        //##########################################################################################
                        SharedPreferences sp2 = getApplication().getSharedPreferences("materiiView", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp2.edit();
                        Set<String> set2 = new HashSet<>();
                        for (String s : listaMaterii) {
                            if (!set2.contains(s)) {
                                set2.add(s);
                            }
                        }
                        editor.putStringSet("matView", set2);
                        editor.commit();
                        //########################################################################################

                    //    nrModificari++;

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

        public void LogOut (View view){
            Intent intent = new Intent(ViewProfilProfesor.this, Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            this.finish();
        }

        int OK = 1;
        public boolean validare () {
            boolean valid = true;
            if (user.getText().toString().isEmpty()) {
                user.setError("Introduceti un username!");
                valid = false;
            }
            if (email.getText().toString().isEmpty()) {
                email.setError("Inroduceti adresa de email!");
                valid = false;
            }

            if (mMaterieSelected.getText().toString().isEmpty() || OK == 0) {
                mMaterieSelected.setFocusableInTouchMode(true);
                mMaterieSelected.requestFocus();
                mMaterieSelected.setError("Selectati cel putin o materie");
                valid = false;
                OK = 1;
            } else {
                mMaterieSelected.setError(null);
            }

            return valid;
        }
}


