package com.example.belaartes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.belaartes.R;
import com.example.belaartes.data.model.entities.Produto;

import java.util.List;

public class ProdutoAdminAdapter extends RecyclerView.Adapter<ProdutoAdminAdapter.ProdutoAdminViewHolder> {

    private List<Produto> produtos;
    private Context context;
    private OnAdminActionListener listener;

    public interface OnAdminActionListener {
        void onEditarClick(Produto produto);
        void onExcluirClick(Produto produto);
    }

    public ProdutoAdminAdapter(List<Produto> produtos, Context context, OnAdminActionListener listener) {
        this.produtos = produtos;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProdutoAdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_produto_admin, parent, false);
        return new ProdutoAdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoAdminViewHolder holder, int position) {
        Produto produto = produtos.get(position);

        holder.nome.setText(produto.getNome());
        holder.preco.setText(String.format("R$ %.2f", produto.getPreco()));
        holder.estoque.setText("Estoque: " + produto.getEstoque());

        Glide.with(context)
                .load(produto.getImagem())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.imagem);

        holder.btnEditar.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditarClick(produto);
            }
        });

        holder.btnExcluir.setOnClickListener(v -> {
            if (listener != null) {
                listener.onExcluirClick(produto);
            }
        });
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

    static class ProdutoAdminViewHolder extends RecyclerView.ViewHolder {
        ImageView imagem;
        TextView nome, preco, estoque;
        ImageButton btnEditar, btnExcluir;

        public ProdutoAdminViewHolder(@NonNull View itemView) {
            super(itemView);
            imagem = itemView.findViewById(R.id.imgProduto);
            nome = itemView.findViewById(R.id.tvNome);
            preco = itemView.findViewById(R.id.tvPreco);
            estoque = itemView.findViewById(R.id.tvEstoque);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnExcluir = itemView.findViewById(R.id.btnExcluir);
        }
    }

}