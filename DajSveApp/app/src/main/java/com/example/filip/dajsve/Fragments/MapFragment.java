package com.example.filip.dajsve.Fragments;

import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.filip.dajsve.Adapters.RVAdapter;
import com.example.filip.dajsve.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import entities.Ponuda;

/**
 * Created by Filip on 27.11.2016..
 */

public class MapFragment extends Fragment implements OnMapReadyCallback{

    private int position;
    private String name = "Map view";
    private com.google.android.gms.maps.MapFragment mapFragment;
    private GoogleMap map = null;
    private String nazivPonude;
    private String nazivGrada;
    List<Ponuda> svePonude = null;
    protected ArrayList<Ponuda> kliknutePonude = new ArrayList<Ponuda>();
    private RecyclerView rv;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(com.example.map.R.layout.map_fragment, container, false);
        rv = (RecyclerView) v.findViewById(R.id.rv);
        svePonude = Ponuda.getAll();
        System.out.print("Broj ponuda: " + svePonude.size());


        mapFragment = new com.google.android.gms.maps.MapFragment();
        mapFragment.getMapAsync(this);
        getActivity().getFragmentManager().beginTransaction().add(com.example.map.R.id.frame, mapFragment).commit();
        return v;

    }

    @Override
    public void onMapReady(GoogleMap googleMap){
        map = googleMap;


        for (final Ponuda ponuda : svePonude
                ) {
            String ponudaLat = ponuda.getLatitude();
            String ponudaLon = ponuda.getLongitude();
            if(ponudaLat.contentEquals("nema") || ponudaLon.contentEquals("nema")){continue;}
            else{
                double ponudaLatitude = Double.parseDouble(ponudaLat);
                double ponudaLongitude = Double.parseDouble(ponudaLon);
                LatLng gradKoordinate = new LatLng(ponudaLatitude, ponudaLongitude);
                nazivGrada = ponuda.getGrad();
                nazivPonude = ponuda.getNaziv();
                map.addMarker(new MarkerOptions()
                        .title(nazivPonude)
                        .snippet(nazivGrada)
                        .position(gradKoordinate)
                );

                setOnMapClicked(map);
            }
        }

    }

    public void setOnMapClicked (GoogleMap googleMap){
        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                for (Ponuda ponuda : svePonude) {
                    String naziv = marker.getTitle();
                    if (ponuda.getNaziv().equals(naziv)) {
                        System.out.print(ponuda.getNaziv());
                        kliknutePonude.add(0,ponuda);
                    } else {
                        continue;
                    }

                    AppCompatActivity activity = (AppCompatActivity) getContext();
                    Fragment detaljiponude = new DetaljiPonudeFragment();
                    Bundle bundle = new Bundle();
                    activity.getSupportFragmentManager().beginTransaction();
                    bundle.putParcelableArrayList("ponuda", kliknutePonude);
                    detaljiponude.setArguments(bundle);
                    activity.getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.linearlayout, detaljiponude).commit();
                }


           }
        });
    }

    }


