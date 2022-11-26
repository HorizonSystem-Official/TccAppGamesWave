package com.example.tccappgameswave;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tccappgameswave.Models.Produto;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Pesquisa extends AppCompatActivity {

    private Retrofit retrofitPesquisaProd;

    List<Produto> produtoList;

    AdpterPesquisas adapterPesquisas;
    public RecyclerView recyclerViewPesquisa;
    EditText editPesquisa;
    String LinkApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa);

        readDataLinkApi();

        retrofitPesquisaProd = new Retrofit.Builder()
                .baseUrl(LinkApi+"Produto/")                                       //endere-ço do webservice
                .addConverterFactory(GsonConverterFactory.create()) //conversor
                .build();

        //pesquisa os produtas e configura a lista
        PesquisaProds();
        recyclerViewPesquisa=(RecyclerView)findViewById(R.id.ListItensPesquisa);
        recyclerViewPesquisa.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        adapterPesquisas = new AdpterPesquisas(getApplicationContext(), produtoList);
        recyclerViewPesquisa.setAdapter(adapterPesquisas);

        editPesquisa=(EditText) findViewById(R.id.TxtEdtPesquisa);
        editPesquisa.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int i, KeyEvent keyEvent) {
                if(keyEvent!=null&&(keyEvent.getKeyCode()==KeyEvent.KEYCODE_ENTER)|| (i== EditorInfo.IME_ACTION_DONE)){
                    NovaPesqusia();
                }
                return false;
            }
        });

        ImageView BtnPesquisa=(ImageView) findViewById(R.id.imageViewPesquisa);
        BtnPesquisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NovaPesqusia();
            }
        });

        //volta
        ImageView btnVoltarHome = (ImageView) findViewById(R.id.imageViewVoltarHome);
        btnVoltarHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TelaHome();
            }
        });
    }

    //faz um outra pesquisa
    private void PesquisaProds() {
        Intent intent = getIntent();
        String Txtpesquisa= intent.getStringExtra("TxtPesquisa");

        //pesquisa
        RESTService restService = retrofitPesquisaProd.create(RESTService.class);
        Call<List<Produto>> call= restService.PesquisaProduto(Txtpesquisa);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<Produto>> call, Response<List<Produto>> response) {
                if (response.isSuccessful()) {
                    produtoList = response.body();
                    adapterPesquisas.setMovieList(produtoList);
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

    public  void NovaPesqusia(){
        if(!editPesquisa.equals("")){
            String TxtPesquisa=editPesquisa.getText().toString();

            this.finish();
            //abre a tela pesquisa e manda o texto para pesquisa
            Intent TelaPesqusia = new Intent(getApplicationContext(),Pesquisa.class);
            TelaPesqusia.putExtra("TxtPesquisa",TxtPesquisa);
            startActivity(TelaPesqusia);
        }
    }

    //ler Link Da api da memoria
    private void readDataLinkApi() {
        try {
            FileInputStream fin = getApplicationContext().openFileInput("LinkApi.txt");
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