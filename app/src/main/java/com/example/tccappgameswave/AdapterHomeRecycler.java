package com.example.tccappgameswave;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterHomeRecycler extends RecyclerView.Adapter<AdapterHomeRecycler.ProdutoViewHolder> {

    Context context;
    List<Produto> listProd;

    public AdapterHomeRecycler(Context context, List<Produto> listProd){
        this.context=context;
        this.listProd=listProd;
    }

    public void setMovieList(List<Produto> listProd) {
        this.listProd = listProd;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    //define o layout usadado
    public AdapterHomeRecycler.ProdutoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.produto_item,parent,false);
        return new ProdutoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHomeRecycler.ProdutoViewHolder holder, int position) {
        //define da onde vem os valores
        Picasso.get()
                .load(listProd.get(position).getImgCapa())
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .into(holder.imgviewProd);

        holder.txtViewProdNome.setText(listProd.get(position).getProdNome());
        holder.TxtViewProdPreco.setText(listProd.get(position).getProdValor().toString());
    }

    @Override
    public int getItemCount() {
        return listProd == null ? 0 : listProd.size();
    }

    //define os campo com o layout
    public static class ProdutoViewHolder extends RecyclerView.ViewHolder {
         ImageView imgviewProd;
         TextView txtViewProdNome;
         TextView TxtViewProdPreco;

        public ProdutoViewHolder(@NonNull View itemView) {
            super(itemView);

            imgviewProd = itemView.findViewById(R.id.imgviewProd);
            txtViewProdNome = itemView.findViewById(R.id.txtViewProdNome);
            TxtViewProdPreco = itemView.findViewById(R.id.TxtViewProdPreco);
        }
    }

}
