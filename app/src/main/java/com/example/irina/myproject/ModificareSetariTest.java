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

import com.example.irina.myproject.contracts.DatabaseContract;
import com.example.irina.myproject.helpers.DatabaseHelper;

import static java.lang.Integer.parseInt;

public class ModificareSetariTest extends AppCompatActivity {

    Button btnCancel;
    Button btnApply;
    EditText nrRulari;
    int idTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificare_setari_test);

        btnCancel = (Button) findViewById(R.id.buttonCancelM);
        btnApply = (Button) findViewById(R.id.buttonApplyM);
        nrRulari = (EditText) findViewById(R.id.etNrRulariM);

        idTest = getIntent().getIntExtra("idTestMSelectat",0);

        DatabaseHelper helper = new DatabaseHelper(ModificareSetariTest.this);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseContract.TestTable.TABLE_NAME + " WHERE " + DatabaseContract.TestTable.COLUMN_NAME_ID + " = " + idTest,null);

        while(cursor.moveToNext()){
            int index = cursor.getColumnIndex(DatabaseContract.TestTable.COLUMN_NAME_NRRULARI);
            int nr = cursor.getInt(index);
            StringBuilder sb = new StringBuilder();
            sb.append(nr);
            nrRulari.setText(sb.toString());
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                finish();
            }
        });

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sp = getApplication().getSharedPreferences("setariTestModif", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("nrRulariModif", parseInt(nrRulari.getText().toString()));
                editor.apply();

                Intent intent = new Intent();
                finish();
            }
        });
    }
}
