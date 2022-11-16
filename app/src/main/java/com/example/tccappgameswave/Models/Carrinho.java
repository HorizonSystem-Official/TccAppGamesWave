package com.example.tccappgameswave.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Carrinho {

    @SerializedName("CodCarrinho")
    @Expose
    public int CodCarrinho;

    @SerializedName("ValorTotal")
    @Expose
    public double ValorTotal;

    @SerializedName("Cupom")
    @Expose
    public String Cupom;

    public int getCodCarrinho() {
        return CodCarrinho;
    }

    public void setCodCarrinho(int codCarrinho) {
        CodCarrinho = codCarrinho;
    }

    public double getValorTotal() {
        return ValorTotal;
    }

    public void setValorTotal(double valorTotal) {
        ValorTotal = valorTotal;
    }

    public String getCupom() {
        return Cupom;
    }

    public void setCupom(String cupom) {
        Cupom = cupom;
    }

}
