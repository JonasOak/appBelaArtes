package com.example.belaartes.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.belaartes.R;
import com.example.belaartes.data.model.entities.Produto;

import java.util.List;

// ProdutoAdapter.java
// ProdutoAdapter.java
public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.ProdutoViewHolder> {

    private List<Produto> produtos;
    private Context context;
    private OnProdutoClickListener listener;

    public interface OnProdutoClickListener {
        void onProdutoClick(Produto produto);
    }

    public ProdutoAdapter(List<Produto> produtos, Context context, OnProdutoClickListener listener) {
        this.produtos = produtos;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProdutoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_produto_cliente, parent, false);
        return new ProdutoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoViewHolder holder, int position) {
        Produto produto = produtos.get(position);

        holder.nome.setText(produto.getNome());
        holder.preco.setText(String.format("R$ %.2f", produto.getPreco()));

        // Carregar imagem com Glide (recomendado)
        Glide.with(context)
                .load(produto.getImagem())
                .placeholder(R.drawable.placeholder_produto)
                .into(holder.imagem);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onProdutoClick(produto);
            }
        });
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

    static class ProdutoViewHolder extends RecyclerView.ViewHolder {
        ImageView imagem;
        TextView nome, preco;

        public ProdutoViewHolder(@NonNull View itemView) {
            super(itemView);
            imagem = itemView.findViewById(R.id.imgProduto);
            nome = itemView.findViewById(R.id.tvNome);
            preco = itemView.findViewById(R.id.tvPreco);
        }
    }
}