package com.example.tccappgameswave;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Produto {

    @SerializedName("CodProd")
    @Expose
    public Integer CodProd;

    @SerializedName("ProdNome")
    @Expose
    public String ProdNome;

    @SerializedName("ProdTipo")
    @Expose
    public String ProdTipo;

    @SerializedName("ProdQtnEstoque")
    @Expose
    public Integer ProdQtnEstoque;

    @SerializedName("ProdDesc")
    @Expose
    public String ProdDesc;

    @SerializedName("ProdAnoLanc")
    @Expose
    public String ProdAnoLanc;

    @SerializedName("ProdFaixaEtaria")
    @Expose
    public String ProdFaixaEtaria;

    @SerializedName("ProdValor")
    @Expose
    public Double ProdValor;

    @SerializedName("ImgCapa")
    @Expose
    public String ImgCapa;

    @SerializedName("FkFunc")
    @Expose
    public Integer FkFunc;

    public Produto(String prodNome, Double prodValor, String imgCapa) {
        this.ProdNome=prodNome;
        this.ProdValor=prodValor;
        this.ImgCapa=imgCapa;
    }

    public Integer getCodProd() {
        return CodProd;
    }

    public void setCodProd(Integer codProd) {
        CodProd = codProd;
    }

    public String getProdNome() {
        return ProdNome;
    }

    public void setProdNome(String prodNome) {
        ProdNome = prodNome;
    }

    public String getProdTipo() {
        return ProdTipo;
    }

    public void setProdTipo(String prodTipo) {
        ProdTipo = prodTipo;
    }

    public Integer getProdQtnEstoque() {
        return ProdQtnEstoque;
    }

    public void setProdQtnEstoque(Integer prodQtnEstoque) {
        ProdQtnEstoque = prodQtnEstoque;
    }

    public String getProdDesc() {
        return ProdDesc;
    }

    public void setProdDesc(String prodDesc) {
        ProdDesc = prodDesc;
    }

    public String getProdAnoLanc() {
        return ProdAnoLanc;
    }

    public void setProdAnoLanc(String prodAnoLanc) {
        ProdAnoLanc = prodAnoLanc;
    }

    public String getProdFaixaEtaria() {
        return ProdFaixaEtaria;
    }

    public void setProdFaixaEtaria(String prodFaixaEtaria) {
        ProdFaixaEtaria = prodFaixaEtaria;
    }

    public Double getProdValor() {
        return ProdValor;
    }

    public void setProdValor(Double prodValor) {
        ProdValor = prodValor;
    }

    public String getImgCapa() {
        return ImgCapa;
    }

    public void setImgCapa(String imgCapa) {
        ImgCapa = imgCapa;
    }

    public Integer getFkFunc() {
        return FkFunc;
    }

    public void setFkFunc(Integer fkFunc) {
        FkFunc = fkFunc;
    }

    @Override
    public String toString() {
        return "CodProd: " + getCodProd() +
                "\n ProdNome: " + getProdNome()+
                "\n ProdTipo: " + getProdTipo()+
                "\n ProdValor: " + getProdValor()+
                "\n ImgCapa: " + getImgCapa()+"\n";
    }
}
