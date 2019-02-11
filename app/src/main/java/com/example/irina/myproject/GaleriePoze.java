package com.example.irina.myproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.irina.myproject.adaptor.GalerieAdaptor;
import com.example.irina.myproject.models.Galerie;
import com.example.irina.myproject.workers.GalerieWorker;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class GaleriePoze extends AppCompatActivity {

    private ListView listView;
    public static ArrayList<Galerie> galerieList = new ArrayList<>();
    public static GalerieAdaptor galerieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galerie_poze);

        galerieAdapter = new GalerieAdaptor(getApplicationContext(),R.layout.activity_imagini_adapter,galerieList);
        listView = findViewById(R.id.listViewImagini);
        listView.setAdapter(galerieAdapter);

       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Galerie poza = new Galerie(galerieList.get(position).imagine);
                Intent intent2 = new Intent();
                ByteArrayOutputStream bao = new ByteArrayOutputStream();
                poza.imagine.compress(Bitmap.CompressFormat.JPEG,100,bao);
                byte[] b = bao.toByteArray();
                intent2.putExtra("poza", b);
                setResult(RESULT_OK,intent2);
                finish();
            }
        });

        Intent intent = getIntent();
        if(intent!= null){
            String categorie = "question";

            GalerieWorker worker = new GalerieWorker();
            worker.execute(categorie);
        }
    }
}
