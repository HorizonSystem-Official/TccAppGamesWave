package com.example.tccappgameswave;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetelhesProd extends AppCompatActivity {
    private final String URL = "https://oldbluephone64.conveyor.cloud/api/Produto/";

    private Retrofit retrofitProd;
    Produto prod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detelhes_prod);

        retrofitProd = new Retrofit.Builder()
                .baseUrl(URL)                                       //endereço do webservice
                .addConverterFactory(GsonConverterFactory.create()) //conversor
                .build();
        //lista os jogos
        MostraProd();
    }

    private void MostraProd() {
        //pega categoria
        int codProd=1;

        //pesquisa
        RESTService restService = retrofitProd.create(RESTService.class);
        retrofit2.Call<Produto> call= restService.MostraProdDetalhes(codProd);
        //executa e mostra a requisisao
        call.enqueue(new Callback<Produto>() {
            @Override
            public void onResponse(retrofit2.Call<Produto> call, Response<Produto> response) {
                if (response.isSuccessful()) {
                    prod = response.body();
                    Log.i("Lista de Jogos", String.valueOf(prod));
                }
            }

            @Override
            public void onFailure(retrofit2.Call<Produto> call, Throwable t) {
                Log.i("Ocorreu um erro ao tentar consultar o Perfil. Erro:", t.getMessage());
            }
        });
    }
}