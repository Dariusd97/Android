package com.example.irina.myproject.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.irina.myproject.ClasaTest;
import com.example.irina.myproject.R;

import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AdaptorRaportPeTest extends ArrayAdapter<String> {


    public AdaptorRaportPeTest(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
    }

    @android.support.annotation.NonNull
    @Override
    public View getView(int position, @android.support.annotation.Nullable View convertView, @android.support.annotation.NonNull ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.activity_afisare_teste,null);
        }

        String[] test = getItem(position).split(",");

        TextView textView = convertView.findViewById(R.id.textViewNumeTest);
        textView.setText(test[0]);
        TextView punctaj =convertView.findViewById(R.id.textViewPunctajTest);
        punctaj.setText(test[1]);

        return convertView;
    }
}
