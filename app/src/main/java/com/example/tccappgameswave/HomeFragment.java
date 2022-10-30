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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {
    private final String URL = "https://oldsparklyboat45.conveyor.cloud/api/Produto/";

    private Retrofit retrofitHomeProd;

     List<Produto> produtoList;
     AdapterHomeRecycler adapter;
     public  RecyclerView recyclerView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        produtoList = new ArrayList<>();

        retrofitHomeProd = new Retrofit.Builder()
                .baseUrl(URL)                                       //endere-ço do webservice
                .addConverterFactory(GsonConverterFactory.create()) //conversor
                .build();
        //lista os jogos
        MostraProds();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);

        View banner=(View) view.findViewById(R.id.banner);
        banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetelhesProd();
            }
        });

        //inicia o recyclerView
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerview_ProdCat);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        adapter = new AdapterHomeRecycler(getContext(), produtoList);
        recyclerView.setAdapter(adapter);
        recyclerView.setFocusable(false);
        recyclerView.setVisibility(View.VISIBLE);

        return view;
    }

    private void MostraProds() {
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
                    Log.i("Lista de Jogos", String.valueOf(produtoList));


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
        startActivity(DetelhesProd);
    }
}