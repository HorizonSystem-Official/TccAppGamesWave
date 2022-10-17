package com.example.tccappgameswave;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView txtEsqueceu = (TextView) findViewById(R.id.txtEsqueceu);
        txtEsqueceu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TelaMudaSenha();
            }
        });

        TextView TxtCriarConta = (TextView) findViewById(R.id.TxtCriarConta);
        TxtCriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TelaCriaConta();
            }
        });

        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TelaHome();
            }
        });
    }

    public  void TelaMudaSenha(){
        Intent MudaSenha = new Intent(getApplicationContext(), esqueceuAsenha.class);
        startActivity(MudaSenha);
    }

    public  void TelaCriaConta(){
        Intent CriaConta = new Intent(getApplicationContext(),cadastroCliente.class);
        startActivity(CriaConta);
    }

    public  void TelaHome(){
        Intent Home = new Intent(getApplicationContext(),Home.class);
        startActivity(Home);
    }
}