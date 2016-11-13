package com.example.filip.dajsve.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.filip.dajsve.R;
import com.example.webservice.WebServiceCaller;

import java.util.List;

import entities.Ponuda;

/**
 * Created by Filip on 28.10.2016..
 */

public class SvePonudeFragment extends Fragment {

    /*WebServiceCaller wsCaller = new WebServiceCaller();

    private RecyclerView rv;
    private List<Ponuda> ponuda;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sve_ponude_fragment, container, false);

        rv = (RecyclerView) rootView.findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);


        initializeData();
        initializeAdapter();

        return rootView;

    }

    private void initializeData(){
        ponuda = wsCaller.dohvatiPonude();
    }

    private void initializeAdapter(){
        RVAdapter adapter = new RVAdapter(ponuda);
        rv.setAdapter(adapter);
    }*/



}
