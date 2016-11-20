package com.example.filip.dajsve.Loaders;

import com.example.core.DataLoadedListener;
import com.example.core.DataLoader;

import entities.Grad;
import entities.Ponuda;

/**
 * Created by Filip on 20.11.2016..
 */

public class DatabaseDataLoader extends DataLoader {

    @Override
    public void loadData(DataLoadedListener dataLoadedListener){
        super.loadData(dataLoadedListener);
        try{
            gradovi = Grad.getAll();
            ponude = Ponuda.getAll();

            mDataLoadedListener.onDataLoaded(gradovi, ponude);

        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

}
