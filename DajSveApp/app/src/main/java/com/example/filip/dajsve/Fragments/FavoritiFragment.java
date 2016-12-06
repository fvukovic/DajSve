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

import entities.Favorit;
import entities.Grad;
import entities.Ponuda;

import static android.support.v4.widget.SwipeRefreshLayout.*;

/**
 * Created by Filip on 28.10.2016..
 */

public class FavoritiFragment extends Fragment   {

    private RecyclerView rv;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sve_ponude_fragment, container, false);
        rv = (RecyclerView) rootView.findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());


        rv.setLayoutManager(llm);
        List<Favorit> novaLista= Favorit.getAll();
        List<Ponuda> listaPonuda= new ArrayList<Ponuda>() ;
        for(Favorit favorit : novaLista){

            Ponuda novi = new Ponuda(favorit.getId(),favorit.getTekstPonude(),
                    favorit.getCijena(),favorit.getPopust(),favorit.getCijenaOriginal(),favorit.getUrlSlike(), favorit.getUrlLogo(),
                    favorit.getUsteda(),"","", favorit.getDatumPonude());
            listaPonuda.add(novi);

        }
        RVAdapter adapter = new RVAdapter(listaPonuda,getContext());
        rv.setAdapter(adapter);

        return rootView;
    }



}



