package com.example.filip.dajsve.Loaders;

import android.nfc.Tag;
import android.os.Looper;
import android.util.Log;

import com.example.core.DataLoadedListener;
import com.example.core.DataLoader;
import com.example.webservice.WebServiceCaller;
import com.example.webservice.WebServiceHandler;
import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.config.DatabaseConfig;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.FastStoreModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.util.ArrayList;
import java.util.List;

import entities.Grad;
import entities.Kategorija;
import entities.MainDatabase;
import entities.Ponuda;

/**
 * Created by Filip on 9.11.2016..
 */

public class WebServiceDataLoader extends DataLoader {

    private boolean gradoviUcitani = false;
    private boolean ponudeUcitane = false;
    private boolean kategorijeUcitane = false;
    public List<Grad> gradovi;
    public List<Ponuda> ponude;
    public List<Kategorija> kategorije;

    @Override
    public void loadData(DataLoadedListener dataLoadedListener){
        super.loadData(dataLoadedListener);

        WebServiceCaller gradoviWs = new WebServiceCaller(gradoviHandler);
        WebServiceCaller ponudeWs = new WebServiceCaller(ponudeHandler);
        WebServiceCaller kategorijeWs = new WebServiceCaller(kategorijeHandler);

        gradoviWs.dohvatiSve(Grad.class);
        ponudeWs.dohvatiSve(Ponuda.class);
        kategorijeWs.dohvatiSve(Kategorija.class);

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

    WebServiceHandler kategorijeHandler = new WebServiceHandler() {
        @Override
        public void onDataArrived(Object result, boolean ok) {
            if(ok){
                kategorije = (List<Kategorija>) result;
                for(Kategorija kategorija : kategorije){
                    kategorija.save();
                }
                kategorijeUcitane = true;
                System.out.println("Trenutno ima: " + kategorije.size()+ " kategorija!");
                provjeriJesuLiPodaciUcitani();
            }
        }
    };

//    Database database = MainDatabase.class;
    WebServiceHandler ponudeHandler = new WebServiceHandler() {
        @Override
        public void onDataArrived(Object result, boolean ok) {
            if(Looper.myLooper()!=Looper.getMainLooper())
                Ponuda.deleteAll();
            if(ok){
                System.out.println("Pokrenula se funkcija za transakcije");
                ponude = (List<Ponuda>) result;

                FlowManager.getDatabase(MainDatabase.class)
                        .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                                new ProcessModelTransaction.ProcessModel<Ponuda>() {
                                    @Override
                                    public void processModel(Ponuda ponuda) {
                                        ponuda.save();
                                    }
                                }).addAll(ponude).build())
                        .error(new Transaction.Error() {
                            @Override
                            public void onError(Transaction transaction, Throwable error) {
                                System.out.println("Greška u transakciji");
                            }
                        })
                        .success(new Transaction.Success() {
                            @Override
                            public void onSuccess(Transaction transaction) {
                                System.out.println("Transakcija uspješna");
                            }
                        }).build().execute();

                System.out.println("Završava funkcija za transakcije");

                ponudeUcitane = true;
                provjeriJesuLiPodaciUcitani();
            }
        }
    };

    private void provjeriJesuLiPodaciUcitani(){
        if(gradoviUcitani && ponudeUcitane){
            mDataLoadedListener.onDataLoaded(Grad.getAll(), Ponuda.getAll(), Kategorija.getAll());
        }
    }


}
