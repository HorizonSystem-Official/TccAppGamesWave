package com.example.tccappgameswave.Models;

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
    public String DataNasc ;

    @SerializedName("Senha")
    @Expose
    public String Senha;

    @SerializedName("EmailCli")
    @Expose
    public String EmailCli;

    @SerializedName("TelCli")
    @Expose
    public String TelCli ;

    //inserir no banco
    public Cliente(String vCPF, String VnomeCliente, String VdataNasc, String VemailCli, String VtelCli) {
        this.CPF = vCPF;
        this.NomeCliente = VnomeCliente;
        this.DataNasc = VdataNasc;
        this.TelCli = VtelCli;
        this.EmailCli = VemailCli;
    }

    //inserir na api
    public Cliente(String cliCpf, String cliNome, String cliNasc, String cliSenha, String cliEmail, String cliTel) {
        this.CPF = cliCpf;
        this.NomeCliente = cliNome;
        this.DataNasc = cliNasc;
        this.Senha = cliSenha;
        this.TelCli = cliEmail;
        this.EmailCli = cliTel;
    }

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

    public String getDataNasc() {
        return DataNasc;
    }

    public void setDataNasc(String dataNasc) {
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
