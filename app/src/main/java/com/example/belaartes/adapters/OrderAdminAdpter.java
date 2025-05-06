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
import com.example.belaartes.data.model.entities.Produto;

import java.util.List;

public class OrderAdminAdpter extends RecyclerView.Adapter<OrderAdminAdpter.OrderAdminViewHolder>{
    private List<Produto> products;
    private Context context;
    private OnAdminActionListener listener;

    private int selectPosition;
    public interface OnAdminActionListener{
        void onView(Produto product);
    }

    public OrderAdminAdpter(List<Produto> products, Context context, OnAdminActionListener listener) {
        this.products = products;
        this.context = context;
        this.listener = listener;
    }

    /**
     * Configuração do layout
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     *
     */
    @NonNull
    @Override
    public OrderAdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.item_order_admin, parent, false);
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order_admin, parent, false);
        return new OrderAdminViewHolder(view);
    }


    /**
     *  Adicionar os item em um recyclerView
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull OrderAdminViewHolder holder, int position) {
        Produto product = products.get(position);
        holder.productName.setText(product.getNome());
        holder.productPrice.setText(String.format("R$ %.2f", product.getPreco()));
        holder.productAmount.setText("Estoque: " + product.getEstoque());
        String imagemBase64 = product.getImagem();
        if (imagemBase64 != null && !imagemBase64.isEmpty()) {
            try {
                byte[] imagemBytes = Base64.decode(imagemBase64, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(imagemBytes, 0, imagemBytes.length);
                holder.orderProductImg.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
                holder.orderProductImg.setImageResource(R.drawable.ic_launcher_foreground);
            }
        } else {
            holder.orderProductImg.setImageResource(R.drawable.ic_launcher_foreground);

        }
        holder.buttonView.setOnClickListener(v ->{
            this.selectPosition = position;
            if(listener != null){
                listener.onView(product);
            }
            Toast.makeText(context, "Editando", Toast.LENGTH_LONG);
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public int getSelectPosition() {
        return selectPosition;
    }

    static class OrderAdminViewHolder extends RecyclerView.ViewHolder{
         ImageView orderProductImg;
        ImageButton buttonView;
        TextView productName, productPrice, productAmount;
        public OrderAdminViewHolder(@NonNull View itemView) {
            super(itemView);
            initializeUi();

        }

        private void initializeUi(){
            this.orderProductImg = itemView.findViewById(R.id.order_img);
            this.buttonView = itemView.findViewById(R.id.order_view);
            this.productName = itemView.findViewById(R.id.order_name);
            this.productPrice = itemView.findViewById(R.id.order_price);
            this.productAmount = itemView.findViewById(R.id.order_amount);
        }
    }
}
