package com.example.belaartes.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.belaartes.R;
import com.example.belaartes.data.model.entities.Produto;
import com.example.belaartes.ui.cliente.ProdutoDetalheActivity;

import java.util.List;

public class ProdutoCarouselAdapter extends RecyclerView.Adapter<ProdutoCarouselAdapter.ProdutoViewHolder> {

    private final List<Produto> produtos;
    private final Context context;

    public ProdutoCarouselAdapter(Context context, List<Produto> produtos) {
        this.context = context;
        this.produtos = produtos;
    }

    @NonNull
    @Override
    public ProdutoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_carrossel_produto, parent, false);
        return new ProdutoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoViewHolder holder, int position) {
        Produto produto = produtos.get(position);

        String imagemBase64 = produto.getImagem();
        if (imagemBase64 != null && !imagemBase64.isEmpty()) {
            byte[] imagemBytes = Base64.decode(imagemBase64, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imagemBytes, 0, imagemBytes.length);
            holder.imgProduto.setImageBitmap(bitmap);
        } else {
            holder.imgProduto.setImageResource(R.drawable.ic_launcher_foreground);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProdutoDetalheActivity.class);
            intent.putExtra("produtoId", produto.getIdProduto());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

    public static class ProdutoViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduto;

        public ProdutoViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduto = itemView.findViewById(R.id.imgProdutoCarrossel);
        }
    }

}
