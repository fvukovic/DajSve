package com.example.filip.dajsve.Fragments;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.core.DataLoadedListener;
import com.example.filip.dajsve.R;
import java.util.ArrayList;
import java.util.List;

import entities.Grad;
import entities.Ponuda;
/**
 * Created by Filip on 28.10.2016..
 */

public class SvePonudeFragment extends Fragment implements DataLoadedListener {

    private RecyclerView rv;
    public  RVAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sve_ponude_fragment, container, false);
        rv = (RecyclerView) rootView.findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        if(Ponuda.getAll().isEmpty()){

        Ponuda nova =new Ponuda(1,null,1,1,1,"nema podataka",1,null,null,null);
        List<Ponuda> novaLista= new ArrayList<Ponuda>();
        novaLista.add(nova);
        RVAdapter adapter = new RVAdapter(novaLista);
        rv.setAdapter(adapter);}
        else {
            ArrayList<Ponuda> novaLista= new ArrayList<Ponuda>();
            novaLista= (ArrayList<Ponuda>) Ponuda.getAll();
            RVAdapter adapter = new RVAdapter(novaLista);
            rv.setAdapter(adapter);
        }
        return rootView;
    }


    @Override
    public void onDataLoaded(List<Grad> gradovi, List<Ponuda> ponude) {
         ArrayList<Ponuda> ponudaArrayList = new ArrayList<Ponuda>();
        for (Ponuda ponuda : ponude) {
            ponudaArrayList.add(ponuda);
        }
        RVAdapter adapter = new RVAdapter(ponudaArrayList);
        this.adapter= adapter;
        getFragmentManager()
                .beginTransaction()
                .attach(this)
                .commit();

    }

}

