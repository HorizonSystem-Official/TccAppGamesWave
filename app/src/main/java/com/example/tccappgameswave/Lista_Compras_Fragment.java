package com.example.tccappgameswave;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.tccappgameswave.Models.Carrinho;
import com.example.tccappgameswave.Models.ItemCarrinho;

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

    TextView txtViewTotal, textViewCarrinhoVazio;
    ImageView imgCarrinhoVazio;
    String FormPagSelect;
    ProgressBar progressBar;
    ConstraintLayout TelaToda;

    Button btnPagar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ItemCarrinhoList = new ArrayList<>();
        //de dados
        readDataLinkApi();
        readDataCpf();

        //carrinho
        retrofitItensCarrinho = new Retrofit.Builder()
                .baseUrl(LinkApi+"Carrinho/")                                       //endere-ço do webservice
                .addConverterFactory(GsonConverterFactory.create()) //conversor
                .build();

        //lista os itens
        MostraItemCarrinho();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_lista__compras_, container, false);

        txtViewTotal=view.findViewById(R.id.textViewTotal);

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

        //se carrinho vazio deixa abri home
        imgCarrinhoVazio =view.findViewById(R.id.imageViewCarrinhoVazio);
        imgCarrinhoVazio.setOnClickListener(viewList ->Home());

        //se carrinho vazio deixa abri home
        textViewCarrinhoVazio =view.findViewById(R.id.textViewCarrinhoVazio);
        textViewCarrinhoVazio.setOnClickListener(viewList -> Home());

        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        TelaToda =view.findViewById(R.id.TelaToda);
        TelaToda.setVisibility(View.GONE);

        //abre recibo
        btnPagar=(Button) view.findViewById(R.id.btnPagar);
        btnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialog();
            }
        });
        return view;
    }

    private  void ShowDialog(){
            //array de formas de pagamento
            String [] FormPag={"Cartão", "Pix", "Boleto"};
            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
            alert.setTitle("Selecione o metodo de pagamento");
            alert.setSingleChoiceItems(FormPag, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int whitch) {
                    FormPagSelect= FormPag[whitch];
                }
            });

            alert.setPositiveButton("Confirmar Compra", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent AbreRceibo = new Intent(getContext(),TelaRecibo.class);
                    AbreRceibo.putExtra("FormPagSelect",FormPagSelect);
                    startActivity(AbreRceibo);
                }
            });

            alert.setNegativeButton("Sair", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            alert.show();
    }

    public  void Home(){
        Intent TelaHome = new Intent(getContext(), Home.class);
        startActivity(TelaHome);
    }

    //mostra o carrinho
    private void MostraItemCarrinho() {
        RESTService restService = retrofitItensCarrinho.create(RESTService.class);
        Call<List<ItemCarrinho>> call= restService.ItensCarrinho(sCpf);
        //executa e mostra a requisisao
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<ItemCarrinho>> call, Response<List<ItemCarrinho>> response) {
                if (response.isSuccessful()) {
                    ItemCarrinhoList = response.body();
                    adapter.setMovieList(ItemCarrinhoList);

                    //se o carrinho estiver vazio
                    if(ItemCarrinhoList.isEmpty()){
                        btnPagar.setEnabled(false);
                        txtViewTotal.setText("R$ 00.00");
                        imgCarrinhoVazio.setVisibility(View.VISIBLE);
                        textViewCarrinhoVazio.setVisibility(View.VISIBLE);
                    }
                    else
                        MostraTotalCarrinho();

                    progressBar.setVisibility(View.GONE);
                    TelaToda.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<ItemCarrinho>> call, Throwable t) {
                Log.i("Ocorreu um erro ao tentar consultar o Perfil. Erro:", t.getMessage());
            }
        });
    }

    //valor total
    private void MostraTotalCarrinho() {
        RESTService restService = retrofitItensCarrinho.create(RESTService.class);
        Call<Carrinho> call= restService.valorTotalCarrrinho(sCpf);
        call.enqueue(new Callback<>() {
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