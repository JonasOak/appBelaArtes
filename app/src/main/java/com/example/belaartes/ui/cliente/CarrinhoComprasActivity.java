package com.example.belaartes.ui.cliente;

import static com.example.belaartes.data.session.CheckoutSession.listCart;
import static com.example.belaartes.data.session.ClientSession.clientSession;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.example.belaartes.data.repository.OrderRepository;
import com.example.belaartes.data.repository.ProdutoRepository;
import com.example.belaartes.ui.auth.LoginActivity;
import com.example.belaartes.ui.comum.BaseClienteActivity;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CarrinhoComprasActivity extends BaseClienteActivity  {
    private TextView productSubTotal;
    private Button sendProof;
    private CheckBox terms;

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

    protected boolean checkUserConnection() {

        return clientSession != null;
    }


    protected void sendMessage() {
        sendOrder();
        String numeroTelefone = "5571983579082";

        StringBuilder mensagem = new StringBuilder();
        mensagem.append("Olá, meu nome é ").append(clientSession.getNome()).append("!\n\n");
        mensagem.append("Gostaria de solicitar os seguintes produtos:\n\n");

        BigDecimal total = BigDecimal.ZERO;
        int contador = 1;

        for (ItemPedido item : listCart) {
            Produto produto = item.getProduto();
            BigDecimal preco = produto.getPreco().multiply(BigDecimal.valueOf(item.getQuantidade()));
            total = total.add(preco);

            mensagem.append(contador++).append(". Produto: ").append(produto.getNome()).append("\n")
                    .append("   Descrição: ").append(produto.getDescricao()).append("\n")
                    .append("   Quantidade: ").append(item.getQuantidade()).append("\n")
                    .append("   Preço: R$ ").append(produto.getPreco()).append("\n\n");
        }

        mensagem.append("Total: R$ ").append(total);

        try {
            String mensagemCodificada = URLEncoder.encode(mensagem.toString(), "UTF-8");
            String url = "https://wa.me/" + numeroTelefone + "?text=" + mensagemCodificada;

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(CarrinhoComprasActivity.this, "Erro ao abrir o WhatsApp", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupListeners() {
        sendProof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkTerms()) {
                    if (checkUserConnection()) {
                        sendMessage();
                    } else {
                        runOnUiThread(() -> {
                            Intent startLogin = new Intent(CarrinhoComprasActivity.this, LoginActivity.class);
                            startActivity(startLogin);
                            Toast.makeText(CarrinhoComprasActivity.this, "Faça o login para continuar", Toast.LENGTH_SHORT).show();
                        });
                    }
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(CarrinhoComprasActivity.this, "Por favor, aceite os termos de uso.", Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });

    }

    private void initializeUI() {
        this.productSubTotal = findViewById(R.id.txtSubtotal);
        this.sendProof = findViewById(R.id.btnFinalizarCompra);
        this.terms = findViewById(R.id.checkTermos);
    }

    private void calculateAllProduct() {
        BigDecimal subCalculate = BigDecimal.ZERO;
        for (ItemPedido requestCart : listCart) {
            subCalculate = subCalculate.add(requestCart.getProduto().getPreco());
        }
        productSubTotal.setText(String.valueOf(subCalculate));
    }

    private void sendOrder() {
        OrderRepository.registerOrder(this, new OrderRepository.RegisterOrderCallback() {
            @Override
            public void onSuccess(String order) {
                Toast.makeText(CarrinhoComprasActivity.this, order, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(CarrinhoComprasActivity.this, error, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private boolean checkTerms() {
        return terms.isChecked();
    }
}