package com.example.tccappgameswave;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tccappgameswave.Models.Comentario;

import java.util.List;

public class AdapterComentariosRecycler extends  RecyclerView.Adapter<AdapterComentariosRecycler.ComentarioHolder> {

    Context context;
    List<Comentario> comentariosList;

    public AdapterComentariosRecycler(Context context, List<Comentario> comentariosList){
        this.context=context;
        this.comentariosList=comentariosList;
    }

    public void setMovieList(List<Comentario> comentariosList) {
        this.comentariosList = comentariosList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ComentarioHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_comentario,parent,false);
        return new AdapterComentariosRecycler.ComentarioHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComentarioHolder holder, int position) {
        holder.txtNome.setText(comentariosList.get(position).getNomeCliente());
        holder.texComentario.setText(comentariosList.get(position).TxtComentario);
    }

    @Override
    public int getItemCount() {
        return comentariosList == null ? 0 : comentariosList.size();
    }

    //define os campo com o layout
    public class ComentarioHolder extends RecyclerView.ViewHolder {
        TextView txtNome;
        TextView texComentario;

        public ComentarioHolder(@NonNull View itemView) {
            super(itemView);

            txtNome = itemView.findViewById(R.id.textViewNome);
            texComentario = itemView.findViewById(R.id.textViewComenet);

        }
    }
}
