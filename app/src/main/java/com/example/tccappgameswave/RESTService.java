package com.example.tccappgameswave;

import com.example.tccappgameswave.Models.Carrinho;
import com.example.tccappgameswave.Models.Cliente;
import com.example.tccappgameswave.Models.Comentario;
import com.example.tccappgameswave.Models.ItemCarrinho;
import com.example.tccappgameswave.Models.Produto;
import com.example.tccappgameswave.Models.Venda;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RESTService {

    //*********************//
    //      Produto       //
    //*********************//
    @GET("prodCategoria?")
    Call<List<Produto>> MostraProdPorCat(@Query("cat") String cat);

    @GET("ProdDetalhado?")
    Call<Produto> MostraProdDetalhes(@Query("idProd") int codProd);

    @GET("PesquisaProd?")
    Call<List<Produto>>  PesquisaProduto(@Query("txtPesquisa") String txtPesquisa);

    //comentarios
    @GET("ComentariosProd?")
    Call<List<Comentario>> ListComentarios(@Query("idProd") int idProd);

    //*********************//
    //      Carrinho       //
    //*********************//

    //add item ao carrinho
    @POST("addItemCarrinho")
    Call<Void> AddItensCarrinho(@Body ItemCarrinho itemCarrinho);

    //mostra itens do carrinho
    @GET("ItensCarrinho?")
    Call<List<ItemCarrinho>> ItensCarrinho(@Query("cpf") String cpf);

    //valor total carrinho
    @GET("TotalCarrinho?")
    Call<Carrinho> valorTotalCarrrinho(@Query("cpf") String cpf);

    //Remove item
    @DELETE("removeItem?")
    Call<Void> RemoveItensCarrinho(@Query("codProd") int codProd, @Query("cpf") String cpf);


    //*********************//
    //      Cliente       //
    //*********************//
    @GET("LoginCliente?")
    Call<Cliente> LoginCliente(@Query("Emailcli") String email, @Query("senhaCli") String senha);

    @GET("DadosCli?")
    Call<Cliente> DetalhesCliente(@Query("CpfCli") String cpf);

    @POST("addCliente")
    Call<Void> addCliente(@Body Cliente cliente);

    //*********************//
    //      Venda         //
    //*********************//
    @GET("Recibo?")
    Call<Venda> Recibo(@Query("cpf") String cpf);

    @POST("EfetuaCompra")
    Call<Void> EfetuaCompra(@Body Venda venda);
}
