package com.example.tccappgameswave;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.FileInputStream;
import java.io.IOException;

public class esqueceuAsenha extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esqueceu_asenha);

        ImageView imgVoltar = (ImageView) findViewById(R.id.imageView);
        imgVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TelaLogin();
            }
        });

        readDataCpf();
    }

    public  void TelaLogin(){
        Intent Login = new Intent(getApplicationContext(), login.class);
        startActivity(Login);
    }
    //ler cpf da memoria
    private void readDataCpf() {
        try {
            FileInputStream fin = openFileInput("CodUser.txt");
            int a;
            //constroi a string letra por letra
            StringBuilder temp = new StringBuilder();
            while ((a = fin.read()) != -1)
            {
                temp.append((char)a);
            }

            String cpf=temp.toString();
            Log.d("CodUser",cpf);
            fin.close();//fecha busca
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}