package com.example.belaartes.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.belaartes.R;
import com.example.belaartes.data.model.entities.ItemPedido;
import com.example.belaartes.data.model.entities.Produto;
import com.example.belaartes.ui.cliente.CarrinhoComprasActivity;

import java.util.List;

public class CarrinhoAdapter extends RecyclerView.Adapter<CarrinhoAdapter.CarrinhoViewHolder> {

    private List<ItemPedido> lista;

    /**
     * Configuração para remover item do carrinho
     */
    private OnItemRemoveListener onItemRemoveListener;

    public interface OnItemRemoveListener {
        void onItemRemoved();
    }

    public CarrinhoAdapter(List<ItemPedido> lista, OnItemRemoveListener listener) {
        this.lista = lista;
        this.onItemRemoveListener = listener;

    }


    @NonNull
    @Override
    public CarrinhoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carrinho, parent, false);
        return new CarrinhoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarrinhoViewHolder holder, int position) {
        ItemPedido item = lista.get(position);  // Mudando de ItemCarrinho para ItemPedido
        Produto produto = item.getProduto();

        holder.nomeProduto.setText(produto.getNome());
        holder.descricao.setText(produto.getDescricao());
        holder.quantidade.setText(String.valueOf(item.getQuantidade()));
        holder.preco.setText("R$ " + produto.getPreco());

        if (produto.getImagem() != null && !produto.getImagem().isEmpty()) {
            byte[] imagemBytes = Base64.decode(produto.getImagem(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imagemBytes, 0, imagemBytes.length);
            holder.imgProduto.setImageBitmap(bitmap);
        }
        // Clicar para excluir
        holder.cleaProduct.setOnClickListener(v -> {
            lista.remove(holder.getAdapterPosition());
            notifyItemRemoved(holder.getAdapterPosition());
            notifyItemRangeChanged(holder.getAdapterPosition(), lista.size());

            if (onItemRemoveListener != null) {
                onItemRemoveListener.onItemRemoved();
            }
        });
    }


    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class CarrinhoViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduto;
        TextView nomeProduto, descricao, preco, quantidade;
        ImageButton cleaProduct;

        public CarrinhoViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduto = itemView.findViewById(R.id.imgProduto);
            nomeProduto = itemView.findViewById(R.id.txtNomeProduto);
            descricao = itemView.findViewById(R.id.txtCorProduto);
            preco = itemView.findViewById(R.id.txtPrecoProduto);
            quantidade = itemView.findViewById(R.id.txtQuantidade);
            cleaProduct = itemView.findViewById(R.id.btn_list_product_Excluir);
        }


    }


}
