package com.example.irina.myproject.adaptor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.irina.myproject.R;
import com.example.irina.myproject.models.Galerie;

import java.util.List;

public class GalerieAdaptor extends ArrayAdapter<Galerie> {
    public GalerieAdaptor(@NonNull Context context, int resource, @NonNull List<Galerie> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.activity_imagini_adapter,null);
        }

        Galerie galerie = getItem(position);

        ImageView imageView = convertView.findViewById(R.id.imageViewPoza);
        imageView.setImageBitmap(galerie.imagine);
        TextView numeArtist = convertView.findViewById(R.id.textViewAutor);
        numeArtist.setText(galerie.numeArtist);
        TextView siteArtist = convertView.findViewById(R.id.textViewSite);
        siteArtist.setText(galerie.siteArtist);

        return convertView;
    }
}

