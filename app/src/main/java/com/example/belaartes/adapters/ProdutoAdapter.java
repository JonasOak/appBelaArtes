package com.example.belaartes.adapters;

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

//public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.ProdutoViewHolder> {
//
//    private List<Produto> listaProdutos;
//
//    public ProdutoAdapter(List<Produto> listaProdutos) {
//        this.listaProdutos = listaProdutos;
//    }
//
//    @NonNull
//    @Override
//    public ProdutoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.item_produto, parent, false);
//        return new ProdutoViewHolder(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ProdutoViewHolder holder, int position) {
//        Produto produto = listaProdutos.get(position);
//
//        holder.nome.setText(produto.getNome());
//        holder.descricao.setText(produto.getDescricao());
//        holder.preco.setText("R$ " + produto.getPreco().toString());
//        holder.categoria.setText(produto.getCategoria());
//        holder.estoque.setText("Estoque: " + produto.getEstoque());
//
//        // Se estiver usando imagens locais na drawable
//        // int idImagem = holder.itemView.getContext().getResources().getIdentifier(produto.getImagem(), "drawable", holder.itemView.getContext().getPackageName());
//        // holder.imagem.setImageResource(idImagem);
//
//        // Caso esteja carregando de uma URL (depois podemos usar Glide ou Picasso)
//    }
//
//    @Override
//    public int getItemCount() {
//        return listaProdutos.size();
//    }
//
//    public static class ProdutoViewHolder extends RecyclerView.ViewHolder {
//        TextView nome, descricao, preco, categoria, estoque;
//        ImageView imagem;
//
//        public ProdutoViewHolder(@NonNull View itemView) {
//            super(itemView);
//            nome = itemView.findViewById(R.id.textNome);
//            descricao = itemView.findViewById(R.id.textDescricao);
//            preco = itemView.findViewById(R.id.textPreco);
//            categoria = itemView.findViewById(R.id.textCategoria);
//            estoque = itemView.findViewById(R.id.textEstoque);
//            imagem = itemView.findViewById(R.id.imgProduto);
//        }
//    }
//}