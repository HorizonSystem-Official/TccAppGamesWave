package com.example.tccappgameswave;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tccappgameswave.Models.Produto;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.CropSquareTransformation;

public class AdpterPesquisas extends RecyclerView.Adapter<AdpterPesquisas.ProdutoPesqViewHolder>{

    Context context;
    List<Produto> produtoList;

    public AdpterPesquisas(Context context, List<Produto> listProd){
        this.context=context;
        this.produtoList=listProd;
    }

    public void setMovieList(List<Produto> listProd) {
        this.produtoList = listProd;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public AdpterPesquisas.ProdutoPesqViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_pesquisado,parent,false);
        return new AdpterPesquisas.ProdutoPesqViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdpterPesquisas.ProdutoPesqViewHolder holder, int position) {
        //define da onde vem os valores
        Picasso.get()
                .load(produtoList.get(position).getImgCapa())
                .placeholder(R.mipmap.ic_launcher_round)
                .transform(new CropSquareTransformation())
                .error(R.mipmap.ic_launcher_round)
                .into(holder.imgviewProd);

        String prodNome =produtoList.get(position).getProdNome();

        holder.txtViewProdNome.setText(prodNome);

        String precoProd=produtoList.get(position).getProdValor().toString();
        String penultimaChar= String.valueOf(precoProd.charAt(precoProd.length() - 2));
        if(penultimaChar.equals(".")){
            holder.TxtViewProdPreco.setText("R$: "+precoProd+"0");
        }
        else
            holder.TxtViewProdPreco.setText("R$: "+precoProd);

        //onclick abre produto
        int idProd= produtoList.get(position).getCodProd();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent AbreProdFromPesq = new Intent(context, DetelhesProd.class);
                AbreProdFromPesq.putExtra("codProduto",idProd);
                AbreProdFromPesq.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(AbreProdFromPesq);
            }
        });
    }


    @Override
    public int getItemCount() {
        return produtoList == null ? 0 : produtoList.size();
    }

    //define os campo com o layout
    public class ProdutoPesqViewHolder extends RecyclerView.ViewHolder {
        ImageView imgviewProd;
        TextView txtViewProdNome;
        TextView TxtViewProdPreco;

        public ProdutoPesqViewHolder(@NonNull View itemView) {
            super(itemView);

            imgviewProd = itemView.findViewById(R.id.imageViewProdItem);
            txtViewProdNome = itemView.findViewById(R.id.textViewNomeProdItem);
            TxtViewProdPreco = itemView.findViewById(R.id.textViewTotal);
        }
    }
}
