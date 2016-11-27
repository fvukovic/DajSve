package com.example.filip.dajsve.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.filip.dajsve.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import entities.Ponuda;

/**
 * Created by Helena on 23.11.2016..
 */

public class DetaljiPonudeFragment extends android.support.v4.app.Fragment {

    private ImageView ponudaSlika;
    private TextView ponudaNaziv;
    private TextView ponudaDescription;
    private TextView ponudaCijena;
    private TextView ponudaPopust;
    private TextView ponudaOriginal;
    private TextView ponudaDatum;
    private TextView ponudaUsteda;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.detalji_ponude_fragment, container, false);
        ponudaSlika=(ImageView)rootView.findViewById(R.id.ponuda_image);
        ponudaNaziv=(TextView) rootView.findViewById(R.id.ponuda_name);
        ponudaDescription=(TextView)rootView.findViewById(R.id.ponuda_description);
        ponudaCijena=(TextView)rootView.findViewById(R.id.ponuda_cijena);
        ponudaPopust=(TextView)rootView.findViewById(R.id.ponuda_popust);
        ponudaOriginal=(TextView)rootView.findViewById(R.id.ponuda_original);
        ponudaDatum=(TextView)rootView.findViewById(R.id.ponuda_datum);
        ponudaUsteda=(TextView)rootView.findViewById(R.id.ponuda_usteda);


        Bundle bundle = getArguments();
        ArrayList<Ponuda> listaDohvacena = bundle.getParcelableArrayList("ponuda");
        Ponuda ponudaDohvacena = listaDohvacena.get(0);

        Context context=ponudaSlika.getContext();
        Picasso.with(context).load(ponudaDohvacena.getURL()).into(ponudaSlika);
        ponudaNaziv.setText(ponudaDohvacena.getNaziv());
        ponudaDescription.setText(ponudaDohvacena.getTekstPonude());
        ponudaCijena.setText("cijena=" + ponudaDohvacena.getCijena());
        ponudaPopust.setText("popust="+(Integer.toString(ponudaDohvacena.getPopust())));
        ponudaOriginal.setText("stara cijena=" + (Integer.toString(ponudaDohvacena.getCijenaOriginal())));
        ponudaDatum.setText("datum=" + ponudaDohvacena.getDatumPonude());
        ponudaUsteda.setText("usteda="+Integer.toString(ponudaDohvacena.getUsteda()));

        return rootView;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
}

