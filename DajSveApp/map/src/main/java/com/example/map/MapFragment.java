package com.example.map;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.internal.DowngradeableSafeParcel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entities.Ponuda;

/**
 * Created by Filip on 27.11.2016..
 */

public class MapFragment extends android.support.v4.app.Fragment implements OnMapReadyCallback{

    private int position;
    private String name = "Map view";
    private com.google.android.gms.maps.MapFragment mapFragment;
    private GoogleMap map = null;
    private String nazivPonude;
    private String nazivGrada;
    List<Ponuda> svePonude = null;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        svePonude = Ponuda.getAll();
        System.out.print("Broj ponuda: " + svePonude.size());

        View v = inflater.inflate(R.layout.map_fragment, container, false);
        mapFragment = new com.google.android.gms.maps.MapFragment();
        mapFragment.getMapAsync(this);
        getActivity().getFragmentManager().beginTransaction().add(R.id.frame, mapFragment).commit();
        return v;

    }

    @Override
    public void onMapReady(GoogleMap googleMap){
        map = googleMap;


        for (Ponuda ponuda : svePonude
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
            }

        }

    }


    }


