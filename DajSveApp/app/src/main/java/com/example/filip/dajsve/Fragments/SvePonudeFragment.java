package com.example.filip.dajsve.Fragments;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
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
import com.example.core.DataLoader;
import com.example.filip.dajsve.Activities.MainActivity;
import com.example.filip.dajsve.Loaders.WebServiceDataLoader;
import com.example.filip.dajsve.R;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.logging.Handler;

import entities.Grad;
import entities.Ponuda;

import static android.support.v4.widget.SwipeRefreshLayout.*;

/**
 * Created by Filip on 28.10.2016..
 */

public class SvePonudeFragment extends Fragment implements  OnRefreshListener  {

    private RecyclerView rv;
    public  RVAdapter adapter;
    public SwipeRefreshLayout mSwipeRefreshLayout;
    Thread dretvaRefresh;
    boolean daDretvaRadi = true;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sve_ponude_fragment, container, false);
        rv = (RecyclerView) rootView.findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());

        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        System.out.println("OVOLIKO : "+Ponuda.getAll().size());
        rv.setLayoutManager(llm);
        ArrayList<Ponuda> novaLista= new ArrayList<Ponuda>();
        novaLista= (ArrayList<Ponuda>) Ponuda.getAll();
        RVAdapter adapter = new RVAdapter(novaLista,getContext());
        rv.setAdapter(adapter);

        return rootView;
    }
        class task extends AsyncTask<Void , Void, Void>{

            @Override
            protected void onPostExecute(Void aVoid) {
                System.out.println("USAO SAM U POST IN");
                ArrayList<Ponuda> novaLista= new ArrayList<Ponuda>();
                novaLista= (ArrayList<Ponuda>) Ponuda.getAll();
                RVAdapter adapter = new RVAdapter(novaLista,getContext());
                super.onPostExecute(aVoid);
                 mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            protected Void doInBackground(Void... params) {
                Looper.prepare();
                Ponuda.deleteAll();
                ((MainActivity)getActivity()).loadData();
                return null;

            }
        }




    @Override
    public void onRefresh() {
        System.out.println("dali se refresta: "+ mSwipeRefreshLayout.isRefreshing());
        System.out.println("Refreshana je stranica");
        new task().execute();


    }


}



