package com.example.irina.myproject.adaptor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.irina.myproject.ClasaTest;
import com.example.irina.myproject.R;

import org.w3c.dom.Text;

import java.util.List;

public class AdaptorRaportIntrebari extends ArrayAdapter<ClasaTest> {
    public AdaptorRaportIntrebari(@NonNull Context context, int resource, @NonNull List<ClasaTest> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.adaptor_raport_raspunsuri,null);
        }
        ClasaTest test = getItem(position);

        TextView intrebare = convertView.findViewById(R.id.textViewIntrebare);
        intrebare.setText(test.titlu);
        TextView raspuns = convertView.findViewById(R.id.textViewRaspuns);
        raspuns.setText(test.categorie);
        return convertView;
    }
}
