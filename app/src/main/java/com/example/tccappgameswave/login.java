package com.example.tccappgameswave;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class login extends AppCompatActivity {

    String LinkApi="https://funshinybook65.conveyor.cloud/api/";

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
        MudaSenha.putExtra("LinkApi",LinkApi);
        startActivity(MudaSenha);
    }

    public  void TelaCriaConta(){
        Intent CriaConta = new Intent(getApplicationContext(),cadastroCliente.class);
        CriaConta.putExtra("LinkApi",LinkApi);
        startActivity(CriaConta);
    }

    public  void TelaHome(){
        Intent Home = new Intent(getApplicationContext(),Home.class);
        Home.putExtra("LinkApi",LinkApi);
        startActivity(Home);
    }
}