package com.example.filip.dajsve.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.core.DataLoadedListener;
import com.example.filip.dajsve.Activities.MainActivity;
import com.example.filip.dajsve.R;

import java.util.ArrayList;
import java.util.List;

import entities.Grad;
import entities.Ponuda;

/**
 * Created by Filip on 29.11.2016..
 */

public class NovePonudeFragment extends Fragment implements DataLoadedListener {
    private RecyclerView rv;
    android.os.Handler handlerAzuriranje;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.nove_ponude_fragment, container, false);
        rv = (RecyclerView) rootView.findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());

        rv.setLayoutManager(llm);

            ArrayList<Ponuda> novaLista;
            novaLista= (ArrayList<Ponuda>) Ponuda.getNew();
            RVAdapter adapter = new RVAdapter(novaLista,getContext());
            rv.setAdapter(adapter);

        rv.setLayoutManager(llm);
        if(Ponuda.getNew().isEmpty()){
            Ponuda nova = new Ponuda(1,null,1,1,1,"nema podataka",1,null,null,null);
            novaLista.add(nova);
            adapter = new RVAdapter(novaLista,getContext());
            rv.setAdapter(adapter);}
        else {
            novaLista= (ArrayList<Ponuda>) Ponuda.getNew();
            adapter = new RVAdapter(novaLista,getContext());
            rv.setAdapter(adapter);
        }

        return rootView;
    }

    @Override
    public void onDataLoaded(List<Grad> gradovi, List<Ponuda> ponude) {

    }

    /*protected SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            azurirajPodatke();
        }
    };


    @Override
    public void onCompletion(String result) {

        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    private final Runnable refreshing = new Runnable(){
        public void run(){
            try {
                if(isRefreshing()){
                    handlerAzuriranje.postDelayed(this, 1000);
                }else{
                    mSwipeRefreshLayout.setRefreshing(false);
                    mainActivity.forceUpdate();
                    setLayout();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private boolean isRefreshing(){
        return mSwipeRefreshLayout.isRefreshing();
    }*/

    public void onRefresh() {
        System.out.println("Refreshana je stranica");
        Ponuda.deleteAll();
        ((MainActivity)getActivity()).loadData();

        /*Runnable refrashanjeStranice = new Runnable() {
            @Override
            public void run() {
                azurirajPodatke();
            }
        };*/
        /*System.out.println("Pokrećemo novi thread");
        new Thread(refrashanjeStranice).start();*/

    }

    private void azurirajPodatke() {

        Ponuda.deleteAll();
        ((MainActivity)getActivity()).loadData();
    }
}
