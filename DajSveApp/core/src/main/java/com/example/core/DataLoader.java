package com.example.core;
import java.util.ArrayList;
import java.util.List;

import entities.Grad;
import entities.Ponuda;

/**
 * Created by Filip on 8.11.2016..
 */

public abstract class DataLoader {

    public List<Grad> gradovi;
    public List<Ponuda> ponude;

    protected DataLoadedListener mDataLoadedListener;

    public void loadData(DataLoadedListener dataLoadedListener){
        this.mDataLoadedListener = dataLoadedListener;
    }

    /*public boolean dataLoaded(){
        if(gradovi == null || ponude == null){
            return false;
        } else{
            return true;
        }
    }*/
}
