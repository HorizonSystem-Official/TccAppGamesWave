package com.example.tccappgameswave.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Venda {

    @SerializedName("CodVenda")
    @Expose
    public int CodVenda;

    @SerializedName("FormaPag")
    @Expose
    public String FormaPag;

    @SerializedName("Parcela")
    @Expose
    public int Parcela ;

    @SerializedName("Total")
    @Expose
    public float Total ;

    @SerializedName("CodCarrinho")
    @Expose
    public int CodCarrinho ;

    @SerializedName("Clinte_CPF")
    @Expose
    public String Clinte_CPF ;


    public int getCodVenda() {
        return CodVenda;
    }

    public void setCodVenda(int codVenda) {
        CodVenda = codVenda;
    }

    public String getFormaPag() {
        return FormaPag;
    }

    public void setFormaPag(String formaPag) {
        FormaPag = formaPag;
    }

    public int getParcela() {
        return Parcela;
    }

    public void setParcela(int parcela) {
        Parcela = parcela;
    }

    public float getTotal() {
        return Total;
    }

    public void setTotal(float total) {
        Total = total;
    }

    public int getCodCarrinho() {
        return CodCarrinho;
    }

    public void setCodCarrinho(int codCarrinho) {
        CodCarrinho = codCarrinho;
    }

    public String getClinte_CPF() {
        return Clinte_CPF;
    }

    public void setClinte_CPF(String clinte_CPF) {
        Clinte_CPF = clinte_CPF;
    }
}
