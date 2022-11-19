package com.example.tccappgameswave;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tccappgameswave.Models.Cliente;

import java.io.FileInputStream;
import java.io.IOException;

public class UserFragment extends Fragment {

    String sCpf;
    TextView txtViewNameCli,txtViewEmailCli;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        readDataCpf();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_user, container, false);

        txtViewNameCli=(TextView)view.findViewById(R.id.textViewNome);
        txtViewEmailCli=(TextView)view.findViewById(R.id.textViewEmail);

        BancoDeDados db=new BancoDeDados(getActivity());
        Cliente cli= db.selecionaCliente(sCpf);

        if(db.selecionaCliente(sCpf)==null){
            Log.i("Nome", "Não deu");
        }
        else{
            Log.i("Nome", "Nome: " + cli.getNomeCliente());
        }

        txtViewNameCli.setText("Nome: " + cli.getNomeCliente());
        txtViewEmailCli.setText("Email: " + cli.getEmailCli());

        return view;
    }

    //ler cpf da memoria
    private void readDataCpf() {
        try {
            FileInputStream fin = getActivity().openFileInput("CodUser.txt");
            int a;
            //constroi a string letra por letra
            StringBuilder temp = new StringBuilder();
            while ((a = fin.read()) != -1)
            {
                temp.append((char)a);
            }

            sCpf=temp.toString();
            fin.close();//fecha busca
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}