package com.example.filip.dajsve.Activities;

import android.graphics.Color;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.core.DataLoadedListener;
import com.example.core.DataLoader;
import com.example.filip.dajsve.Fragments.FavoritiFragment;
import com.example.filip.dajsve.Fragments.MojeKategorijeFragment;
import com.example.filip.dajsve.Fragments.SvePonudeFragment;
import com.example.filip.dajsve.Loaders.WebServiceDataLoader;
import com.example.filip.dajsve.R;
import com.example.webservice.WebServiceCaller;
import android.os.StrictMode;
import java.util.ArrayList;
import java.util.List;

import entities.Grad;

import static android.R.attr.data;
import static android.R.attr.fragment;
import static android.R.attr.homeLayout;

public class MainActivity extends AppCompatActivity implements DataLoadedListener{

    ListView listView;
    ArrayAdapter<String> listAdapter;
    String arrayFragment[] = {"Sve ponude", "Favoriti", "Moje kategorije", "Mapa", "Facebook pregled"};
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        listView = (ListView) findViewById(R.id.listview);
        listAdapter = new ArrayAdapter<String>(this, R.layout.textview_item, arrayFragment);
        listView.setAdapter(listAdapter);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);


        //dohvaćanje resursa Gradovi i postavljanje u spinner
        Spinner spinnerGradovi = (Spinner) findViewById(R.id.gradovi_spinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.gradovi_array, R.layout.spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGradovi.setAdapter(spinnerAdapter);
        //!!!kraj dohvaćanje resursa gradovi


        //postavljanje početnog fragmenta glavne aktivnosti
        Fragment home = new SvePonudeFragment();
        FragmentManager fragmento = getSupportFragmentManager();
        fragmento.beginTransaction()
                .replace(R.id.linearlayout, home)
                .commit();
        //!!!kraj postavljanje početnog fragmenta glavne aktivnosti

        //postavljanje listenera za klik na item u meniju
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment fragment = null;
                switch (position){
                    case 0:
                        fragment = new SvePonudeFragment();
                        break;
                    case 1:
                        fragment = new FavoritiFragment();
                        break;
                    case 2:
                        fragment = new MojeKategorijeFragment();
                        break;
                    default:
                        fragment = new SvePonudeFragment();
                        break;
                }

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.linearlayout, fragment).commit();
                drawerLayout.closeDrawers();
            }
        });
        //!!!kraj postavljanje listenera za klik na item u meniju


        //pocetak
        WebServiceCaller wsCaller = new WebServiceCaller();

        String rez = wsCaller.GetDataFromWeb();

        Toast.makeText(this, rez, Toast.LENGTH_LONG).show();
    //kraj
    }

    public void loadData(){
        DataLoader dataLoader;
        dataLoader = new WebServiceDataLoader();

        dataLoader.loadData(this);
    }


    @Override
    public void onDataLoaded(ArrayList<Grad> gradovi) {
//        ArrayList<Grad> gradovi = new ArrayList<Grad>();


        if(gradovi!=null){
            //ovdje trebam podatke ispisat
        }

        /*List<ExpandableStoreItem> storeItemList = new ArrayList<ExpandableStoreItem>();

        if(stores != null) {
            for (Store store : stores) {
                storeItemList.add(new ExpandableStoreItem(store));
            }
            RecyclerView mRecycler = (RecyclerView) findViewById(R.id.main_recycler);
            if(mRecycler != null) {
                adapter = new StoreRecyclerAdapter(this, storeItemList);
                mRecycler.setAdapter(adapter);
                mRecycler.setLayoutManager(new LinearLayoutManager(this));

                // https://github.com/bignerdranch/expandable-recycler-view/blob/master/expandablerecyclerview/src/main/java/com/bignerdranch/expandablerecyclerview/Adapter/ExpandableRecyclerAdapter.java
                // store states and reload states
                adapter.expandParent(0);
            }
        }*/
    }

}
