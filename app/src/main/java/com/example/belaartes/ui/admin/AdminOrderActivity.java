package com.example.belaartes.ui.admin;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.belaartes.R;
import com.example.belaartes.adapters.ProdutoAdminAdapter;
import com.example.belaartes.data.model.entities.Cliente;
import com.example.belaartes.data.model.entities.Produto;
import com.example.belaartes.data.repository.OrderRepository;
import com.example.belaartes.ui.comum.MenuHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AdminOrderActivity extends AppCompatActivity implements ProdutoAdminAdapter.OnAdminActionListener  {
    private RecyclerView recyclerView;
    private ProdutoAdminAdapter adapter;
    private FloatingActionButton fabAddProduto;
    private List<Cliente> listClient;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_produtos);
        listClient = new ArrayList<>();
        getAllClientsOrders();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        recyclerView = findViewById(R.id.rvProdutosAdmin);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fabAddProduto = findViewById(R.id.fabAddProduto);
        fabAddProduto.setOnClickListener(v -> {
            startActivity(new Intent(this, CriarProdutoActivity.class));
        });
        fabAddProduto.setVisibility(View.GONE);

        getAllOrder();
    }


    private void getAllOrder(){
        OrderRepository.getAllProdutos(AdminOrderActivity.this, new OrderRepository.OrdersProdutoCallback() {
            @Override
            public void onSuccess(List<Produto> produtos) {
                adapter = new ProdutoAdminAdapter(produtos, AdminOrderActivity.this, AdminOrderActivity.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onError(String mensagemErro) {

            }
        });
    }

    private void getAllClientsOrders(){
        OrderRepository.getAllClientOrders(AdminOrderActivity.this, new OrderRepository.OrdersClientsCallBack() {
            @Override
            public void onSuccess(List<Cliente> client) {
                listClient.addAll(client);
            }

            @Override
            public void onError(String mensagemErro) {

            }
        });
    };


    //Visualizar cliente
    @Override
    public void onEditarClick(Produto produto) {
        Cliente searchClient = listClient.get(adapter.getGetPosition());
        new AlertDialog.Builder(this)
                .setTitle("Detalhes do Usuário")
                .setMessage("NOME: " + searchClient.getNome() +
                        "\nCPF: " + searchClient.getCpf() +
                        "\nTELEFONE: " + searchClient.getTelefone())
                .setPositiveButton("Ok", (dialog, which) -> {

                })

                .show();


    }

    @Override
    public void onExcluirClick(Produto produto) {

    }
}
