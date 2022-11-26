package com.example.tccappgameswave;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tccappgameswave.Models.Cliente;
import com.example.tccappgameswave.Models.Venda;

import java.io.FileInputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TelaRecibo extends AppCompatActivity {

    Venda venda;
    public Retrofit retrofitVenda, retrofitrecibo ;

    String sCpf;
    String LinkApi;
    public String FormPagSelect;

    TextView txtNomeUser,txtCpfUser,txtMeioPag,txtParcelas, txtTotal;

    ImageView checkImg;
    ConstraintLayout TelaToda;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_recibo);

        Intent intent = getIntent();
        FormPagSelect =intent.getStringExtra("FormPagSelect");

        txtNomeUser=(TextView)findViewById(R.id.txtNomeUser);
        txtCpfUser=(TextView)findViewById(R.id.txtCpfUser);
        txtMeioPag=(TextView)findViewById(R.id.txtMeioPag);
        txtParcelas=(TextView)findViewById(R.id.txtParcelas);
        txtTotal=(TextView)findViewById(R.id.txtTotal);

        checkImg =(ImageView) findViewById(R.id.checkImg);
        checkImg.setVisibility(View.VISIBLE);

        TelaToda =(ConstraintLayout) findViewById(R.id.TelaToda);
        TelaToda.setVisibility(View.GONE);

        readDataLinkApi();
        readDataCpf();

        //efetua venda
        retrofitVenda = new Retrofit.Builder()
                .baseUrl(LinkApi+"Venda/")                          //endere-ço do webservice
                .addConverterFactory(GsonConverterFactory.create()) //conversor
                .build();

        //Recibo
        retrofitrecibo = new Retrofit.Builder()
                .baseUrl(LinkApi+"Venda/")                          //endere-ço do webservice
                .addConverterFactory(GsonConverterFactory.create()) //conversor
                .build();

        FazVenda(Venda());

        //volta
        ImageView btnVoltarHome = (ImageView) findViewById(R.id.imageViewVoltarHome);
        btnVoltarHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent TelaHome = new Intent(getApplicationContext(), Home.class);
                startActivity(TelaHome);
            }
        });
    }

    private void MostraRecibo() {
        //pesquisa
        RESTService restService = retrofitrecibo.create(RESTService.class);
        Call<Venda> call= restService.Recibo(sCpf);
        call.enqueue(new Callback<Venda>() {
            @Override
            public void onResponse(Call<Venda> call, Response<Venda> response) {
                if (response.isSuccessful()) {
                    venda=response.body();

                    BancoDeDados db=new BancoDeDados(getApplicationContext());
                    Cliente cli= db.selecionaCliente(sCpf);

                    if(db.selecionaCliente(sCpf)==null){
                        Log.i("Nome", "Não deu");
                    }
                    else{
                        Log.i("Nome", "Nome: " + cli.getNomeCliente());
                    }


                    String Cpf=String.valueOf(venda.getClinte_CPF());
                    String FormaPag=String.valueOf(venda.getFormaPag());
                    String Parcelas=String.valueOf(venda.getParcela());


                    Log.d("Forma de pagamento",FormaPag);
                    txtNomeUser.setText("Nome: "+cli.getNomeCliente());
                    txtCpfUser.setText("CPF: "+Cpf);
                    txtMeioPag.setText(FormaPag);
                    txtParcelas.setText(Parcelas);

                    String Totalcompra=String.valueOf(venda.getTotal());
                    String penultimaChar= String.valueOf(Totalcompra.charAt(Totalcompra.length() - 2));
                    if(penultimaChar.equals(".")){
                        txtTotal.setText("R$"+Totalcompra+"0");
                    }
                    else
                        txtTotal.setText("R$"+Totalcompra);

                    checkImg =(ImageView) findViewById(R.id.checkImg);
                    checkImg.setVisibility(View.GONE);

                    TelaToda =(ConstraintLayout) findViewById(R.id.TelaToda);
                    TelaToda.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Venda> call, Throwable t) {
                Log.i("Ocorreu um erro ao tentar concluiur a conmpra:", t.getMessage());
            }
        });
    }

    public Venda Venda(){
        Venda venda=new Venda();
        venda.setFormaPag(FormPagSelect);
        venda.setParcela(1);
        venda.setClinte_CPF(sCpf);
        return venda;
    }

    private void FazVenda(Venda venda){

        RESTService restService= retrofitVenda.create(RESTService.class);
        Call<Void> call= restService.EfetuaCompra(venda);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    //abre tela de lista de carrinho
                    MostraRecibo();
                    Log.i("Deu certo:", "Abriu recibo");
                }
                Log.i("Deu certo:", String.valueOf(response.code()));
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i("Ocorreu um erro ao tentar comprar. Erro:", t.getMessage());
            }
        });
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

    //ler cpf da memoria
    private void readDataCpf() {
        try {
            FileInputStream fin = getApplicationContext().openFileInput("CodUser.txt");
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