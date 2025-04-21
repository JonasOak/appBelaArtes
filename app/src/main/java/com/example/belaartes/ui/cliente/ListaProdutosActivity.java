package com.example.belaartes.ui.cliente;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.belaartes.R;
import com.example.belaartes.adapters.ProdutoAdapter;
import com.example.belaartes.data.model.entities.Produto;
import com.example.belaartes.data.repository.ProdutoRepository;

import java.util.List;

public class ListaProdutosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProdutoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produtos);

        recyclerView = findViewById(R.id.rvProdutos);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // 2 colunas

        carregarProdutos();
    }

    private void carregarProdutos() {
        ProdutoRepository.getAllProdutos(this, new ProdutoRepository.ProdutoCallback() {
            @Override
            public void onSuccess(List<Produto> produtos) {
                adapter = new ProdutoAdapter(produtos, ListaProdutosActivity.this, null);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onError(String error) {
                Toast.makeText(ListaProdutosActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}