package com.example.filip.dajsve.Activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.filip.dajsve.Fragments.FavoritiFragment;
import com.example.filip.dajsve.Fragments.MojeKategorijeFragment;
import com.example.filip.dajsve.Fragments.SvePonudeFragment;
import com.example.filip.dajsve.R;

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
        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayFragment);
        listView.setAdapter(listAdapter);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment fragment;
                fragment = new SvePonudeFragment();
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
                }

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.relativelayout, fragment).commit();
                drawerLayout.closeDrawers();
            }
        });
    }
}
