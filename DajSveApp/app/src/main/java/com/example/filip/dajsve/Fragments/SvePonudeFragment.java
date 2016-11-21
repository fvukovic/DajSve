package com.example.filip.dajsve.Fragments;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.core.DataLoader;
import com.example.filip.dajsve.Activities.MainActivity;
import com.example.filip.dajsve.Loaders.WebServiceDataLoader;
import com.example.filip.dajsve.R;
import com.example.webservice.WebServiceCaller;

import java.util.ArrayList;
import java.util.List;

import entities.Grad;
import entities.Ponuda;

/**
 * Created by Filip on 28.10.2016..
 */

public class SvePonudeFragment extends Fragment {

    private RecyclerView rv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sve_ponude_fragment, container, false);

        rv = (RecyclerView) rootView.findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        ArrayList<Ponuda> lista = new ArrayList<>();
        RVAdapter adapter = new RVAdapter(lista);

        rv.setAdapter(adapter);

        return rootView;
    }


    public void initializeAdapter(List<Ponuda> preuzetePonude){

        RVAdapter adapter = new RVAdapter(preuzetePonude);

        rv.setAdapter(adapter);

        Fragment frg = getFragmentManager().findFragmentByTag("sve_ponude_fragment_tag");
        final android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(frg);
        ft.attach(frg);
        ft.commit();
    }

}
