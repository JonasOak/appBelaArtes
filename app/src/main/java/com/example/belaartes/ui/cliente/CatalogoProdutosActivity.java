package com.example.belaartes.ui.cliente;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.belaartes.R;
import com.example.belaartes.adapters.ProdutoAdapter;
import com.example.belaartes.data.model.entities.Produto;
import com.example.belaartes.data.repository.ProdutoRepository;
import com.example.belaartes.ui.comum.BaseClienteActivity;

import java.util.List;

public class CatalogoProdutosActivity extends BaseClienteActivity {

    private RecyclerView recyclerView;
    private ProdutoAdapter adapter;
    private EditText search_text;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produtos);
        initializeUI();
        loadRecyclerView();
        loadProducts();
        listenToText();
    }

    @Override
    protected int getSelectedBottomNavigationItemId() {
        return R.id.nav_produtos;
    }

    protected void initializeUI(){
        recyclerView = findViewById(R.id.rvProdutos);
        search_text = findViewById(R.id.edtBuscar);
    }
    private void loadRecyclerView(){
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setPadding(0, 0, 0, 0);
        recyclerView.setClipToPadding(false);
        recyclerView.setClipChildren(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setBackgroundColor(ContextCompat.getColor(this, R.color.pink));
    }

    private void loadProducts() {
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

    private void listenToText() {
        search_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (adapter != null) {
                    adapter.filtrar(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}