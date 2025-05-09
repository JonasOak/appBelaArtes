package com.example.belaartes.adapters;

import android.content.Context;
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
                .inflate(R.layout.activity_catalogo_produtos, parent, false);
        return new ProdutoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoViewHolder holder, int position) {
        Produto produto = produtos.get(position);

        holder.nome.setText(produto.getNome());
        holder.preco.setText(String.format("R$ %.2f", produto.getPreco()));
        holder.descricao.setText(produto.getDescricao());
        holder.categoria.setText(produto.getCategoria());
        holder.estoque.setText("Estoque: " + produto.getEstoque());


        String imagemBase64 = produto.getImagem();
        if (imagemBase64 != null && !imagemBase64.isEmpty()) {
            try {
                byte[] imagemBytes = Base64.decode(imagemBase64, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(imagemBytes, 0, imagemBytes.length);
                holder.imagem.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
                holder.imagem.setImageResource(R.drawable.ic_launcher_foreground);
            }
        } else {
            holder.imagem.setImageResource(R.drawable.ic_launcher_foreground);
        }

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
        TextView nome, preco, descricao, categoria, estoque;

        public ProdutoViewHolder(@NonNull View itemView) {
            super(itemView);
            imagem = itemView.findViewById(R.id.imgProduto);
            nome = itemView.findViewById(R.id.tvNome);
            descricao = itemView.findViewById(R.id.tvDescricao);
            categoria = itemView.findViewById(R.id.tvCategoria);
            estoque = itemView.findViewById(R.id.tvEstoque);
            preco = itemView.findViewById(R.id.tvPreco);
        }
    }
}