package com.example.tccappgameswave;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private String fileLinkApi = "LinkApi.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                gravaDataLinkApi();
                TelaLogin();
            }
        },2000);//2seg
    }

    public  void TelaLogin(){
        Intent MudaSenha = new Intent(getApplicationContext(), login.class);
        startActivity(MudaSenha);
    }

    //escreve na memoria
    private void gravaDataLinkApi(){
        try {
            FileOutputStream fos = openFileOutput(fileLinkApi, Context.MODE_PRIVATE);
            String dataLinkApi = "https://oldsparklycard61.conveyor.cloud/api/";
            //trnforma em byter e grava
            fos.write(dataLinkApi.getBytes());
            fos.flush();
            fos.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}