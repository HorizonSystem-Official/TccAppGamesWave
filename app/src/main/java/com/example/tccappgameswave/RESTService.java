package com.example.tccappgameswave;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RESTService {
    @GET("prodCategoria?")
    Call<List<Produto>> MostraProdPorCat(@Query("cat") String cat);

    @GET("umProdSimples?")
    Call<List<Produto>> MostraProdDetalhes(@Query("idProd") int codProd);
}
