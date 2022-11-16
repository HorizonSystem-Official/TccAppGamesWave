package com.example.tccappgameswave;

import com.example.tccappgameswave.Models.Carrinho;
import com.example.tccappgameswave.Models.Cliente;
import com.example.tccappgameswave.Models.Comentario;
import com.example.tccappgameswave.Models.ItemCarrinho;
import com.example.tccappgameswave.Models.Produto;

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

    //valor total carrinho
    @GET("TotalCarrinho?")
    Call<Carrinho> valorTotalCarrrinho(@Query("cpf") String cpf);

    //comentarios
    @GET("ComentariosProd?")
    Call<List<Comentario>> ListComentarios(@Query("idProd") int idProd);

    //*********************//
    //      Carrinho       //
    //*********************//

    //add item ao carrinho
    @POST("addItemCarrinho")
    Call<ItemCarrinho> AddItensCarrinho(@Body ItemCarrinho itemCarrinho);
    //Call<ItemCarrinho> AddItensCarrinho(@Field("QtnProd")int QtnProd, @Field("CodProd") int CodProd, @Field("Cpf") String Cpf);

    //mostra itens do carrinho
    @GET("ItensCarrinho?")
    Call<List<ItemCarrinho>> ItensCarrinho(@Query("cpf") String cpf);

    //Remove item
    @DELETE("removeItem?")
    Call<Void> RemoveItensCarrinho(@Query("codProd") int codProd, @Query("cpf") String cpf);


    //*********************//
    //      Cliente       //
    //*********************//
    @GET("LoginCliente?")
    Call<Cliente> LoginCliente(@Query("Emailcli") String email, @Query("senhaCli") String senha);

    @POST("addCliente")
    Call<Cliente> addCliente(@Body Cliente cliente);
}
