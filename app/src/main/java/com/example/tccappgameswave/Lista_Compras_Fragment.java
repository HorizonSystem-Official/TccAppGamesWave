package com.example.tccappgameswave;

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

public class Lista_Compras_Fragment extends Fragment implements RecyclerViewInterface{

    private Retrofit retrofitItensCarrinho;

    List<ItemCarrinho> ItemCarrinhoList;
    AdapterListItensRecycler adapter;
    public RecyclerView recyclerItemCarrinho;

    String URL="https://lostorangephone79.conveyor.cloud/api/Carrinho/";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ItemCarrinhoList = new ArrayList<>();

        retrofitItensCarrinho = new Retrofit.Builder()
                .baseUrl(URL)                                       //endere-ço do webservice
                .addConverterFactory(GsonConverterFactory.create()) //conversor
                .build();

        //lista os itens
        MostraItemCarrinho();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_lista__compras_, container, false);

        //inicia o recyclerView
        recyclerItemCarrinho=(RecyclerView)view.findViewById(R.id.ListItensCarrinho);
        recyclerItemCarrinho.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new AdapterListItensRecycler(getContext(), ItemCarrinhoList, this) {
            @Override
            public void onItemClick(int position) {
                Log.i("Lista de Jogos", String.valueOf(ItemCarrinhoList.get(position).getCodProd()));
            }
        };
        recyclerItemCarrinho.setAdapter(adapter);
        recyclerItemCarrinho.setFocusable(false);
        recyclerItemCarrinho.setVisibility(View.VISIBLE);

        return view;
    }

    private void MostraItemCarrinho() {
        //pega o cpf
        String sCpf = "333.333.333-33";

        //pesquisa
        RESTService restService = retrofitItensCarrinho.create(RESTService.class);
        Call<List<ItemCarrinho>> call= restService.ItensCarrinho(sCpf);
        //executa e mostra a requisisao
        call.enqueue(new Callback<List<ItemCarrinho>>() {
            @Override
            public void onResponse(Call<List<ItemCarrinho>> call, Response<List<ItemCarrinho>> response) {
                if (response.isSuccessful()) {
                    ItemCarrinhoList = response.body();
                    adapter.setMovieList(ItemCarrinhoList);
                    Log.i("Lista de Jogos", String.valueOf(ItemCarrinhoList));
                }
            }

            @Override
            public void onFailure(Call<List<ItemCarrinho>> call, Throwable t) {
                Log.i("Ocorreu um erro ao tentar consultar o Perfil. Erro:", t.getMessage());
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Log.i("Lista de Jogos", String.valueOf(ItemCarrinhoList.get(position).getCodProd()));
    }
}