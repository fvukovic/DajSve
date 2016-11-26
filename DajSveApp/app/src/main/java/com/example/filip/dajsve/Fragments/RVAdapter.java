package com.example.filip.dajsve.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.filip.dajsve.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import com.squareup.picasso.Picasso;
import android.content.Context;
import android.widget.Toast;

import entities.Ponuda;

/**
 * Created by Helena on 12.11.2016..
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PonudeViewHolder> {
    public static class PonudeViewHolder extends RecyclerView.ViewHolder{

        CardView cv;
        TextView ponudaNaziv;
        TextView ponudaOpis;
        ImageView ponudaSlika;

        PonudeViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.card_view);
            ponudaNaziv = (TextView)itemView.findViewById(R.id.ponuda_name);
            ponudaOpis = (TextView)itemView.findViewById(R.id.ponuda_description);
            ponudaSlika = (ImageView)itemView.findViewById(R.id.ponuda_image);
        }
    }


    List<Ponuda> ponuda;
    Context context;
    public RVAdapter(List<Ponuda> ponuda,Context context){
        this.ponuda = ponuda;
        this.context=context;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PonudeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sve_ponude_item, viewGroup, false);
        PonudeViewHolder pvh = new PonudeViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PonudeViewHolder PonudeViewHolder, int i) {
        PonudeViewHolder.ponudaNaziv.setText(ponuda.get(i).getNaziv());
        PonudeViewHolder.ponudaOpis.setText("Cijena: "+ponuda.get(i).getCijena()+ " kuna");
        //Context context =PonudeViewHolder.ponudaSlika.getContext();
        Picasso.with(context).load(ponuda.get(i).getURL()).into(PonudeViewHolder.ponudaSlika);

        final int index=i+1;
        final ArrayList<Ponuda> ponudaArrayList=new ArrayList<Ponuda>();
        ponudaArrayList.add(ponuda.get(i));

        System.out.println("Trenutno ima: " + getItemCount());


        PonudeViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AppCompatActivity activity = (AppCompatActivity) context;
                Fragment detaljiponude = new DetaljiPonudeFragment();
                Bundle bundle = new Bundle();
                activity.getSupportFragmentManager().beginTransaction();
                bundle.putParcelableArrayList("ponuda", ponudaArrayList);
                detaljiponude.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().addToBackStack(null);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.linearlayout, detaljiponude).commit();

            }
        });

    }


    public void clear() {
        Ponuda.deleteAll();
    }



    @Override
    public int getItemCount() {
        return ponuda.size();
    }
}
