package com.example.tccappgameswave;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tccappgameswave.Models.ItemCarrinho;
import com.squareup.picasso.Picasso;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import jp.wasabeef.picasso.transformations.CropSquareTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class AdapterListItensRecycler extends RecyclerView.Adapter<AdapterListItensRecycler.ItemViewHolder> {

    Context context;
    List<ItemCarrinho> ItemCarrinhoList;

    private Retrofit retrofitItem;
    String sCpf;
    String LinkApi;


    protected AdapterListItensRecycler(Context context, List<ItemCarrinho> itemCarrinhoList) {
        this.context=context;
        this.ItemCarrinhoList=itemCarrinhoList;
    }

    public void setMovieList(List<ItemCarrinho> itemCarrinhoList) {
        this.ItemCarrinhoList = itemCarrinhoList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterListItensRecycler.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carrinho,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListItensRecycler.ItemViewHolder holder, int position) {
//define da onde vem os valores
        Picasso.get()
                .load(ItemCarrinhoList.get(position).getImgCapa())
                .placeholder(R.mipmap.ic_launcher_round)
                .transform(new CropSquareTransformation())
                .error(R.mipmap.ic_launcher_round)
                .into(holder.imgProdItem);

        holder.textNomeProdItem.setText(ItemCarrinhoList.get(position).getProdNome());
        holder.textQtn.setText("Quantidade: "+ItemCarrinhoList.get(position).getQtnProd());
        holder.textTotal.setText(ItemCarrinhoList.get(position).getValorTotal().toString());

        //onclick abre produto
        int idProd= ItemCarrinhoList.get(position).getCodProd();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Id prod", String.valueOf(idProd));

                Intent AbreProd = new Intent(context, DetelhesProd.class);
                AbreProd.putExtra("codProduto",idProd);
                context.startActivity(AbreProd);
            }
        });

        //onclick abre produto
        holder.BtnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readDataCpf();
                removeItem(idProd, sCpf);

                Intent TelaHome = new Intent(context, Home.class);
                int codFragment=1;
                TelaHome.putExtra("codFragment",codFragment);
                context.startActivity(TelaHome);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ItemCarrinhoList == null ? 0 : ItemCarrinhoList.size();
    }

    //abre os detalhes do produto
    public abstract void onItemClick(int position);

    //define os campo com o layout
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProdItem;
        TextView textNomeProdItem;
        TextView textQtn;
        TextView textTotal;
        ImageButton BtnRemove;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProdItem = itemView.findViewById(R.id.imageViewProdItem);
            textNomeProdItem = itemView.findViewById(R.id.textViewNomeProdItem);
            textQtn = itemView.findViewById(R.id.textViewQtn);
            textTotal = itemView.findViewById(R.id.textViewTotal);
            BtnRemove = itemView.findViewById(R.id.imageViewProdDelete);
        }
    }

    private void removeItem(int codProd, String cpf) {
        readDataLinkApi();
        //mostra prod
        retrofitItem = new Retrofit.Builder()
                .baseUrl(LinkApi+"Carrinho/")                                       //endereço do webservice
                .addConverterFactory(GsonConverterFactory.create()) //conversor
                .build();

        //pesquisa
        RESTService restService = retrofitItem.create(RESTService.class);
        Call<Void> call= restService.RemoveItensCarrinho(codProd, cpf);
        //executa e mostra a requisisao
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.i("Apagou", String.valueOf(codProd));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i("Ocorreu um erro ao tentar consultar o Perfil. Erro:", t.getMessage());
            }
        });
    }

    //ler Link Da api da memoria
    private void readDataLinkApi() {
        try {
            FileInputStream fin = context.openFileInput("LinkApi.txt");
            int a;
            //constroi a string letra por letra
            StringBuilder temp = new StringBuilder();
            while ((a = fin.read()) != -1) {
                temp.append((char)a);
            }

            LinkApi=temp.toString();
            fin.close();//fecha busca
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    //ler cpf da memoria
    private void readDataCpf() {
        try {
            FileInputStream fin = context.openFileInput("CodUser.txt");
            int a;
            //constroi a string letra por letra
            StringBuilder temp = new StringBuilder();
            while ((a = fin.read()) != -1)
            {
                temp.append((char)a);
            }

            sCpf=temp.toString();
            fin.close();//fecha busca
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
