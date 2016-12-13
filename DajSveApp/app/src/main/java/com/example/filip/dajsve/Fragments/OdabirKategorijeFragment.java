package com.example.filip.dajsve.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.filip.dajsve.Adapters.OdabirKategorijeAdapter;
import com.example.filip.dajsve.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entities.Kategorija;
import entities.OmiljenaKategorija;

/**
 * Created by Filip on 13.12.2016..
 */

public class OdabirKategorijeFragment extends android.support.v4.app.Fragment {
    private RecyclerView rv;
    public RVAdapter adapt;
    public Button gumbDalje;
    public CheckBox cb;
    private CardView cardViewKategorije;
    public OdabirKategorijeAdapter adapter;
    public View rootView;
    public View drugiView;
    List<Kategorija> novaLista;

    Map<Integer, String> kategorije = new HashMap<>();



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.odabir_kategorije_fragment, container, false);
        drugiView = inflater.inflate(R.layout.odabir_kategorije_item, container, false);
        cardViewKategorije = (CardView) drugiView.findViewById(R.id.card_view_kategorije);
        rv = (RecyclerView) rootView.findViewById(R.id.rv_kategorije);
        gumbDalje = (Button) rootView.findViewById(R.id.button_dalje);

        cb = (CheckBox) drugiView.findViewById(R.id.odabir_kategorije_checkbox);
        System.out.println("Checkbox" + cb);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        novaLista = Kategorija.getAll();
        Collections.shuffle(novaLista);
        adapter = new OdabirKategorijeAdapter(novaLista,getContext());
        rv.setAdapter(adapter);

        return rootView;
    }



        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);


            gumbDalje.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppCompatActivity activity = (AppCompatActivity) getContext();
                    android.support.v4.app.Fragment novePonude = new NovePonudeFragment();
                    Bundle bundle = new Bundle();


                    activity.getSupportFragmentManager().beginTransaction();
                    activity.getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.linearlayout, novePonude).commit();
                }
            });


        }


}
