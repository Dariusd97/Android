package com.example.irina.myproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.irina.myproject.adaptor.PersonListAdapter;

import java.util.ArrayList;
import java.util.Random;

public class StartEchipe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_echipe);

        ListView mListView = (ListView)findViewById(R.id.listView);

       ArrayList<String> listaPersoane= (ArrayList<String>) getIntent().getStringArrayListExtra("participanti");
       int nrEchipe = (int) getIntent().getSerializableExtra("nrEchipe");

       System.out.println("=======================================");
       for(int i =0;i<listaPersoane.size();i++){
           System.out.println(listaPersoane.get(i).toString());
       }
        System.out.println("=======================================");
       System.out.println("NR ECHiPE: " + nrEchipe);


        ArrayList<ArrayList<String>> listaFinala = new ArrayList<>();

        listaFinala = generareEchipe(listaPersoane,nrEchipe);

        ArrayList<Echipa> listaEchipe = new ArrayList<>();

        ArrayList<String> listaMembrii;
        for(int i=0;i<listaFinala.size();i++){

            listaMembrii = new ArrayList<>();
            listaMembrii = listaFinala.get(i);

            Echipa e = new Echipa("echipa " + (i+1),listaMembrii);
            listaEchipe.add(e);
        }

        PersonListAdapter adapter = new PersonListAdapter(this,R.layout.adapter_view_layout,listaEchipe);
        mListView.setAdapter(adapter);

    }


    public ArrayList<ArrayList<String>> generareEchipe(ArrayList<String> listaPersoane, int numarEchipe){
        Random random  = new Random();

        // int nrEchipe = parseInt(numarEchipe);
        int nrEchipe = numarEchipe;
        ArrayList<String> item = new ArrayList<>();
        ArrayList<ArrayList<String>> listaFinala = new ArrayList<>();

        int nrInscrisi = listaPersoane.size();

        int nrMembrii = listaPersoane.size() / nrEchipe;

        int restPersoane = nrInscrisi%nrEchipe;

        int copie = nrInscrisi;

        for(int i=0 ;i< copie ;i++) {

            if(nrInscrisi > restPersoane){

                while(item.size() < nrMembrii) {
                    String participant = listaPersoane.get(random.nextInt(listaPersoane.size()));
                    item.add(participant);
                    listaPersoane.remove(participant);
                    nrInscrisi--;
                }
                listaFinala.add(item);
                item = new ArrayList<>();
            }else {

                if(restPersoane==0) {
                    break;
                }
                for(int j=0;j< restPersoane;j++) {


                    item.add(listaPersoane.get(j));
                }
                for(int a=0;a<item.size();a++) {

                    ArrayList<String> copieLista = listaFinala.get(a);
                    copieLista.add(item.get(a));
                }
                break;
            }
        }
        return listaFinala;
    }

    public void navigateToRating(View view){
        Intent intent = new Intent(StartEchipe.this, Rating.class);
        startActivity(intent);
    }

}
