package com.example.belaartes.ui.cliente;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.belaartes.R;
import com.example.belaartes.adapters.ProdutoAdapter;
import com.example.belaartes.data.model.entities.Produto;
import com.example.belaartes.data.repository.ProdutoRepository;

import java.util.List;

public class CatalogoProdutosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProdutoAdapter adapter;

    // Botão de carrinho
    private Button checkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produtos);

        recyclerView = findViewById(R.id.rvProdutos);

        //Carregar configuração de carrinho de comprar
        this.checkout = findViewById(R.id.btn_checkout);
        setupListeners();


        // GridLayoutManager com 2 colunas
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setPadding(0, 200, 0, 0);
        recyclerView.setClipToPadding(false);
        recyclerView.setClipChildren(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setBackgroundColor(ContextCompat.getColor(this, R.color.pink));

        carregarProdutos();
    }

    private void setupListeners(){
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(()->{
                    Intent screenCheckout = new Intent(CatalogoProdutosActivity.this, CarrinhoComprasActivity.class);
                    startActivity(screenCheckout);
                    Toast.makeText(CatalogoProdutosActivity.this, "testando", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private void carregarProdutos() {
        ProdutoRepository.getAllProdutos(this, new ProdutoRepository.ProdutoCallback() {
            @Override
            public void onSuccess(List<Produto> produtos) {
                adapter = new ProdutoAdapter(produtos, CatalogoProdutosActivity.this, produto -> {
                    Intent intent = new Intent(CatalogoProdutosActivity.this, ProdutoDetalheActivity.class);
                    intent.putExtra("produtoId", produto.getIdProduto());
                    startActivity(intent);
                });
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onError(String error) {
                Toast.makeText(CatalogoProdutosActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}