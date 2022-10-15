package com.example.tccappgameswave;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                TelaLogin();
            }
        },2000);//2seg
    }

    public  void TelaLogin(){
        Intent MudaSenha = new Intent(getApplicationContext(), login.class);
        startActivity(MudaSenha);
    }
}