package com.example.irina.myproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.irina.myproject.adaptor.GridAdaptor;
import com.example.irina.myproject.contracts.DatabaseContract;
import com.example.irina.myproject.helpers.DatabaseHelper;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class StartIndividual extends AppCompatActivity {

    TextView nrEchipe;
    Button btnEchipe;
    TextView pin;
    TextView nrParticipanti;
    Button btnGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_individual);

        GridView mGridView = (GridView) findViewById(R.id.gridView);

        btnEchipe = (Button) findViewById(R.id.btnEchipe);
        pin = (TextView) findViewById((R.id.et_pin));
        nrParticipanti = (TextView) findViewById(R.id.etNrParticipanti);
        btnGo = (Button) findViewById(R.id.button13);

        SharedPreferences sp9 = getApplication().getSharedPreferences("pinTest", MODE_PRIVATE);
        SharedPreferences.Editor editor9 = sp9.edit();
        int nr = sp9.getInt("pinT",0);

        StringBuilder sb = new StringBuilder();
        sb.append(nr);
        pin.setText(sb.toString());

        final ArrayList<User> listaPersoane = new ArrayList<>();


        DatabaseHelper helper = new DatabaseHelper(StartIndividual.this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseContract.StudentTable.TABLE_NAME + " WHERE "+ DatabaseContract.StudentTable.COLUMN_NAME_ORIGIN + " like 'json'" , null);



        while(cursor.moveToNext()){
            int index = cursor.getColumnIndex(DatabaseContract.StudentTable.COLUMN_NAME_USERNAME);
            User user = new User();
            user.setUsername(cursor.getString(index));
            listaPersoane.add(user);
        }

        final ArrayList<String> listaParticipanti = new ArrayList<>();

        for (int i = 0; i < listaPersoane.size(); i++) {
            listaParticipanti.add(listaPersoane.get(i).getUsername());
        }

        StringBuilder sb2 = new StringBuilder();
        sb2.append(listaPersoane.size());
        nrParticipanti.setText(sb2.toString());

        GridAdaptor adapter = new GridAdaptor(this, R.layout.activity_grid_layout, listaPersoane);
        mGridView.setAdapter(adapter);

        btnEchipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    final AlertDialog.Builder mBuiledr = new AlertDialog.Builder(StartIndividual.this);
                    View mView = getLayoutInflater().inflate(R.layout.dialog_nrechipe, null);
                    final EditText nrEchipe = (EditText) mView.findViewById(R.id.etNrEchipe);
                    Button btnCancel = (Button) mView.findViewById(R.id.buttonCancelM);
                    Button btnOK = (Button) mView.findViewById(R.id.buttonOK);

                    btnOK.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!nrEchipe.getText().toString().isEmpty()) {
                                Intent intent = new Intent(StartIndividual.this, StartEchipe.class);
                                intent.putStringArrayListExtra("participanti", listaParticipanti);
                                intent.putExtra("nrEchipe", parseInt(nrEchipe.getText().toString()));
                                startActivity(intent);
                            } else {
                                Toast.makeText(StartIndividual.this, "Introduceti numarul de echipe!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                    mBuiledr.setView(mView);
                    final AlertDialog dialog = mBuiledr.create();

                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();

            }

        });
    }

    public void navigateToRating(View view){
        Intent intent = new Intent(StartIndividual.this, Rating.class);
        startActivity(intent);
    }
}
