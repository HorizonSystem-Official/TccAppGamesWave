package com.example.tccappgameswave;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public abstract class AdapterListItensRecycler extends RecyclerView.Adapter<AdapterListItensRecycler.ItemViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;

    Context context;
    List<ItemCarrinho> ItemCarrinhoList;


    protected AdapterListItensRecycler(Context context, List<ItemCarrinho> itemCarrinhoList, RecyclerViewInterface recyclerViewInterface) {
        this.context=context;
        this.ItemCarrinhoList=itemCarrinhoList;
        this.recyclerViewInterface=recyclerViewInterface;
    }

    public void setMovieList(List<ItemCarrinho> itemCarrinhoList) {
        this.ItemCarrinhoList = itemCarrinhoList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterListItensRecycler.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carrinho,parent,false);
        return new ItemViewHolder(view,recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListItensRecycler.ItemViewHolder holder, int position) {
//define da onde vem os valores
//        Picasso.get()
//                .load(ItemCarrinhoList.get(position).g())
//                .placeholder(R.mipmap.ic_launcher_round)
//                .error(R.mipmap.ic_launcher_round)
//                .into(holder.imgProdItem);

        holder.textNomeProdItem.setText(ItemCarrinhoList.get(position).getProdNome());
        holder.textQtn.setText("Quantidade: "+ItemCarrinhoList.get(position).getQtnProd());
        holder.textTotal.setText(ItemCarrinhoList.get(position).getValorTotal().toString());
    }

    @Override
    public int getItemCount() {
        return ItemCarrinhoList == null ? 0 : ItemCarrinhoList.size();
    }

    //abre os detalhes do produto
    public abstract void onItemClick(int position);

    //define os campo com o layout
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProdItem;
        TextView textNomeProdItem;
        TextView textQtn;
        TextView textTotal;

        public ItemViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            imgProdItem = itemView.findViewById(R.id.imageViewProdItem);
            textNomeProdItem = itemView.findViewById(R.id.textViewNomeProdItem);
            textQtn = itemView.findViewById(R.id.textViewQtn);
            textTotal = itemView.findViewById(R.id.textViewTotal);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface!=null){
                        int pos=getAdapterPosition();

                        if(pos!=RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }

                }
            });
        }
    }
}
