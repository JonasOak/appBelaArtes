package com.example.belaartes.ui.cliente;

import static com.example.belaartes.data.session.CheckoutSession.listCart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.belaartes.R;
import com.example.belaartes.data.model.entities.ItemPedido;
import com.example.belaartes.data.model.entities.Produto;
import com.example.belaartes.data.repository.ProdutoRepository;
import com.example.belaartes.data.session.CheckoutSession;
import com.example.belaartes.ui.comum.BaseClienteActivity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDetalheActivity extends BaseClienteActivity {

    private ImageView imgProduto;
    private TextView tvNome, tvDescricao, tvCategoria, tvPreco, tvEstoque;
    private Produto produtoCart;
    private ImageButton addCart;
    private ImageButton close;

    private Context context;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_produto_detalhe);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeUi();
        int produtoId = getIntent().getIntExtra("produtoId", -1);
        if (produtoId != -1) {
            carregarProduto(produtoId);
        }
        setupListeners();
    }

    private void initializeUi() {
        imgProduto = findViewById(R.id.imgProduto);
        tvNome = findViewById(R.id.tvNome);
        tvDescricao = findViewById(R.id.tvDescricao);
        tvCategoria = findViewById(R.id.tvCategoria);
        tvPreco = findViewById(R.id.tvPreco);
        tvEstoque = findViewById(R.id.tvEstoque);
        addCart = findViewById(R.id.btnAddCarrinho);
        this.produtoCart = new Produto();
        close = findViewById(R.id.btn_product_datalhe_close);
    }

    private void setupListeners() {
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ItemPedido requestCart = new ItemPedido(produtoCart, 1, produtoCart.getPreco());
                System.out.println(requestCart);
                listCart.add(requestCart);
                Toast.makeText(ProdutoDetalheActivity.this, "Adicionado ao carrinho: " + produtoCart.getNome(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected Produto createProduct(Produto product) {
        return new Produto(product.getIdProduto(), product.getNome(), product.getDescricao(), product.getCategoria(), product.getPreco(), product.getImagem(), product.getEstoque());
    }

    @Override
    protected int getSelectedBottomNavigationItemId() {
        return R.id.nav_produtos;
    }

    private void carregarProduto(int id) {
        ProdutoRepository.buscarProdutoPorId(this, id, new ProdutoRepository.ProdutoCallbackUnico() {
            @Override
            public void onSuccess(Produto produto) {
                tvNome.setText(produto.getNome());
                tvDescricao.setText(produto.getDescricao());
                tvCategoria.setText(produto.getCategoria());
                tvPreco.setText(String.format("R$ %.2f", produto.getPreco()));
                tvEstoque.setText("Estoque: " + produto.getEstoque());
                String imagemBase64 = produto.getImagem();
                if (imagemBase64 != null && !imagemBase64.isEmpty()) {
                    byte[] imagemBytes = Base64.decode(imagemBase64, Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imagemBytes, 0, imagemBytes.length);
                    imgProduto.setImageBitmap(bitmap);
                    // Criar produto
                    produtoCart = createProduct(produto);

                } else {
                    imgProduto.setImageResource(R.drawable.ic_launcher_foreground);
                }

            }

            @Override
            public void onError(String error) {
                Toast.makeText(ProdutoDetalheActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}