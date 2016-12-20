package com.example.filip.dajsve.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.filip.dajsve.Adapters.RVAdapter;
import com.example.filip.dajsve.R;
import java.util.ArrayList;
import java.util.List;

import entities.Favorit;
import entities.Ponuda;

/**
 * Created by Filip on 28.10.2016..
 */

public class FavoritiFragment extends Fragment   {

    private RecyclerView rv;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.favoriti_fragment, container, false);
        rv = (RecyclerView) rootView.findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        List<Favorit> novaLista= Favorit.getAll();
        TextView textView = (TextView) rootView.findViewById(R.id.textView2);
        if(novaLista.isEmpty())
        {
            textView.setText("Niste odabrali ni jedan favorit");
        }
        else
        {
            textView.setText("");
        }
        rv.setLayoutManager(llm);

        List<Ponuda> listaPonuda= new ArrayList<Ponuda>() ;
        List<Ponuda> listaSvihPonuda=Ponuda.getAll() ;

        for(Favorit favorit : novaLista){
            for(Ponuda ponuda : listaSvihPonuda)
            {
                if(favorit.getisIdPonude()==ponuda.getId())
                {
                    Ponuda novi = new Ponuda(favorit.getId(),favorit.getHash(), favorit.getTekstPonude(),
                            favorit.getCijena(),favorit.getPopust(),favorit.getCijenaOriginal(),favorit.getUrlSlike(), favorit.getUrlLogo(), favorit.getUrlWeba(),
                            favorit.getUsteda(),"",favorit.getGrad(), favorit.getDatumPonude(),"nema","nema");
                    listaPonuda.add(novi);
                }
            }
        }
        RVAdapter adapter = new RVAdapter(listaPonuda,getContext());
        if(adapter.getItemCount() == 0){

        }else{
            rv.setAdapter(adapter);
        }


        return rootView;
    }

}
