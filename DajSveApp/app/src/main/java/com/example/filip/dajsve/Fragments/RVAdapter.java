package com.example.filip.dajsve.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import java.util.List;
import com.squareup.picasso.Picasso;
import android.content.Context;
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
    RVAdapter(List<Ponuda> ponuda){
        this.ponuda = ponuda;
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
        Context context =PonudeViewHolder.ponudaSlika.getContext();
        Picasso.with(context).load(ponuda.get(i).getURL()).into(PonudeViewHolder.ponudaSlika);
    }

    @Override
    public int getItemCount() {
        return ponuda.size();
    }
}
