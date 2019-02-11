package com.example.irina.myproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class HomeStudent extends AppCompatActivity {

    TextView showEmail;
    TextView showDate;

    // contecare la firebase
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference("date_logare");
    //DatabaseReference ref2 = database.getReference("date_logare");

    public HomeStudent() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String email;
                String date;
                Intent intent = getIntent();

                email = intent.getStringExtra("username");
                String emailFirebase = email.replace(".","");

                HashMap<String, String> query = (HashMap<String, String>) dataSnapshot.child("student").child(emailFirebase).getValue();

                showEmail = (TextView)findViewById(R.id.showEmail);
                showDate=(TextView)findViewById(R.id.showDate);
                showEmail.setText(query.get("username_student"));
                showDate.setText(query.get("data_student"));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_student);
    }


    public void navigateToViewProfile(View view) {
        Intent intent = new Intent(HomeStudent.this, ViewProfilStudent.class);
        startActivity(intent);
    }

    public void navigateToStartTest(View view) {
        Intent intent = new Intent(HomeStudent.this, StartTest.class);
        startActivity(intent);
    }

    public void navigateToRezultatetTeste(View view) {
        Intent intent = new Intent(HomeStudent.this, RezultateTeste.class);
        startActivity(intent);
    }

}