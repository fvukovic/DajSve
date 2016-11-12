package com.example.filip.dajsve.Activities;

import android.graphics.Color;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
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
import entities.Ponuda;

import static android.R.attr.data;
import static android.R.attr.fragment;
import static android.R.attr.homeLayout;
import static android.R.attr.spinnerDropDownItemStyle;

public class MainActivity extends AppCompatActivity implements DataLoadedListener{

    ListView listView;
    ArrayAdapter<String> listAdapter;
    String arrayFragment[] = {"Sve ponude", "Favoriti", "Moje kategorije", "Mapa", "Facebook pregled"};
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerListener;
    ActionBarActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                Toast.makeText(MainActivity.this, "Drawer closed", Toast.LENGTH_SHORT).show();
            }
            public void onDrawerOpened(View drawerView){
                super.onDrawerOpened(drawerView);
                Toast.makeText(MainActivity.this, "Drawer opened", Toast.LENGTH_SHORT).show();
            }
        };
        drawerLayout.setDrawerListener(drawerListener);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        //dohvaćanje resursa Gradovi i postavljanje u spinner
        WebServiceCaller wsCaller = new WebServiceCaller();

        List<Grad> listaEntitetaGrad = wsCaller.GetDataFromWeb();
        //!!!kraj dohvaćanje resursa gradovi

        //dohvacanje ponuda
        List<Ponuda> listaEntitetaPonuda = wsCaller.dohvatiPodatke();
        //!!!kraj dohvaćanje resursa gradovi i podaci za ponude


        //Inicijalizacija spinnera, i adaptera za nazive gradova
        Spinner spinnerGradovi = (Spinner) findViewById(R.id.gradovi_spinner);
        ArrayAdapter<String> adapterGradovi;
        List<String> listaGradova = new ArrayList<>();
        //dodavanje dohvacenih gradova u novu listu
        for(Grad grad : listaEntitetaGrad){
            listaGradova.add(grad.getNaziv());
        }
        //prikaz gradova iz liste
            adapterGradovi = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaGradova);
            spinnerGradovi.setAdapter(adapterGradovi);

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


    //kraj
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
        DataLoader dataLoader;
        dataLoader = new WebServiceDataLoader();

        dataLoader.loadData(this);
    }


    @Override
    public void onDataLoaded(ArrayList<Grad> grads) {


    }

}
