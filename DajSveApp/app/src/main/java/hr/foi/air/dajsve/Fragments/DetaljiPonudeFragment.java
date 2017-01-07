package hr.foi.air.dajsve.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entities.Favorit;
import entities.Ponuda;

/**
 * Created by Helena on 23.11.2016..
 */

public class DetaljiPonudeFragment extends android.support.v4.app.Fragment implements OnMapReadyCallback {
    //public CheckBox favoritCheckBox;
    Favorit trenutni;
    boolean statusFavoritPonuda;
    public Ponuda ponudaDohvacena;
    private ImageView ponudaSlika;
    private TextView ponudaNaziv;
    private TextView ponudaDescription;
    private TextView ponudaCijena;
    private TextView ponudaPopust;
    private TextView ponudaOriginal;
    private TextView ponudaDatum;
    private TextView ponudaUsteda;
    private TextView ponudaGrad;
    private LinearLayout linkNaStranicu;
    private FrameLayout mapaPrikaz;
    private int position;
    private String name = "Map view";
    private com.google.android.gms.maps.MapFragment mapFragment;
    private GoogleMap map = null;
    public LinearLayout gumbDodajUFavorite;
    public boolean ponudaJeFavorit;
    public TextView dodajUFavoriteTekst;
    public ImageView dodajUFavoriteSlika;

    Context context;
    private ImageView prozirnaSlika;
    private ScrollView scroll;
    private boolean ulazNaFragment=false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View rootView = inflater.inflate(hr.foi.air.dajsve.R.layout.detalji_ponude_fragment, container, false);
        context = rootView.getContext();

        ponudaSlika=(ImageView)rootView.findViewById(hr.foi.air.dajsve.R.id.ponuda_image);
        ponudaNaziv=(TextView) rootView.findViewById(hr.foi.air.dajsve.R.id.ponuda_name);
        linkNaStranicu = (LinearLayout) rootView.findViewById(hr.foi.air.dajsve.R.id.link_na_stranicu);
        ponudaCijena=(TextView)rootView.findViewById(hr.foi.air.dajsve.R.id.ponuda_cijena);
        ponudaPopust=(TextView)rootView.findViewById(hr.foi.air.dajsve.R.id.ponuda_popust);
        dodajUFavoriteTekst = (TextView) rootView.findViewById(hr.foi.air.dajsve.R.id.dodaj_u_favorite_text);
        dodajUFavoriteSlika = (ImageView) rootView.findViewById(hr.foi.air.dajsve.R.id.dodaj_brisi_favorita_slika);
        ponudaOriginal=(TextView)rootView.findViewById(hr.foi.air.dajsve.R.id.ponuda_original);
        gumbDodajUFavorite = (LinearLayout) rootView.findViewById(hr.foi.air.dajsve.R.id.dodaj_brisi_favorita);
        mapaPrikaz=(FrameLayout)rootView.findViewById(hr.foi.air.dajsve.R.id.mapa_prikaz);

        ponudaJeFavorit = false;

        Bundle bundle = getArguments();
        List<Favorit> favoriti= Favorit.getAll();
        ArrayList<Ponuda> listaDohvacena = bundle.getParcelableArrayList("ponuda");
        ponudaDohvacena = listaDohvacena.get(0);

        Picasso.with(context).load(ponudaDohvacena.getURL()).into(ponudaSlika);
        ponudaNaziv.setText(ponudaDohvacena.getNaziv());
        ponudaCijena.setText(ponudaDohvacena.getCijena() + " kuna");
        ponudaPopust.setText((Integer.toString(ponudaDohvacena.getPopust()) + "%"));

        //strike preko stare cijene
        ponudaOriginal.setPaintFlags(ponudaOriginal.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        ponudaOriginal.setText((Integer.toString(ponudaDohvacena.getCijenaOriginal()) + " kuna"));

        prozirnaSlika = (ImageView) rootView.findViewById(hr.foi.air.dajsve.R.id.prozirnaslika);
        scroll = (ScrollView) rootView.findViewById(hr.foi.air.dajsve.R.id.skrolanje);

        prozirnaSlika.setOnTouchListener(new View.OnTouchListener() {@Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    // Disallow ScrollView to intercept touch events.
                    scroll.requestDisallowInterceptTouchEvent(true);
                    // Disable touch on transparent view
                    return false;

                case MotionEvent.ACTION_UP:
                    // Allow ScrollView to intercept touch events.
                    scroll.requestDisallowInterceptTouchEvent(false);
                    return true;

                case MotionEvent.ACTION_MOVE:
                    scroll.requestDisallowInterceptTouchEvent(true);
                    return false;

                default:
                    return true;
            }
        }
        });

        linkNaStranicu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(ponudaDohvacena.getUrlWeba());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });

        //provjera je li ponuda favorit, ako je mjenja boju u crveno i mijenja tekst gumba, ako nije onda u zeleno
        ponudaJeFavorit = false;
        dodajUFavoriteTekst.setText("Spremi ponudu");
        dodajUFavoriteSlika.setRotation(0);
