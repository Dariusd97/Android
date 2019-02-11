package com.example.irina.myproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.irina.myproject.contracts.DatabaseContract;
import com.example.irina.myproject.helpers.DatabaseHelper;

public class StartTest extends AppCompatActivity {

    Button btnGo;
    EditText pin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_test);

        pin = (EditText) findViewById(R.id.editTextPIN);
        btnGo = (Button) findViewById(R.id.button12);
    }

    public void navigateToRulareTest(View view){
        Intent intent = new Intent(StartTest.this, RulareTest.class) ;
        intent.putExtra("pinTest",pin.getText().toString());
        startActivity(intent);
    }


}
