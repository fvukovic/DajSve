package com.example.filip.dajsve.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.filip.dajsve.Activities.MainActivity;
import com.example.filip.dajsve.Adapters.RVAdapter;
import com.example.filip.dajsve.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import entities.OmiljenaKategorija;
import entities.Ponuda;

/**
 * Created by Filip on 28.10.2016..
 */

public class MojeKategorijeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView rv;
    public RVAdapter adapter;
    public SwipeRefreshLayout mSwipeRefreshLayout;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.moje_kategorije_fragment, container, false);
        rv = (RecyclerView) rootView.findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());


        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        System.out.println("OVOLIKO : "+ Ponuda.getAll().size());

        rv.setLayoutManager(llm);

        ArrayList<Ponuda> listaOmiljenihPonuda = new ArrayList<Ponuda>();

        List<OmiljenaKategorija> listaOmiljenihKategorija = OmiljenaKategorija.getAll();
        for(OmiljenaKategorija omiljenaKategorija : listaOmiljenihKategorija){
            listaOmiljenihPonuda.addAll(Ponuda.getByFavoriteCategory(omiljenaKategorija.getNaziv()));
        }
        Collections.shuffle(listaOmiljenihPonuda);

        RVAdapter adapter = new RVAdapter(listaOmiljenihPonuda,getContext());
        rv.setAdapter(adapter);

        return rootView;
    }

    class task extends AsyncTask<Void , Void, Void> {

        @Override
        protected void onPostExecute(Void aVoid) {
            mSwipeRefreshLayout.setRefreshing(false);
            System.out.println("USAO SAM U POST IN");

            ArrayList<Ponuda> listaOmiljenihPonuda = new ArrayList<Ponuda>();

            List<OmiljenaKategorija> listaOmiljenihKategorija = OmiljenaKategorija.getAll();
            for(OmiljenaKategorija omiljenaKategorija : listaOmiljenihKategorija){
                listaOmiljenihPonuda.addAll(Ponuda.getByFavoriteCategory(omiljenaKategorija.getNaziv()));
            }
            Collections.shuffle(listaOmiljenihPonuda);
            RVAdapter adapter = new RVAdapter(listaOmiljenihPonuda,getContext());
            rv.setAdapter(adapter);
            super.onPostExecute(aVoid);
        }


        @Override
        protected Void doInBackground(Void... params) {
            if (Looper.myLooper() == null)
            {
                Looper.prepare();
            }
            ((MainActivity)getActivity()).loadData();
            return null;

        }
    }

    @Override
    public void onRefresh() {
        new task().execute();
    }
}
