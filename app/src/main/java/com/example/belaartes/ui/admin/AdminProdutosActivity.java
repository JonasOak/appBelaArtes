package com.example.belaartes.ui.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.belaartes.ui.comum.MenuHelper;
import com.example.belaartes.R;
import com.example.belaartes.adapters.ProdutoAdminAdapter;
import com.example.belaartes.data.model.entities.Produto;
import com.example.belaartes.data.repository.ProdutoRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AdminProdutosActivity extends AppCompatActivity implements ProdutoAdminAdapter.OnAdminActionListener {

    private RecyclerView recyclerView;
    private ProdutoAdminAdapter adapter;
    private FloatingActionButton fabAddProduto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_produtos);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // RecyclerView
        recyclerView = findViewById(R.id.rvProdutosAdmin);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Botão flutuante para adicionar produto
        fabAddProduto = findViewById(R.id.fabAddProduto);
        fabAddProduto.setOnClickListener(v -> {
            startActivity(new Intent(this, CadastroProdutoActivity.class));
        });

        // Carrega os produtos
        carregarProdutos();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setQueryHint("Buscar produtos...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Ação ao apertar "buscar"
                Toast.makeText(AdminProdutosActivity.this, "Buscando por: " + query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_menu) {
            View anchor = findViewById(R.id.toolbar);
            MenuHelper.mostrarMenu(this, anchor);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void carregarProdutos() {
        ProdutoRepository.getAllProdutos(this, new ProdutoRepository.ProdutoCallback() {
            @Override
            public void onSuccess(List<Produto> produtos) {
                adapter = new ProdutoAdminAdapter(produtos, AdminProdutosActivity.this, AdminProdutosActivity.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onError(String error) {
                Toast.makeText(AdminProdutosActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onEditarClick(Produto produto) {
        abrirEdicao(produto);
    }

    @Override
    public void onExcluirClick(Produto produto) {
        confirmarExclusao(produto);
    }

    private void abrirEdicao(Produto produto) {
        Intent intent = new Intent(this, CadastroProdutoActivity.class);
        intent.putExtra("produto", produto);
        startActivity(intent);
    }

   private void confirmarExclusao(Produto produto) {
       new AlertDialog.Builder(this)
                .setTitle("Confirmar exclusão")
               .setMessage("Deseja realmente excluir " + produto.getNome() + "?")
                .setPositiveButton("Excluir", (dialog, which) -> excluirProduto(produto))
                .setNegativeButton("Cancelar", null)
                .show();
   }

   private void excluirProduto(Produto produto) {
       ProdutoRepository.deleteProduto(this, produto.getIdProduto(), new ProdutoRepository.ProdutoDeleteCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(AdminProdutosActivity.this, "Produto excluído", Toast.LENGTH_SHORT).show();
                carregarProdutos();
            }

            @Override
           public void onError(String error) {
               Toast.makeText(AdminProdutosActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}