package com.example.belaartes.ui.cliente;

import static com.example.belaartes.data.session.CheckoutSession.listCart;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.belaartes.R;
import com.example.belaartes.adapters.CarrinhoAdapter;
import com.example.belaartes.data.model.entities.ItemPedido;
import com.example.belaartes.data.model.entities.Produto;
import com.example.belaartes.data.repository.ProdutoRepository;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CarrinhoComprasActivity extends AppCompatActivity {
    private ImageView imgProduct;
    private TextView productName, productDescription, productAmount, productMoney, productSubTotal;
    private Button sendProof;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_carrinho_compras);

        RecyclerView recyclerCarrinho = findViewById(R.id.recyclerCarrinho);
        recyclerCarrinho.setLayoutManager(new LinearLayoutManager(this));
        CarrinhoAdapter adapter = new CarrinhoAdapter(listCart);

        recyclerCarrinho.setAdapter(adapter);
        initializeUI();
        setupListeners();
        calculateAllProduct();

    }

    private void setupListeners(){
        sendProof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numeroTelefone = "5571986917919";
                String mensagem = "Olá, meu nome é ";

                try {
                    // Codifica a mensagem para URL
                    String mensagemCodificada = URLEncoder.encode(mensagem, "UTF-8");
                    // Monta o link para o WhatsApp
                    String url = "https://wa.me/" + numeroTelefone + "?text=" + mensagemCodificada;
                    // Cria o intent
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(CarrinhoComprasActivity.this, "Erro ao abrir o WhatsApp", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void initializeUI() {
        this.productSubTotal = findViewById(R.id.txtSubtotal);
    }

    private void calculateAllProduct(){
        BigDecimal subCalculate = BigDecimal.ZERO;
        for(ItemPedido requestCart : listCart){
            subCalculate = subCalculate.add(requestCart.getProduto().getPreco());
        }
        productSubTotal.setText(String.valueOf(subCalculate));
    }


}