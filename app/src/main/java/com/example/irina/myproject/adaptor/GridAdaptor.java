package com.example.irina.myproject.adaptor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.irina.myproject.R;
import com.example.irina.myproject.User;

import java.util.ArrayList;

public class GridAdaptor extends ArrayAdapter<User> {


    public GridAdaptor(@NonNull Context context, int resource, @NonNull ArrayList<User> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater =LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.activity_grid_layout,null);
        }
        String participant= getItem(position).getUsername();

        TextView numeParticipanti = (TextView)convertView.findViewById(R.id.TextViewNumeParticipant);
        numeParticipanti.setText(participant.toString());

        return convertView;
    }
}
