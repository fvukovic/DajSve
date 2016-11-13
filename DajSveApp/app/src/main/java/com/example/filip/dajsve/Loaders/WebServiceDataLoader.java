package com.example.filip.dajsve.Loaders;

import com.example.core.DataLoadedListener;
import com.example.core.DataLoader;
import com.example.webservice.WebServiceCaller;
import com.example.webservice.WebServiceHandler;

import java.util.ArrayList;
import java.util.List;

import entities.Grad;

/**
 * Created by Filip on 9.11.2016..
 */

public class WebServiceDataLoader extends DataLoader {

    private boolean gradoviUcitani = false;

    @Override
    public void loadData(DataLoadedListener dataLoadedListener){
        super.loadData(dataLoadedListener);

        WebServiceCaller gradoviWs = new WebServiceCaller(gradoviHandler);
//        WebServiceCaller ponudeWs = new WebServiceCaller(discountsHandler);

        gradoviWs.dohvatiGradove();
//        discountsWs.getAll("getAll", Discount.class);

    }

    WebServiceHandler gradoviHandler = new WebServiceHandler() {
        @Override
        public void onDataArrived(Object result, boolean ok) {
            if(ok){
                List<Grad> gradovi = (List<Grad>) result;
                /*for(Grad grad : gradovi){
                    grad.save();
                }*/
                gradoviUcitani = true;
                provjeriJesuLiPodaciUcitani(gradovi);
            }
        }
    };

    private void provjeriJesuLiPodaciUcitani(List<Grad> gradovi){
        if(gradoviUcitani){
            mDataLoadedListener.onDataLoaded(gradovi);
        }
    }


}
