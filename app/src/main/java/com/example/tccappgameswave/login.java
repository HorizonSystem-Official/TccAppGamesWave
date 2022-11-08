package com.example.tccappgameswave;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class login extends AppCompatActivity {

    private String fileCodUser = "CodUser.txt";
    String LinkApi;
    Retrofit retrofitLoginCli;
    EditText emailEdt,senhaEdt;
    Cliente cli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

         emailEdt =(EditText) findViewById(R.id.editUserLogin);
         senhaEdt =(EditText) findViewById(R.id.EditSenhaLogin);

        readDataLinkApi();
        gravaDataCpf();
        LoginCli();

        //add item
        retrofitLoginCli= new Retrofit.Builder()
                .baseUrl(LinkApi+"Cliente/").
                addConverterFactory(GsonConverterFactory.create())
                .build();

        TextView txtEsqueceu = (TextView) findViewById(R.id.txtEsqueceu);
        txtEsqueceu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TelaMudaSenha();
            }
        });

        TextView TxtCriarConta = (TextView) findViewById(R.id.TxtCriarConta);
        TxtCriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TelaCriaConta();
            }
        });

        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TelaHome();
            }
        });
    }

    public  void TelaMudaSenha(){
        Intent MudaSenha = new Intent(getApplicationContext(), esqueceuAsenha.class);
        startActivity(MudaSenha);
        gravaDataCpf();
    }

    public  void TelaCriaConta(){
        Intent CriaConta = new Intent(getApplicationContext(),cadastroCliente.class);
        startActivity(CriaConta);
    }

    public  void TelaHome(){
        //LoginCli(emailEdt.getText().toString(), senhaEdt.getText().toString());
        //LoginCli("Jularia@gmail.com", "123456");

        Intent Home = new Intent(getApplicationContext(),Home.class);
        startActivity(Home);
    }

    private void LoginCli() {
        //pesquisa
        String email="Jularia@gmail.com";
        String senha="123456";

        RESTService restService = retrofitLoginCli.create(RESTService.class);
        Call<Cliente> call= restService.LoginCliente(email, senha);

        //executa e mostra a requisisao
        call.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                if (response.isSuccessful()) {
                    cli=response.body();
                    //Log.i("Lista de Jogos", cli.getCPF());
                }
            }

            @Override
            public void onFailure(Call<Cliente> call, Throwable t) {
                Log.i("Ocorreu um erro ao tentar entrar. Erro:", t.getMessage());
            }
        });
    }

    //ler aquivo da memoria
    private void readDataLinkApi() {
        try {
            FileInputStream fin = openFileInput("LinkApi.txt");
            int a;
            //constroi a string letra por letra
            StringBuilder temp = new StringBuilder();
            while ((a = fin.read()) != -1)
            {
                temp.append((char)a);
            }

            LinkApi=temp.toString();
            fin.close();//fecha busca
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    //escreve na memoria
    private void gravaDataCpf(){
        try {
            FileOutputStream fos = openFileOutput(fileCodUser, Context.MODE_PRIVATE);
            String dataCpfCli = "333.333.333-33";
            //trnforma em byter e grava
            fos.write(dataCpfCli.getBytes());
            fos.flush();
            fos.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}