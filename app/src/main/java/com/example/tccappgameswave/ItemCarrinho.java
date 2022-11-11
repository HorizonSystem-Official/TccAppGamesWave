package com.example.tccappgameswave;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemCarrinho {

    @SerializedName("ProdNome")
    @Expose
    public String ProdNome;

    @SerializedName("QtnProd")
    @Expose
    public int QtnProd;

    @SerializedName("CodProd")
    @Expose
    public int CodProd;

    @SerializedName("ValorUnit")
    @Expose
    public String ValorUnit;

    @SerializedName("Cpf")
    @Expose
    public String Cpf;

    @SerializedName("ValorTotal")
    @Expose
    public String ValorTotal;

    @SerializedName("ImgCapa")
    @Expose
    public String ImgCapa;

    public ItemCarrinho( int qtnProd, int codProd, String cpf) {

    }


    public String getProdNome() {
        return ProdNome;
    }

    public void setProdNome(String prodNome) {
        ProdNome = prodNome;
    }

    public int getQtnProd() {
        return QtnProd;
    }

    public void setQtnProd(int qtnProd) {
        QtnProd = qtnProd;
    }

    public int getCodProd() {
        return CodProd;
    }

    public void setCodProd(int codProd) {
        CodProd = codProd;
    }

    public String getValorUnit() {
        return ValorUnit;
    }

    public void setValorUnit(String valorUnit) {
        ValorUnit = valorUnit;
    }

    public String getCpf() {
        return Cpf;
    }

    public void setCpf(String cpf) {
        Cpf = cpf;
    }

    public String getValorTotal() {
        return ValorTotal;
    }

    public void setValorTotal(String valorTotal) {
        ValorTotal = valorTotal;
    }

    public String getImgCapa() {
        return ImgCapa;
    }

    public void setImgCapa(String imgCapa) {
        ImgCapa = imgCapa;
    }
}
