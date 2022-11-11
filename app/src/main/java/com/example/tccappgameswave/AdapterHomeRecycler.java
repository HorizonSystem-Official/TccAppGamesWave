package com.example.tccappgameswave;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

import jp.wasabeef.picasso.transformations.CropSquareTransformation;


public class AdapterHomeRecycler extends RecyclerView.Adapter<AdapterHomeRecycler.ProdutoViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;

    Context context;
    List<Produto> produtoList;

    public AdapterHomeRecycler(Context context, List<Produto> listProd, RecyclerViewInterface recyclerViewInterface){
        this.context=context;
        this.produtoList=listProd;
        this.recyclerViewInterface=recyclerViewInterface;
    }

    public void setMovieList(List<Produto> listProd) {
        this.produtoList = listProd;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    //define o layout usadado
    public AdapterHomeRecycler.ProdutoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.produto_item,parent,false);
        return new ProdutoViewHolder(view,recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHomeRecycler.ProdutoViewHolder holder, int position) {
        //define da onde vem os valores
        Picasso.get()
                .load(produtoList.get(position).getImgCapa())
                .placeholder(R.mipmap.ic_launcher_round)
                .transform(new CropSquareTransformation())
                .error(R.mipmap.ic_launcher_round)
                .into(holder.imgviewProd);

        String prodNome =produtoList.get(position).getProdNome();

        if(prodNome.length()>12){
            holder.txtViewProdNome.setText(prodNome.substring(0, 9)+"...");
        }else {
            holder.txtViewProdNome.setText(prodNome);
        }

        String precoProd=produtoList.get(position).getProdValor().toString();
        String penultimaChar= String.valueOf(precoProd.charAt(precoProd.length() - 2));
        if(penultimaChar.equals(".")){
            holder.TxtViewProdPreco.setText("R$: "+precoProd+"0");
        }
        else
        holder.TxtViewProdPreco.setText("R$: "+precoProd);
    }

    @Override
    public int getItemCount() {
        return produtoList == null ? 0 : produtoList.size();
    }

    //define os campo com o layout
    public class ProdutoViewHolder extends RecyclerView.ViewHolder {
         ImageView imgviewProd;
         TextView txtViewProdNome;
         TextView TxtViewProdPreco;

        public ProdutoViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            imgviewProd = itemView.findViewById(R.id.imgviewProd);
            txtViewProdNome = itemView.findViewById(R.id.txtViewProdNome);
            TxtViewProdPreco = itemView.findViewById(R.id.TxtViewProdPreco);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   if(recyclerViewInterface!=null){
                       int pos=getAdapterPosition();

                       if(pos!=RecyclerView.NO_POSITION){
                           recyclerViewInterface.onItemClick(pos);
                       }
                   }

                }
            });
        }
    }
}
