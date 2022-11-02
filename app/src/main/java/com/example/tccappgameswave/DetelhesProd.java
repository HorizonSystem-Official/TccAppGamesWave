package com.example.tccappgameswave;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetelhesProd extends AppCompatActivity {
    private final String URL = "https://lostorangephone79.conveyor.cloud/api/Produto/";

    private Retrofit retrofitProd;
    Produto prod;

    ImageView imgProd;
    TextView textNomeProd;
    TextView textCat;
    TextView textDateLanc;
    TextView textDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detelhes_prod);

         imgProd = (ImageView) findViewById(R.id.imgviewProd);
         textNomeProd =(TextView) findViewById(R.id.textViewNomeProduto);
         textCat =(TextView) findViewById(R.id.textViewCat);
         textDateLanc =(TextView) findViewById(R.id.textViewDateLanc);
         textDesc =(TextView) findViewById(R.id.textViewDesc);

        retrofitProd = new Retrofit.Builder()
                .baseUrl(URL)                                       //endereço do webservice
                .addConverterFactory(GsonConverterFactory.create()) //conversor
                .build();
        //lista os jogos
        MostraUmProd();

        //volta
        ImageView btnVoltarHome = (ImageView) findViewById(R.id.imageViewVoltarHome);
        btnVoltarHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TelaHome();
            }
        });

        //adiciona ao carrinho
        Button btnAddCarrinho = (Button) findViewById(R.id.btnAddCarrinho);
        btnAddCarrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertItem();
            }
        });
    }

    private void MostraUmProd() {
        Intent intent = getIntent();
        int codProd = intent.getIntExtra("codProduto",2);

        //pesquisa
        RESTService restService = retrofitProd.create(RESTService.class);
        Call<Produto> call= restService.MostraProdDetalhes(codProd);

        //executa e mostra a requisisao
        call.enqueue(new Callback<Produto>() {
            @Override
            public void onResponse(Call<Produto> call, Response<Produto> response) {
                if (response.isSuccessful()) {

                    prod=response.body();
                    //Log.i("Lista de Jogos", String.valueOf(prod));

                    //mostra dados na tela
                    Picasso.get().load(prod.getImgCapa()).into(imgProd);
                    textNomeProd.setText(prod.getProdNome());
                    textCat.setText(prod.getProdTipo());
                    textDateLanc.setText(prod.getProdAnoLanc());
                    textDesc.setText(prod.getProdDesc());
                }
            }

            @Override
            public void onFailure(Call<Produto> call, Throwable t) {
                Log.i("Ocorreu um erro ao tentar consultar o Perfil. Erro:", t.getMessage());
            }
        });
    }

    public  void TelaHome(){
        Intent TelaHome = new Intent(getApplicationContext(), Home.class);
        startActivity(TelaHome);
    }

    public  void InsertItem(){
        Intent TelaHome = new Intent(getApplicationContext(), Home.class);
        int codFragment=1;
        TelaHome.putExtra("codFragment",codFragment);
        startActivity(TelaHome);
    }

}