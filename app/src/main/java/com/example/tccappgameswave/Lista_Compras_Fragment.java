package com.example.tccappgameswave;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Lista_Compras_Fragment extends Fragment {

    private Retrofit retrofitItensCarrinho;

    Carrinho carrinho;
    List<ItemCarrinho> ItemCarrinhoList;
    AdapterListItensRecycler adapter;
    public RecyclerView recyclerItemCarrinho;

    String sCpf;
    String LinkApi;

    TextView txtViewTotal;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ItemCarrinhoList = new ArrayList<>();

        readDataLinkApi();
        readDataCpf();

        retrofitItensCarrinho = new Retrofit.Builder()
                .baseUrl(LinkApi+"Carrinho/")                                       //endere-ço do webservice
                .addConverterFactory(GsonConverterFactory.create()) //conversor
                .build();

        //lista os itens
        MostraItemCarrinho();
        MostraTotalCarrinho();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_lista__compras_, container, false);

        txtViewTotal=(TextView)view.findViewById(R.id.textViewTotal);

        //inicia o recyclerView
        recyclerItemCarrinho=(RecyclerView)view.findViewById(R.id.ListItensCarrinho);
        recyclerItemCarrinho.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new AdapterListItensRecycler(getContext(), ItemCarrinhoList) {
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
        //pesquisa
        RESTService restService = retrofitItensCarrinho.create(RESTService.class);
        Call<List<ItemCarrinho>> call= restService.ItensCarrinho(sCpf);
        Log.i("Lista de Jogos", sCpf);
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

    private void MostraTotalCarrinho() {
        //pesquisa
        RESTService restService = retrofitItensCarrinho.create(RESTService.class);
        Call<Carrinho> call= restService.valorTotalCarrrinho(sCpf);
        call.enqueue(new Callback<Carrinho>() {
            @Override
            public void onResponse(Call<Carrinho> call, Response<Carrinho> response) {
                if (response.isSuccessful()) {
                    carrinho=response.body();

                    String carrinhoTotal=String.valueOf(carrinho.getValorTotal());
                    String penultimaChar= String.valueOf(carrinhoTotal.charAt(carrinhoTotal.length() - 2));
                    if(penultimaChar.equals(".")){
                        txtViewTotal.setText("R$: "+carrinhoTotal+"0");
                    }
                    else
                        txtViewTotal.setText("R$: "+carrinhoTotal);
                }
            }

            @Override
            public void onFailure(Call<Carrinho> call, Throwable t) {
                Log.i("Ocorreu um erro ao tentar consultar o Perfil. Erro:", t.getMessage());
            }
        });
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

    //ler cpf da memoria
    private void readDataCpf() {
        try {
            FileInputStream fin = getActivity().openFileInput("CodUser.txt");
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