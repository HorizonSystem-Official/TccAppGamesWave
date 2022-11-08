package com.example.tccappgameswave;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment implements RecyclerViewInterface{

    private Retrofit retrofitHomeProd;

     List<Produto> produtoListTiro,produtoListTerror,produtoListRPG;
     List<Produto> produtoList;

     AdapterHomeRecycler adapterTiro, adapterTerror, adapterRPG;
     AdapterHomeRecycler adapter;
     public  RecyclerView recyclerViewTiro, recyclerViewTerror, recyclerViewRPG;
     public  ImageView banner;

    String LinkApi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readDataLinkApi();

        produtoListTiro = new ArrayList<>();
        produtoListTerror = new ArrayList<>();
        produtoListRPG = new ArrayList<>();
        produtoList = new ArrayList<>();

        retrofitHomeProd = new Retrofit.Builder()
                .baseUrl(LinkApi+"Produto/")                                       //endere-ço do webservice
                .addConverterFactory(GsonConverterFactory.create()) //conversor
                .build();

        //lista os jogos
        MostraProdsTiro();
        MostraProdsRPG();
        MostraProdsTerror();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);

        ImageView banner=(ImageView) view.findViewById(R.id.banner);
        banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetelhesProd();
            }
        });

        Picasso.get()
                .load("https://www.brickfanatics.com/wp-content/uploads/LEGO-Star-Wars-The-Skywalker-Saga-glitched-title-screen-800x445.jpg")
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .into(banner);

        //inicia o recyclerView
        recyclerViewTiro=(RecyclerView)view.findViewById(R.id.recyclerview_ProdCatTiro);
        recyclerViewTerror=(RecyclerView)view.findViewById(R.id.recyclerview_ProdCatTerror);
        recyclerViewRPG=(RecyclerView)view.findViewById(R.id.recyclerview_ProdCatRPG);

        recyclerViewTiro.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewTerror.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewRPG.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        adapterRPG = new AdapterHomeRecycler(getContext(), produtoListRPG, this);
        adapterTerror = new AdapterHomeRecycler(getContext(), produtoListTerror, this);
        adapter = new AdapterHomeRecycler(getContext(), produtoList, this);

        recyclerViewTiro.setAdapter(adapter);
        recyclerViewTerror.setAdapter(adapterTerror);
        recyclerViewRPG.setAdapter(adapterRPG);

        return view;
    }

    private void MostraProdsTiro() {
        //pega categoria
        String sCat = "Tiro";

        //pesquisa
        RESTService restService = retrofitHomeProd.create(RESTService.class);
        Call<List<Produto>> call= restService.MostraProdPorCat(sCat);
        //executa e mostra a requisisao
        call.enqueue(new Callback<List<Produto>>() {
            @Override
            public void onResponse(Call<List<Produto>> call, Response<List<Produto>> response) {
                if (response.isSuccessful()) {
                    produtoList = response.body();
                    adapter.setMovieList(produtoList);
                }
            }

            @Override
            public void onFailure(Call<List<Produto>> call, Throwable t) {
                Log.i("Ocorreu um erro ao tentar consultar o Perfil. Erro:", t.getMessage());
            }
        });
    }

    private void MostraProdsRPG() {
        //pega categoria
        String sCat = "RPG";

        //pesquisa
        RESTService restService = retrofitHomeProd.create(RESTService.class);
        Call<List<Produto>> call= restService.MostraProdPorCat(sCat);
        //executa e mostra a requisisao
        call.enqueue(new Callback<List<Produto>>() {
            @Override
            public void onResponse(Call<List<Produto>> call, Response<List<Produto>> response) {
                if (response.isSuccessful()) {
                    produtoListRPG = response.body();
                    adapterRPG.setMovieList(produtoListRPG);
                }
            }

            @Override
            public void onFailure(Call<List<Produto>> call, Throwable t) {
                Log.i("Ocorreu um erro ao tentar consultar o Perfil. Erro:", t.getMessage());
            }
        });
    }

    private void MostraProdsTerror() {
        //pega categoria
        String sCat = "Terror";

        //pesquisa
        RESTService restService = retrofitHomeProd.create(RESTService.class);
        Call<List<Produto>> call= restService.MostraProdPorCat(sCat);
        //executa e mostra a requisisao
        call.enqueue(new Callback<List<Produto>>() {
            @Override
            public void onResponse(Call<List<Produto>> call, Response<List<Produto>> response) {
                if (response.isSuccessful()) {
                    produtoListTerror = response.body();
                    adapterTerror.setMovieList(produtoListTerror);
                }
            }

            @Override
            public void onFailure(Call<List<Produto>> call, Throwable t) {
                Log.i("Ocorreu um erro ao tentar consultar o Perfil. Erro:", t.getMessage());
            }
        });
    }

    public  void DetelhesProd(){
        Intent DetelhesProd = new Intent(getActivity(), DetelhesProd.class);
        DetelhesProd.putExtra("codProduto",3);
        startActivity(DetelhesProd);
    }

    //abre os detalhes do produto
    @Override
    public void onItemClick(int position) {

        int codProduto=produtoList.get(position).getCodProd();

        /*if(produtoListTiro.get(position).getCodProd()==null){
            Log.i("cod prod que abre:", String.valueOf(produtoListTiro.get(position).getCodProd()));
        }
        else if(produtoListTerror.get(position).getCodProd()==null){
            Log.i("cod prod que abre:", String.valueOf(produtoListTerror.get(position).getCodProd()));
        }

        else if(produtoListRPG.get(position).getCodProd()==null){
            Log.i("cod prod que abre:", String.valueOf(produtoListRPG.get(position).getCodProd()));
        }
        else {
            Log.i("cod prod que abre:","nada");
        }*/

        Intent AbreProd = new Intent(getActivity(), DetelhesProd.class);
        AbreProd.putExtra("codProduto",codProduto);
        startActivity(AbreProd);
    }

    //ler Link Da api da memoria
    private void readDataLinkApi() {
        try {
            FileInputStream fin = getActivity().openFileInput("LinkApi.txt");
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
}