package com.example.tccappgameswave;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetelhesProd extends AppCompatActivity {
    private final String URL = "https://oldsparklyboat45.conveyor.cloud/api/Produto/";

    private Retrofit retrofitProd;
    List<Produto> prod;

    ImageView imgProd;
    TextView textNomeProd;
    TextView textCat;
    TextView textDateLanc;
    TextView textDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detelhes_prod);

        ImageView imgProd = (ImageView) findViewById(R.id.imgviewProd);
        TextView textNomeProd =(TextView) findViewById(R.id.textViewNomeProduto);
        TextView textCat =(TextView) findViewById(R.id.textViewCat);
        TextView textDateLanc =(TextView) findViewById(R.id.textViewDateLanc);
        TextView textDesc =(TextView) findViewById(R.id.textViewDesc);

        retrofitProd = new Retrofit.Builder()
                .baseUrl(URL)                                       //endereço do webservice
                .addConverterFactory(GsonConverterFactory.create()) //conversor
                .build();
        //lista os jogos
        MostraUmProd();
    }

    private void MostraUmProd() {
        //pega categoria
        int codProd=3;

        //pesquisa
        RESTService restService = retrofitProd.create(RESTService.class);
        Call<List<Produto>> call= restService.MostraProdDetalhes(codProd);
        //executa e mostra a requisisao
        call.enqueue(new Callback<List<Produto>>() {
            @Override
            public void onResponse(Call<List<Produto>> call, Response <List<Produto>> response) {
                if (response.isSuccessful()) {
                    prod = response.body();
                    Log.i("Lista de Jogos", String.valueOf(prod));

                }
            }

            @Override
            public void onFailure(Call<List<Produto>> call, Throwable t) {
                Log.i("Ocorreu um erro ao tentar consultar o Perfil. Erro:", t.getMessage());
            }
        });
    }
}