//        gumbDodajUFavorite.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.blaga_tamno_zelena));

        // Provjerava da li je trenutna ponuda favorit, ako je mjenja dizajn kao da je ponuda favorit
        for(Favorit favorit : favoriti){
            if(favorit.getHash().equals(ponudaDohvacena.getHash())){
                ponudaJeFavorit = true;
                dodajUFavoriteTekst.setText("Briši iz spremljenih ponuda");
                dodajUFavoriteSlika.setRotation(45);
//                gumbDodajUFavorite.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.blaga_crvena));
            }
        }

        // klik na gumb dodaj/ukloni favorit
        gumbDodajUFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Favorit> favoriti= Favorit.getAll();
                //ako je vec favorit,brišemo ga iz tablice Favorit
                if(ponudaJeFavorit){
                    Favorit.deleteFromWebUrl(ponudaDohvacena.getUrlWeba());
                    ponudaJeFavorit = false;

                    Toast.makeText(getActivity(), "Ponuda izbrisana iz Omiljenih ponuda", Toast.LENGTH_LONG).show();

                    dodajUFavoriteTekst.setText("Spremi ponudu");

                    //Animacija okretanja ikonice
                    AnimationSet animSet = new AnimationSet(true);
//                    animSet.setInterpolator(new DecelerateInterpolator());
                    animSet.setFillAfter(true);
//                    animSet.setFillEnabled(true);

                    final RotateAnimation animRotate = new RotateAnimation(0.0f, 3690.0f,
                            RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                            RotateAnimation.RELATIVE_TO_SELF, 0.5f);

                    animRotate.setDuration(500);
                    animRotate.setFillAfter(true);
                    animSet.addAnimation(animRotate);

                    dodajUFavoriteSlika.startAnimation(animSet);

                    //ako nije favorit, kreiramo novi element i tako ga dodajemo u favorite
                }else {
                    Favorit novi = new Favorit(favoriti.size(),ponudaDohvacena.getHash(), true, ponudaDohvacena.getId(), ponudaDohvacena.getTekstPonude(),
                            Integer.parseInt(ponudaDohvacena.getCijena()), ponudaDohvacena.getPopust()
                            , ponudaDohvacena.getCijenaOriginal(),ponudaDohvacena.getUrlSlike(), ponudaDohvacena.getUrlLogo(), ponudaDohvacena.getUrlWeba(),
                            ponudaDohvacena.getUsteda(), ponudaDohvacena.getKategorija(), ponudaDohvacena.getGrad(), ponudaDohvacena.getDatumPonude());
                    novi.save();
                    Toast.makeText(getActivity(), "Ponuda spremljena u Omiljene ponude", Toast.LENGTH_LONG).show();

                    ponudaJeFavorit = true;
                    dodajUFavoriteTekst.setText("Briši iz spremljenih ponuda");

                    //Animacija okretanja ikonice
                    AnimationSet animSet = new AnimationSet(true);
//                    animSet.setInterpolator(new DecelerateInterpolator());
                    animSet.setFillAfter(true);
//                    animSet.setFillEnabled(true);

                    final RotateAnimation animRotate = new RotateAnimation(0.0f, 3645.0f,
                            RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                            RotateAnimation.RELATIVE_TO_SELF, 0.5f);


                    animRotate.setDuration(500);
                    animRotate.setFillAfter(true);
                    animSet.addAnimation(animRotate);

                    dodajUFavoriteSlika.startAnimation(animSet);
                }
            }
        });

        //dodavanje google karte u layout
        View v = inflater.inflate(hr.foi.air.dajsve.R.layout.map_fragment, container, false);
        mapFragment = new com.google.android.gms.maps.MapFragment();
        mapFragment.getMapAsync(this);
        getActivity().getFragmentManager().beginTransaction().add(hr.foi.air.dajsve.R.id.frame, mapFragment).commit();
        mapaPrikaz.setFocusable(true);
        mapaPrikaz.addView(v);

        return rootView;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        BitmapDescriptor icon1 = BitmapDescriptorFactory.fromResource(hr.foi.air.dajsve.R.drawable.popust_ic);
        BitmapDescriptor icon2 = BitmapDescriptorFactory.fromResource(hr.foi.air.dajsve.R.drawable.grad_ic);

        try {
            if (ponudaDohvacena.getLongitude().contentEquals("nema") || ponudaDohvacena.getLatitude().contentEquals("nema")){
                //Ako nema koordinata v xml-u onda ime grada u kojem je ponuda pretvara u koordinate
                Geocoder geocoder = new Geocoder(context);
                String grad = ponudaDohvacena.getGrad();
                List<Address> adresa;
                adresa = geocoder.getFromLocationName(grad, 1);
                double latitude= adresa.get(0).getLatitude();
                double longitude= adresa.get(0).getLongitude();
                LatLng gradKoordinate = new LatLng(latitude, longitude);

                Marker marker1=map.addMarker(new MarkerOptions()
                        .title("Lokacija ponude:")
                        .snippet("Grad:" +grad)
                        .position(gradKoordinate)
                        .icon(icon2)
                );


                marker1.showInfoWindow();

                CameraPosition cameraPosition = new CameraPosition.Builder().target(gradKoordinate).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }else{
                //Uzima koordinate koje su dostupne v xml-u
                double ponudaLatitude = Double.parseDouble(ponudaDohvacena.getLatitude());
                double ponudaLongitude = Double.parseDouble(ponudaDohvacena.getLongitude());
                LatLng gradKoordinate = new LatLng(ponudaLatitude, ponudaLongitude);

                Geocoder geocoder = new Geocoder(context);
                List<Address> adresa;
                adresa = geocoder.getFromLocation(ponudaLatitude, ponudaLongitude,1);
                String cityName = adresa.get(0).getAddressLine(0);
                String stateName = adresa.get(0).getAddressLine(1);
                String countryName = adresa.get(0).getAddressLine(2);
                String Locality = adresa.get(0).getLocality();

                Marker marker2=map.addMarker(new MarkerOptions()
                        .title("Lokacija: " + Locality)
                        .snippet("Adresa:" +cityName +" "+ stateName+ " " + countryName)
                        .position(gradKoordinate)
                        .icon(icon1));


                marker2.showInfoWindow();

                //Pozicionira i zumirana lokaciju od koordinata
                CameraPosition cameraPosition = new CameraPosition.Builder().target(gradKoordinate).zoom(13).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
