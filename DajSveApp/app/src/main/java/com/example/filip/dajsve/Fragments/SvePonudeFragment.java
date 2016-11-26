package com.example.filip.dajsve.Fragments;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.core.DataLoadedListener;
import com.example.filip.dajsve.Activities.MainActivity;
import com.example.filip.dajsve.R;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import entities.Grad;
import entities.Ponuda;

import static android.support.v4.widget.SwipeRefreshLayout.*;

/**
 * Created by Filip on 28.10.2016..
 */

public class SvePonudeFragment extends Fragment implements DataLoadedListener, OnRefreshListener {

    private RecyclerView rv;
    public  RVAdapter adapter;
    public SwipeRefreshLayout mSwipeRefreshLayout;
    android.os.Handler handlerAzuriranje;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sve_ponude_fragment, container, false);
        rv = (RecyclerView) rootView.findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());

        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);
        mSwipeRefreshLayout.setOnRefreshListener(this);


        rv.setLayoutManager(llm);
        if(Ponuda.getAll().isEmpty()){
            Ponuda nova = new Ponuda(1,null,1,1,1,"nema podataka",1,null,null,null);
            List<Ponuda> novaLista= new ArrayList<Ponuda>();
            novaLista.add(nova);
            RVAdapter adapter = new RVAdapter(novaLista,getContext());
            rv.setAdapter(adapter);}
        else {
            ArrayList<Ponuda> novaLista= new ArrayList<Ponuda>();
            novaLista= (ArrayList<Ponuda>) Ponuda.getAll();
            RVAdapter adapter = new RVAdapter(novaLista,getContext());
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
        RVAdapter adapter = new RVAdapter(ponudaArrayList,getContext());
        this.adapter= adapter;
        getFragmentManager()
                .beginTransaction()
                .attach(this)
                .commit();
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

    @Override
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

