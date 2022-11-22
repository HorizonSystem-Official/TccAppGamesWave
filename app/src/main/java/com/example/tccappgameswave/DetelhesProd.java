package com.example.tccappgameswave;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.tccappgameswave.Models.Comentario;
import com.example.tccappgameswave.Models.ItemCarrinho;
import com.example.tccappgameswave.Models.Produto;
import com.squareup.picasso.Picasso;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import jp.wasabeef.picasso.transformations.CropSquareTransformation;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetelhesProd extends AppCompatActivity {

    String LinkApi;
    String cpf;
    int codProd;

    private Retrofit retrofitProd, retrofitComent,retrofitAddItem;

    Produto prod;
    List<Comentario> ListComent;

    RecyclerView recyclerView;
    AdapterComentariosRecycler adapter;
    ImageView imgProd, ImgClasInd;
    TextView textNomeProd, textCat, textDateLanc, textDesc, textPreco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detelhes_prod);

        readDataLinkApi();
        readDataCpf();

         Intent intent = getIntent();
         codProd = intent.getIntExtra("codProduto",0);

         imgProd = (ImageView) findViewById(R.id.imgviewProd);
         textNomeProd =(TextView) findViewById(R.id.textViewNomeProduto);
         textCat =(TextView) findViewById(R.id.textViewCat);
         ImgClasInd =(ImageView) findViewById(R.id.ImgClasInd);
         textDateLanc =(TextView) findViewById(R.id.textViewDateLanc);
         textDesc =(TextView) findViewById(R.id.textViewDesc);
         textPreco =(TextView) findViewById(R.id.textViewPreco);


        //inicia o recyclerView
        recyclerView=(RecyclerView)findViewById(R.id.recyclerViewComentario);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new AdapterComentariosRecycler(getApplicationContext(), ListComent);
        recyclerView.setAdapter(adapter);
        recyclerView.setFocusable(false);
        recyclerView.setVisibility(View.VISIBLE);

        //mostra prod
        retrofitProd = new Retrofit.Builder()
                .baseUrl(LinkApi+"Produto/")                                       //endereço do webservice
                .addConverterFactory(GsonConverterFactory.create()) //conversor
                .build();

        //lista comentarios
        retrofitComent = new Retrofit.Builder()
                .baseUrl(LinkApi+"Produto/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        HttpLoggingInterceptor httpLoggingInterceptor=new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient=new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();
        //add item
        retrofitAddItem= new Retrofit.Builder()
                .baseUrl(LinkApi+"Carrinho/").
                addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        //lista os jogos
        MostraUmProd();

        //mostra comentarios
        MostraComentarios();

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
                AddItemCar(InsertProd());
            }
        });
    }

    private void MostraUmProd() {

        //pesquisa
        RESTService restService = retrofitProd.create(RESTService.class);
        Call<Produto> call= restService.MostraProdDetalhes(codProd);

        //executa e mostra a requisisao
        call.enqueue(new Callback<Produto>() {
            @Override
            public void onResponse(Call<Produto> call, Response<Produto> response) {
                if (response.isSuccessful()) {

                    prod=response.body();

                    //mostra dados na tela
                    Picasso.get().load(prod.getImgCapa()).transform(new CropSquareTransformation()).into(imgProd);
                    textNomeProd.setText(prod.getProdNome());
                    textCat.setText(prod.getProdTipo());

                    switch (prod.getProdFaixaEtaria()){
                        case "L":
                            ImgClasInd.setImageResource(R.drawable.classind_l);
                            break;

                        case "+10":
                            ImgClasInd.setImageResource(R.drawable.classind_10);
                            break;

                        case "+14":
                            ImgClasInd.setImageResource(R.drawable.classind_14);
                            break;

                        case "+16":
                            ImgClasInd.setImageResource(R.drawable.classind_16);
                            break;

                        case "+18":
                            ImgClasInd.setImageResource(R.drawable.classind_18);
                            break;

                        default:
                            ImgClasInd.setImageResource(R.drawable.classind_14);
                    }


                    textDateLanc.setText(prod.getProdAnoLanc());
                    textDesc.setText(prod.getProdDesc());

                    String precoProd=prod.getProdValor().toString();
                    String penultimaChar= String.valueOf(precoProd.charAt(precoProd.length() - 2));
                    if(penultimaChar.equals(".")){
                        textPreco.setText("R$"+precoProd+"0");
                    }
                    else
                    textPreco.setText("R$"+precoProd);
                }
            }

            @Override
            public void onFailure(Call<Produto> call, Throwable t) {
                Log.i("Ocorreu um erro ao tentar consultar o Perfil. Erro:", t.getMessage());
            }
        });
    }

    private void MostraComentarios() {
        //pesquisa
        RESTService restService = retrofitComent.create(RESTService.class);
        Call<List<Comentario>> call= restService.ListComentarios(codProd);
        //executa e mostra a requisisao
        call.enqueue(new Callback<List<Comentario>>() {
            @Override
            public void onResponse(Call<List<Comentario>> call, Response<List<Comentario>> response) {
                if (response.isSuccessful()) {
                    ListComent = response.body();
                    adapter.setMovieList(ListComent);
                }
            }

            @Override
            public void onFailure(Call<List<Comentario>> call, Throwable t) {
                Log.i("Ocorreu um erro ao tentar consultar o Perfil. Erro:", t.getMessage());
            }
        });
    }

    public ItemCarrinho InsertProd(){
        ItemCarrinho itemCarrinho=new ItemCarrinho();
        itemCarrinho.setQtnProd(1);
        itemCarrinho.setCodProd(codProd);
        itemCarrinho.setCpf(cpf);
        return itemCarrinho;
    }
    private void AddItemCar(ItemCarrinho itemCarrinho){

        RESTService restService= retrofitAddItem.create(RESTService.class);
        Log.i("codigo", String.valueOf(codProd));
        Call<Void> call= restService.AddItensCarrinho(itemCarrinho);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    //abre tela de lista de carrinho
                    Intent TelaHome = new Intent(getApplicationContext(), Home.class);
                    int codFragment=1;
                    TelaHome.putExtra("codFragment",codFragment);
                    startActivity(TelaHome);
                }
                Log.i("Deu certo:", String.valueOf(response.code()));
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i("Ocorreu um erro ao tentar comprar. Erro:", t.getMessage());
            }
        });
    }

    public  void TelaHome(){
        Intent TelaHome = new Intent(getApplicationContext(), Home.class);
        startActivity(TelaHome);
    }

    //ler Link Da api da memoria
    private void readDataLinkApi() {
        try {
            FileInputStream fin = openFileInput("LinkApi.txt");
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

    private void readDataCpf() {
        try {
            FileInputStream fin = openFileInput("CodUser.txt");
            int a;
            //constroi a string letra por letra
            StringBuilder temp = new StringBuilder();
            while ((a = fin.read()) != -1)
            {
                temp.append((char)a);
            }

            cpf=temp.toString();
            fin.close();//fecha busca
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}