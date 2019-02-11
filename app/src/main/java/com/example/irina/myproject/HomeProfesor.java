package com.example.irina.myproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.irina.myproject.workers.UserWorker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeProfesor extends AppCompatActivity {


    ArrayList<String> listaMaterii;
    boolean[] checkedItems;
    TextView showEmail;
    TextView showDate;
    Button btnTeste;
    // contecare la firebase
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference("date_logare");
    //DatabaseReference ref2 = database.getReference("date_logare");

    public HomeProfesor() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String email;
                String date;
                Intent intent = getIntent();

                email = intent.getStringExtra("username");
                String emailFirebase = email.replace(".","");

                HashMap<String, String> query = (HashMap<String, String>) dataSnapshot.child("profesor").child(emailFirebase).getValue();
                System.out.println("query = " + query);
                showEmail = (TextView)findViewById(R.id.showEmailProfesor);
                showDate=(TextView)findViewById(R.id.showDateProfesor);
                showEmail.setText(query.get("username_profesor"));
                showDate.setText(query.get("data_profesor"));
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
        setContentView(R.layout.activity_home_profesor);

        btnTeste = (Button) findViewById(R.id.button3);

        btnTeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeProfesor.this, Teste.class);
                startActivity(intent);
            }
        });

        listaMaterii=(ArrayList<String>) getIntent().getSerializableExtra("materii1");
        checkedItems=(boolean[]) getIntent()    .getSerializableExtra("checked");

    }

    public void navigateToViewProfileProfesor(View view){
        Intent intent1 = new Intent(HomeProfesor.this, ViewProfilProfesor.class);
        intent1.putStringArrayListExtra("materii2",listaMaterii);
        intent1.putExtra("checked",checkedItems);
        startActivity(intent1);
    }
    /*
        public void navigateToTeste(View view){
            Intent intent = new Intent(HomeProfesor.this, Teste.class);
            startActivity(intent);
        }
    */
    public void navigateToRapoarte(View view){
        Intent intent = new Intent(HomeProfesor.this, RapoarteProfesor.class);
        startActivity(intent);
    }
}
