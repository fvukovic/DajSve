package com.example.filip.dajsve.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.filip.dajsve.R;
import com.google.android.gms.maps.GoogleMap;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import entities.Favorit;
import entities.Ponuda;

/**
 * Created by Helena on 23.11.2016..
 */

public class DetaljiPonudeFragment extends android.support.v4.app.Fragment  {
    public CheckBox favoritCheckBox;
    Favorit trenutni;
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
    private FrameLayout mapaPrikaz;
    private int position;
    private String name = "Map view";
    private com.google.android.gms.maps.MapFragment mapFragment;
    private GoogleMap map = null;
    private ImageButton gumbDodajUFavorite;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.detalji_ponude_fragment, container, false);
        favoritCheckBox= (CheckBox)rootView.findViewById(R.id.checkBox);
        favoritCheckBox.setOnCheckedChangeListener(CheckBoxListener);
        ponudaSlika=(ImageView)rootView.findViewById(R.id.ponuda_image);
        ponudaNaziv=(TextView) rootView.findViewById(R.id.ponuda_name);
        ponudaDescription=(TextView)rootView.findViewById(R.id.ponuda_description);
        ponudaCijena=(TextView)rootView.findViewById(R.id.ponuda_cijena);
        ponudaPopust=(TextView)rootView.findViewById(R.id.ponuda_popust);
        ponudaOriginal=(TextView)rootView.findViewById(R.id.ponuda_original);

        ponudaDatum=(TextView)rootView.findViewById(R.id.ponuda_datum);
        ponudaUsteda=(TextView)rootView.findViewById(R.id.ponuda_usteda);
        mapaPrikaz=(FrameLayout)rootView.findViewById(R.id.mapa_prikaz);
        gumbDodajUFavorite=(ImageButton)rootView.findViewById(R.id.favoriti_dodavanje);

        Bundle bundle = getArguments();
        List<Favorit> favoriti= Favorit.getAll();
        ArrayList<Ponuda> listaDohvacena = bundle.getParcelableArrayList("ponuda");
         ponudaDohvacena = listaDohvacena.get(0);

        Context context=ponudaSlika.getContext();
        Picasso.with(context).load(ponudaDohvacena.getURL()).into(ponudaSlika);
        ponudaNaziv.setText(ponudaDohvacena.getNaziv());
        ponudaDescription.setText(ponudaDohvacena.getTekstPonude());
        ponudaCijena.setText("cijena=" + ponudaDohvacena.getCijena());
        ponudaPopust.setText("popust="+(Integer.toString(ponudaDohvacena.getPopust())));
        ponudaOriginal.setText("stara cijena=" + (Integer.toString(ponudaDohvacena.getCijenaOriginal())));
        ponudaDatum.setText("datum=" + ponudaDohvacena.getDatumPonude());
        ponudaUsteda.setText("usteda="+Integer.toString(ponudaDohvacena.getUsteda()));
        //Provjera da li je favorit
        for(Favorit favorit : favoriti)
        {   System.out.println("DOSAO PRIJE");
            //ovo ne valja..treba naci samo da postoji taj url..
            if(favorit.getUrlSlike()==ponudaDohvacena.getUrlSlike())
            {

                System.out.println("usao sam");
                favoritCheckBox.setChecked(true);
                trenutni=favorit;

            }

        }
        //dodavanje google karte u layout
        View v = inflater.inflate(com.example.map.R.layout.map_fragment, container, false);
        mapFragment = new com.google.android.gms.maps.MapFragment();
        getActivity().getFragmentManager().beginTransaction().add(com.example.map.R.id.frame, mapFragment).commit();
        mapaPrikaz.setFocusable(true);
        mapaPrikaz.addView(v);

        return rootView;

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private CompoundButton.OnCheckedChangeListener CheckBoxListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            List<Favorit> favoriti= Favorit.getAll();
           if(favoritCheckBox.isChecked()){
               System.out.println("Usao sam u  Checked");
               Favorit novi = new Favorit(33,true,ponudaDohvacena.getTekstPonude(),
                       Integer.parseInt( ponudaDohvacena.getCijena()),ponudaDohvacena.getPopust()
                       ,ponudaDohvacena.getCijenaOriginal(),
                       ponudaDohvacena.getUrlSlike(), ponudaDohvacena.getUrlLogo(),
                       ponudaDohvacena.getUsteda(),ponudaDohvacena.getKategorija(),ponudaDohvacena.getGrad(),ponudaDohvacena.getDatumPonude());
                       novi.save();
           } else {
               System.out.println("Usao sam u NIJE Checked");
               for(Favorit favorit : favoriti)
               {
                   if(favorit.getUrlSlike()==ponudaDohvacena.getURL())
                   {
                       favoritCheckBox.setChecked(false);

                       System.out.println("Usao sam u brisanje");
                       //i ovaj delete vj. ne radi..trebalo bi se napraviti upit..pa onda tako brisat..
                       favorit.delete();


                   }
               }



           }
        }
    };
}


