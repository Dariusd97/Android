package com.example.irina.myproject.adaptor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.irina.myproject.Echipa;
import com.example.irina.myproject.R;

import java.util.ArrayList;

public class PersonListAdapter extends ArrayAdapter<Echipa> {


    public PersonListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Echipa> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater =LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.adapter_view_layout,null);
        }
        ArrayList<String> listaMembrii = getItem(position).getListaMembrii();


        TextView tvParticipanti = (TextView)convertView.findViewById(R.id.TextViewParticipanti);
        TextView tvNumeEchipe =(TextView)convertView.findViewById(R.id.TextViewNumeEchipe);
        String item ="";
        for(int i=0 ;i< listaMembrii.size();i++) {

            item = item + listaMembrii.get(i) + "\n";


        }

        tvParticipanti.setText(item.toString());
        tvNumeEchipe.setText( getItem(position).getNumeEchipa());

        return convertView;
    }
}
