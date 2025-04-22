package com.example.belaartes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.belaartes.adapters.ProdutoAdapter;
import com.example.belaartes.data.model.entities.Produto;
import com.example.belaartes.data.repository.ProdutoRepository;

import java.util.List;

public class TelaHome extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProdutoAdapter adapter;
    private List<Produto> listaProdutos;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_home);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listaProdutos = gerarProdutosFake(); // ou vir da API futuramente

        // Aqui, você passa o OnProdutoClickListener implementado na TelaHome
        adapter = new ProdutoAdapter(listaProdutos, this, new ProdutoAdapter.OnProdutoClickListener() {
            @Override
            public void onProdutoClick(Produto produto) {
                // Ao clicar no produto, abre a tela de catalogoProduto
                Intent intent = new Intent(TelaHome.this, catalogoProduto.class);
                intent.putExtra("nome", produto.getNome());
                intent.putExtra("descricao", produto.getDescricao());
                intent.putExtra("categoria", produto.getCategoria());
                intent.putExtra("preco", produto.getPreco());
                intent.putExtra("estoque", produto.getEstoque());
                intent.putExtra("imagem", produto.getImagem()); // Se for necessário
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(adapter);
    }

    // Função para gerar lista de produtos (por enquanto com dados fake)
    private List<Produto> gerarProdutosFake() {
        // Você pode adicionar produtos fake ou vir da API aqui
        return ProdutoRepository.getProdutos(); // Supondo que tenha esse método
    }
}
