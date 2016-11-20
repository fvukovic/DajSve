package com.example.filip.dajsve.Loaders;

import com.example.core.DataLoadedListener;
import com.example.core.DataLoader;
import com.example.webservice.WebServiceCaller;
import com.example.webservice.WebServiceHandler;

import java.util.ArrayList;
import java.util.List;

import entities.Grad;
import entities.Ponuda;

/**
 * Created by Filip on 9.11.2016..
 */

public class WebServiceDataLoader extends DataLoader {

    private boolean gradoviUcitani = false;
    private boolean ponudeUcitane = false;

    public List<Grad> gradovi;
    public List<Ponuda> ponude;

    @Override
    public void loadData(DataLoadedListener dataLoadedListener){
        super.loadData(dataLoadedListener);

        WebServiceCaller gradoviWs = new WebServiceCaller(gradoviHandler);
        WebServiceCaller ponudeWs = new WebServiceCaller(ponudeHandler);

        gradoviWs.dohvatiSve(Grad.class);
        ponudeWs.dohvatiSve(Ponuda.class);

    }

    WebServiceHandler gradoviHandler = new WebServiceHandler() {
        @Override
        public void onDataArrived(Object result, boolean ok) {
            if(ok){
                gradovi = (List<Grad>) result;
                for(Grad grad : gradovi){
                    grad.save();
                }
                gradoviUcitani = true;
                provjeriJesuLiPodaciUcitani();
            }
        }
    };

    WebServiceHandler ponudeHandler = new WebServiceHandler() {
        @Override
        public void onDataArrived(Object result, boolean ok) {
            if(ok){
                ponude = (List<Ponuda>) result;
                for(Ponuda ponuda : ponude){
                    ponuda.save();
                }
                ponudeUcitane = true;
                provjeriJesuLiPodaciUcitani();
            }
        }
    };

    private void provjeriJesuLiPodaciUcitani(){
        if(gradoviUcitani && ponudeUcitane){
            mDataLoadedListener.onDataLoaded(Grad.getAll(), Ponuda.getAll());
        }
    }


}
