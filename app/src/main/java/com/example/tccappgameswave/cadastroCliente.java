package com.example.tccappgameswave;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tccappgameswave.Models.Cliente;

import java.io.FileInputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class cadastroCliente extends AppCompatActivity {

    String LinkApi;
    private Retrofit retrofitCli;
    EditText editNome, editEmail, editCpf, editDataNasc, editTel, editSenha;

    String CliNome, CliEmail, CliCpf, CliNasc, CliTel, CliSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente);

        editNome =(EditText) findViewById(R.id.edit_textNome);
        editEmail =(EditText) findViewById(R.id.edit_textEmail);
        editCpf =(EditText) findViewById(R.id.edit_textCPF);
        editDataNasc =(EditText) findViewById(R.id.edit_textDataNasc);
        editTel =(EditText) findViewById(R.id.edit_textTel);
        editSenha =(EditText) findViewById(R.id.edit_textSenha);

        if(editCpf.length()>0){
            editCpf.addTextChangedListener(Masks.mask(editCpf, "###.###.###-##"));
        }

        readDataLinkApi();

        retrofitCli = new Retrofit.Builder()
                .baseUrl(LinkApi+"Cliente/")                                       //endereço do webservice
                .addConverterFactory(GsonConverterFactory.create()) //conversor
                .build();


        ImageView imgVoltar = (ImageView) findViewById(R.id.imageViewVoltar);
        imgVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TelaLogin();
            }
        });

        //adiciona ao carrinho
        Button btnAddCli = (Button) findViewById(R.id.btnCadastro);
        btnAddCli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //editCpf.addTextChangedListener(Masks.mask(editCpf, "###.###.###-##"));
                //CliCpf=editCpf.getText().toString();

                AddNovoCliente();
            }
        });
    }

    public  void TelaLogin(){
        Intent Login = new Intent(getApplicationContext(), login.class);
        startActivity(Login);
    }

    private void AddNovoCliente(){

        RESTService restService= retrofitCli.create(RESTService.class);

        CliNome=editNome.getText().toString();
        CliEmail=editEmail.getText().toString();

        CliCpf=editCpf.getText().toString();
        CliNasc=editDataNasc.getText().toString();
        CliTel=editTel.getText().toString();
        CliSenha=editSenha.getText().toString();

        Cliente item=new Cliente(CliCpf, CliNome, CliNasc, CliSenha, CliEmail, CliTel);

        Call<Cliente> call= restService.addCliente(item);
        call.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                if(response.isSuccessful()){
                    //abre login
                    TelaLogin();
                    Toast.makeText(getApplicationContext(),"User criado", Toast.LENGTH_SHORT).show();
                }

                Log.i("Deu certo:", String.valueOf(response.code()));
            }

            @Override
            public void onFailure(Call<Cliente> call, Throwable t) {
                Log.i("Ocorreu um erro ao tentar comprar. Erro:", t.getMessage());
            }
        });
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

}