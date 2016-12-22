package com.example.filip.dajsve.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.filip.dajsve.Adapters.RVAdapter;
import com.example.filip.dajsve.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import entities.Ponuda;

/**
 * Created by Filip on 27.11.2016..
 */

public class MapFragment extends Fragment implements OnMapReadyCallback,GoogleMap.OnInfoWindowClickListener{

    private int position;
    private String name = "Map view";
    private com.google.android.gms.maps.MapFragment mapFragment;
    private GoogleMap map = null;
    private String nazivPonude;
    private String nazivGrada;
    List<Ponuda> svePonude = null;
    protected ArrayList<Ponuda> kliknutePonude = new ArrayList<Ponuda>();
    private RecyclerView rv;
    private Context context;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(com.example.map.R.layout.map_fragment, container, false);
        rv = (RecyclerView) v.findViewById(R.id.rv);
        svePonude = Ponuda.getAll();
        System.out.print("Broj ponuda: " + svePonude.size());
        context = v.getContext();


        mapFragment = new com.google.android.gms.maps.MapFragment();
        mapFragment.getMapAsync(this);
        getActivity().getFragmentManager().beginTransaction().add(com.example.map.R.id.frame, mapFragment).commit();
        return v;

    }

    @Override
    public void onMapReady(GoogleMap googleMap){
        map = googleMap;
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.popust_ic);
        map.setInfoWindowAdapter(new detaljniInfoWindow());


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
                Marker marker3=map.addMarker(new MarkerOptions()
                        .title(nazivPonude)
                        .snippet(nazivGrada)
                        .position(gradKoordinate)


                );
                marker3.setTag(ponuda);
                //setOnMapClicked(map);
                map.setOnInfoWindowClickListener(this);
            }
        }

    }

//detalji koji se prikazuju klikom na marker
   @Override
   public void onInfoWindowClick(Marker marker) {
       ArrayList<Ponuda> ponudaTag = new ArrayList<Ponuda>();
       ponudaTag.add((Ponuda) marker.getTag());

       AppCompatActivity activity = (AppCompatActivity) getContext();
       Fragment detaljiponude = new DetaljiPonudeFragment();
       Bundle bundle = new Bundle();
       activity.getSupportFragmentManager().beginTransaction();
       bundle.putParcelableArrayList("ponuda", ponudaTag);
       detaljiponude.setArguments(bundle);
       activity.getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.linearlayout, detaljiponude).commit();
   }

    class detaljniInfoWindow implements GoogleMap.InfoWindowAdapter {

        private final View infowindowView;

        detaljniInfoWindow() {

            infowindowView = getActivity().getLayoutInflater().inflate(R.layout.detalji_map_infowindow, null);
            infowindowView.setLayoutParams(new RelativeLayout.LayoutParams(900, RelativeLayout.LayoutParams.WRAP_CONTENT));


        }

        @Override
        public View getInfoContents(Marker marker) {
            Ponuda markerPonuda = (Ponuda) marker.getTag();

            TextView Title = ((TextView) infowindowView.findViewById(R.id.title));
            Title.setText(marker.getTitle());
            TextView Snippet = ((TextView) infowindowView.findViewById(R.id.snippet));
            Snippet.setText(marker.getSnippet());

            ImageView Slika = (ImageView) infowindowView.findViewById(R.id.slika);

            if (Slika != null) {
                Picasso.with(context).load(markerPonuda.getUrlSlike()).into(Slika, new MarkerCallback(marker));
            }

            return infowindowView;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            // TODO Auto-generated method stub
            return null;
        }
    }

        public class MarkerCallback implements Callback {
            Marker marker=null;

            MarkerCallback(Marker marker) {
                this.marker=marker;
            }

            @Override
            public void onError() {
                Log.e(getClass().getSimpleName(), "Error");
            }

            @Override
            public void onSuccess() {
                if (marker != null && marker.isInfoWindowShown()) {
                    marker.hideInfoWindow();
                    marker.showInfoWindow();
                }
            }
        }
    }


