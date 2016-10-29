package com.example.filip.dajsve.Activities;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.filip.dajsve.Fragments.FavoritiFragment;
import com.example.filip.dajsve.Fragments.MojeKategorijeFragment;
import com.example.filip.dajsve.Fragments.SvePonudeFragment;
import com.example.filip.dajsve.R;

import static android.R.attr.fragment;
import static android.R.attr.homeLayout;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<String> listAdapter;
    String arrayFragment[] = {"Sve ponude", "Favoriti", "Moje kategorije", "Mapa", "Facebook pregled"};
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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


    }
}
