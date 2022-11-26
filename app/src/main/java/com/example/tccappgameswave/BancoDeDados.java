package com.example.tccappgameswave;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tccappgameswave.Models.Cliente;


public class BancoDeDados extends SQLiteOpenHelper {


    //vars Tb Cliente
    public static final String Tabela_Cliente = "TbCliente";
    public static final String Coluna_CPFCli = "CPFCli";
    public static final String Coluna_NomeCli = "NomeCli";
    public static final String Coluna_DateNascCli= "DateNascCli";
    public static final String Coluna_EmailCli = "EmailCli";
    public static final String Coluna_TelCli = "TelCli";

    //nome do banco e versão
    private static final String DATABASE_Nome = "BdGamesWaveCliente.db";
    private static final int DATABASE_VERSION = 1;

    public BancoDeDados(Context context) {
        super(context, DATABASE_Nome, null, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //cria tb cliente
        String Criacao_tabela = "create table " + Tabela_Cliente + "( "
                + Coluna_CPFCli + " text primary key, "
                + Coluna_NomeCli + " text not null,"
                + Coluna_DateNascCli + " Date not null,"
                + Coluna_TelCli + " text not null,"
                + Coluna_EmailCli + " text not null);";

        db.execSQL(Criacao_tabela);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //Insert cliente
    void addCli(Cliente cliente){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values= new ContentValues();
        values.put(Coluna_CPFCli, cliente.getCPF());
        values.put(Coluna_NomeCli, cliente.getNomeCliente());
        values.put(Coluna_DateNascCli, cliente.getDataNasc());
        values.put(Coluna_TelCli, cliente.getTelCli());
        values.put(Coluna_EmailCli, cliente.getEmailCli());

        db.insert(Tabela_Cliente, null, values);
        db.close();
    }

    //delete
    void ApagaCli(Cliente cliente){
        SQLiteDatabase db=this.getWritableDatabase();

        db.delete(Tabela_Cliente, Coluna_CPFCli+"= ?", new String[]{String.valueOf(cliente.getCPF())});
        db.close();
    }

    //select
    Cliente selecionaCliente(String cpf){
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=db.query(Tabela_Cliente,
                new String[]{Coluna_CPFCli, Coluna_NomeCli, Coluna_DateNascCli, Coluna_TelCli, Coluna_EmailCli},
                Coluna_CPFCli+"=?",new String[]{cpf},null, null, null,null);
        if(cursor!=null &&  cursor.getCount()>0){
            cursor.moveToFirst();
        }

        Cliente cli= new Cliente(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                cursor.getString(3), cursor.getString(4));

        return cli;
    }

    //conta users
    public int numeroDeUsers(String cpf) {
        SQLiteDatabase db = this.getReadableDatabase();

        int numUsers = (int) DatabaseUtils.queryNumEntries(db, Tabela_Cliente,Coluna_CPFCli+"=?" ,new String[]{cpf});
        return numUsers;
    }

}
