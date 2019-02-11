package com.example.irina.myproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.irina.myproject.adaptor.AdaptorRaportIntrebari;

import java.util.ArrayList;

public class RaportIntrebari extends AppCompatActivity {
    ListView listView;
    AdaptorRaportIntrebari adaptor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raport_intrebari);

        ArrayList<ClasaTest> lista = new ArrayList<>();

        listView = findViewById(R.id.listViewIntrebari);
        adaptor = new AdaptorRaportIntrebari(getApplicationContext(),R.layout.adaptor_raport_raspunsuri,lista);
        listView.setAdapter(adaptor);
    }
}
