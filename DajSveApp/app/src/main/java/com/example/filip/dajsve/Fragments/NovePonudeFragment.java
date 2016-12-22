package com.example.filip.dajsve.Fragments;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import com.example.filip.dajsve.Activities.MainActivity;
import com.example.filip.dajsve.Adapters.RVAdapter;
import com.example.filip.dajsve.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.OnClick;
import entities.Ponuda;

import static android.support.v4.widget.SwipeRefreshLayout.*;

/**
 * Created by Filip on 29.11.2016..
 */

public class NovePonudeFragment extends Fragment implements OnRefreshListener{
    private RecyclerView rv;
    public RVAdapter adapter;
    public SwipeRefreshLayout mSwipeRefreshLayout;
    List<Ponuda> sosBaza;
    public FloatingActionButton fab;
    public String[] opcijeSortiranja;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.nove_ponude_fragment, container, false);
        final AlertDialog.Builder ad =  new AlertDialog.Builder(getActivity());
        rv = (RecyclerView) rootView.findViewById(R.id.rv);
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab_nove_ponude);
        opcijeSortiranja = new String[3];

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());

        opcijeSortiranja[0] = "Cijena - uzlazno";
        opcijeSortiranja[1] = "Cijena - silazno";
        opcijeSortiranja[2] = "Popust";


        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        System.out.println("OVOLIKO : "+Ponuda.getAll().size());

        rv.setLayoutManager(llm);

        ArrayList<Ponuda> novaLista= new ArrayList<Ponuda>();
        novaLista= (ArrayList<Ponuda>) Ponuda.getNew();

        ad.setTitle("Sortiraj prema:");

        ad.setItems(opcijeSortiranja, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ArrayList<Ponuda> novaLista= new ArrayList<Ponuda>();
                String vrstaSortiranja = null;
                boolean asc = true;
                switch (which){
                    case 0:
                        vrstaSortiranja = "cijena";
                        asc = true;
                        break;
                    case 1:
                        vrstaSortiranja = "cijena";
                        asc = false;
                        break;
                    case 2:
                        vrstaSortiranja = "popust";
                        asc = false;
                        break;
                }
                novaLista= (ArrayList<Ponuda>) Ponuda.getNewOrderBy(vrstaSortiranja, asc);

                RVAdapter adapter = new RVAdapter(novaLista, getContext());
                rv.setAdapter(adapter);
            }
        });

        RVAdapter adapter = new RVAdapter(novaLista, getContext());
        rv.setAdapter(adapter);

        fab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ad.show();
            }
        });

        return rootView;
    }




    class task extends AsyncTask<Void , Void, Void> {

        @Override
        protected void onPostExecute(Void aVoid) {
            mSwipeRefreshLayout.setRefreshing(false);
            System.out.println("USAO SAM U POST IN");
            RVAdapter adapter = new RVAdapter(Ponuda.getNew(),getContext());
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