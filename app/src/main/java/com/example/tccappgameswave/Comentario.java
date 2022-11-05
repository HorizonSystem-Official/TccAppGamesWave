package com.example.tccappgameswave;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comentario {

    @SerializedName("TxtComentario")
    @Expose
    public String TxtComentario;

    @SerializedName("NomeCliente")
    @Expose
    public String NomeCliente;

    public String getTxtComentario() {
        return TxtComentario;
    }

    public void setTxtComentario(String txtComentario) {
        TxtComentario = txtComentario;
    }

    public String getNomeCliente() {
        return NomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        NomeCliente = nomeCliente;
    }
}
