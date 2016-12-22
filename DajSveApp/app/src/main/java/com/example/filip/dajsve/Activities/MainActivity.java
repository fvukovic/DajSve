package com.example.filip.dajsve.Activities;

import android.os.Bundle;
import android.os.Looper;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.core.DataLoadedListener;
import com.example.core.DataLoader;
import com.example.filip.dajsve.Fragments.FavoritiFragment;
import com.example.filip.dajsve.Fragments.MojeKategorijeFragment;
import com.example.filip.dajsve.Fragments.PocetnaFragment;
import com.example.filip.dajsve.Fragments.SvePonudeFragment;
import com.example.filip.dajsve.Loaders.DatabaseDataLoader;
import com.example.filip.dajsve.Loaders.WebServiceDataLoader;
import com.example.filip.dajsve.R;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import entities.Grad;
import entities.Kategorija;
import entities.Ponuda;

public class MainActivity extends AppCompatActivity implements DataLoadedListener{
    SvePonudeFragment svePonudeFragment = new SvePonudeFragment();
    ListView listView;
    ArrayAdapter<String> listAdapter;
    String arrayFragment[] = {"Ponude", "Favoriti", "Moje kategorije", "Mapa"};
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerListener;

    List<Grad> gradLista = null;
    List<Ponuda> ponudaLista = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String grad = getSharedPreferences("GRAD", MODE_PRIVATE).getString("grad", "Zagreb");
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        FlowManager.init(new FlowConfig.Builder(this).build());

        ActionBar ab = getSupportActionBar();
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        listView = (ListView) findViewById(R.id.listview);
        listAdapter = new ArrayAdapter<String>(this, R.layout.textview_item, arrayFragment);
        listView.setAdapter(listAdapter);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        //klasa za otvaranje i zatvaranje drawera
        drawerListener = new ActionBarDrawerToggle(this,drawerLayout,null, R.string.open_drawer,R.string.close_drawer )
        {
            @Override
            public void onDrawerClosed(View drawerView){
                super.onDrawerClosed(drawerView);
            }
            public void onDrawerOpened(View drawerView){
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerListener);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadData();

        //postavljanje početnog fragmenta glavne aktivnosti
        Fragment home = new PocetnaFragment();
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
                        fragment = new PocetnaFragment();
                        break;
                    case 1:
                        fragment = new FavoritiFragment();
                        break;
                    case 2:
                        fragment = new MojeKategorijeFragment();
                        break;
                    case 3:
                        fragment = new com.example.filip.dajsve.Fragments.MapFragment();
                        break;
                    default:
                        fragment = new PocetnaFragment();
                        break;

                }

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.linearlayout, fragment).commit();
                drawerLayout.closeDrawers();
            }



        });
        //!!!kraj postavljanje listenera za klik na item u meniju



    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        drawerListener.syncState();
    }

    //kada kliknem na hamburger
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (drawerListener.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void loadData(){
        System.out.println("KOja je dretva: "+ Looper.myLooper());
        System.out.println("Poziva se funkcija za dohvat podataka");
        DataLoader dataLoader;
        dataLoader = new WebServiceDataLoader();

        if((Ponuda.getAll().isEmpty() || Grad.getAll().isEmpty()) || Looper.myLooper()!=Looper.getMainLooper()){
            System.out.println("Dohvaćamo web podatke");
            Toast.makeText(this, "Dohvaćamo podatke s weba", Toast.LENGTH_LONG).show();
            dataLoader = new WebServiceDataLoader();
        } else {
            System.out.println("Dohvaćamo lokalne podatke");
            Toast.makeText(this, "Dohvaćamo podatke lokalno", Toast.LENGTH_LONG).show();
            dataLoader = new DatabaseDataLoader();
        }

        dataLoader.loadData(this);
        System.out.print("molim te ko boga dragoga"+ Looper.myLooper());
    }


    @Override
    public void onDataLoaded(List<Grad> gradovi, List<Ponuda> ponude, List<Kategorija> kategorije) {

        if(  Looper.myLooper() == Looper.getMainLooper()) {
            Spinner spinnerGradovi = (Spinner) findViewById(R.id.gradovi_spinner);
            ArrayAdapter<String> adapterGradovi;
            List<String> listaGradova = new ArrayList<>();
            ArrayList<Ponuda> ponudaArrayList = new ArrayList<Ponuda>();

            ponudaLista = ponude;
            gradLista = gradovi;

            for (Grad grad : gradovi) {
                listaGradova.add(grad.getNaziv());
            }
            for (Ponuda ponuda : ponude) {
                ponudaArrayList.add(ponuda);
            }
            adapterGradovi = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaGradova);
            spinnerGradovi.setAdapter(adapterGradovi);
        }

    }

}
