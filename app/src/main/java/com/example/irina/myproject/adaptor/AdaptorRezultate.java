package com.example.irina.myproject.adaptor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.irina.myproject.ClasaTest;
import com.example.irina.myproject.R;

import java.util.ArrayList;
import java.util.List;

public class AdaptorRezultate extends ArrayAdapter<ClasaTest> {

    public AdaptorRezultate(@NonNull Context context, int resource, @NonNull List<ClasaTest> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.adapator_rezultate,null);
        }

        ClasaTest ts = getItem(position);

        System.out.println("===========================================");
        System.out.println(ts.toString() + "~~~~" + ts.getPunctaj());
        System.out.println("===========================================");
        TextView tvTest = (TextView)convertView.findViewById(R.id.textViewTest);
        TextView tvPunctaj = (TextView)convertView.findViewById(R.id.textViewPunctaj);

        String punctaj = ts.AfisarePunctaj();

        tvTest.setText(ts.toString());
        tvPunctaj.setText(punctaj);

        return convertView;
    }
}
