package com.example.core;
import java.util.ArrayList;

import entities.Grad;

/**
 * Created by Filip on 8.11.2016..
 */

public abstract class DataLoader {

    public ArrayList<Grad> gradovi;

    protected DataLoadedListener mDataLoadedListener;

    public void loadData(DataLoadedListener dataLoadedListener){
        this.mDataLoadedListener = dataLoadedListener;
    }


    public boolean dataLoaded(){
        if(gradovi == null){
            return false;
        } else{
            return true;
        }
    }
}
