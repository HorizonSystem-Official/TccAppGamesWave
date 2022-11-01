package com.example.tccappgameswave;

import androidx.appcompat.app.AppCompatActivity;

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
    private final String URL = "https://funshinybook65.conveyor.cloud/api/Produto/";

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

        ImageView btnVoltarHome = (ImageView) findViewById(R.id.imageViewVoltarHome);
        btnVoltarHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TelaHome();
            }
        });
    }

    private void MostraUmProd() {
        Intent intent = getIntent();
        int codProd = intent.getIntExtra("codProduto",2);

        //pesquisa
        RESTService restService = retrofitProd.create(RESTService.class);
        Call<List<Produto>> call= restService.MostraProdDetalhes(codProd);


        //executa e mostra a requisisao
        call.enqueue(new Callback<List<Produto>>() {
            @Override
            public void onResponse(Call<List<Produto>> call, Response<List<Produto>> response) {
                if (response.isSuccessful()) {
                    prod = response.body();
                    Log.i("Lista de Jogos", String.valueOf(prod));

                    //mostra dados na tela
                    /*Picasso.get().load(prod.getImgCapa()).into(imgProd);
                    textNomeProd.setText(prod.get(position).getProdNome());
                    textCat.setText(prod.getProdTipo());
                    textDateLanc.setText(prod.getProdAnoLanc());
                    textDesc.setText(prod.getProdDesc());*/
                }
            }

            @Override
            public void onFailure(Call<List<Produto>> call, Throwable t) {
                Log.i("Ocorreu um erro ao tentar consultar o Perfil. Erro:", t.getMessage());
            }
        });
    }

    public  void TelaHome(){
        Intent TelaHome = new Intent(getApplicationContext(), Home.class);
        startActivity(TelaHome);
    }
}