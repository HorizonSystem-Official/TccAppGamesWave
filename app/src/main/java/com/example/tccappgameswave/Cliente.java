package com.example.tccappgameswave;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Cliente {

    @SerializedName("CPF")
    @Expose
    public String CPF;

    @SerializedName("NomeCliente")
    @Expose
    public String NomeCliente;

    @SerializedName("DataNasc")
    @Expose
    public Date DataNasc ;

    @SerializedName("Senha")
    @Expose
    public String Senha;

    @SerializedName("EmailCli")
    @Expose
    public String EmailCli;

    @SerializedName("TelCli")
    @Expose
    public String TelCli ;

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getNomeCliente() {
        return NomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        NomeCliente = nomeCliente;
    }

    public Date getDataNasc() {
        return DataNasc;
    }

    public void setDataNasc(Date dataNasc) {
        DataNasc = dataNasc;
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String senha) {
        Senha = senha;
    }

    public String getEmailCli() {
        return EmailCli;
    }

    public void setEmailCli(String emailCli) {
        EmailCli = emailCli;
    }

    public String getTelCli() {
        return TelCli;
    }

    public void setTelCli(String telCli) {
        TelCli = telCli;
    }
}
