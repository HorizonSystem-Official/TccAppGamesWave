package com.example.tccappgameswave;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

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
    }

    public  void TelaLogin(){
        Intent Login = new Intent(getApplicationContext(), login.class);
        startActivity(Login);
    }
}