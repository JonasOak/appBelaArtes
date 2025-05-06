package com.example.belaartes.ui.admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.belaartes.R;
import com.example.belaartes.adapters.UsuarioAdapter;
import com.example.belaartes.data.model.entities.Usuario;
import com.example.belaartes.data.repository.UsuarioRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ListaUsuariosActivity extends AppCompatActivity {

    private RecyclerView rvUsuarios;
    private UsuarioAdapter adapter;
    private List<Usuario> listaUsuarios = new ArrayList<Usuario>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_usuarios);

        // Configurar Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Configurar RecyclerView
        rvUsuarios = findViewById(R.id.rvUsuarios);
        rvUsuarios.setLayoutManager(new LinearLayoutManager(this));

        adapter = new UsuarioAdapter(listaUsuarios, new UsuarioAdapter.UsuarioClickListener() {
            @Override
            public void onUsuarioClick(Usuario usuario) {
                // Clique no item
            }

            @Override
            public void onEditClick(Usuario usuario) {
                abrirEdicaoUsuario(usuario);
            }
        });
        rvUsuarios.setAdapter(adapter);

        // Botão flutuante
        FloatingActionButton fab = findViewById(R.id.fabAddUsuario);
        fab.setOnClickListener(v -> abrirCadastroUsuario());

        carregarUsuarios();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

//        MenuItem searchItem = menu.findItem(R.id.action_search);
//        SearchView searchView = (SearchView) searchItem.getActionView();
//        searchView.setQueryHint("Buscar usuários...");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            buscarUsuarios();
            return true;
        } else if (id == R.id.action_menu) {
            mostrarMenuDropdown(item);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void buscarUsuarios() {
        Toast.makeText(this, "Busca de usuários acionada", Toast.LENGTH_SHORT).show();
        // Implemente sua lógica de busca aqui
    }

    private void mostrarMenuDropdown(MenuItem item) {
        View menuItemView = findViewById(R.id.action_menu); // Use o ID do seu item de menu
        PopupMenu popup = new PopupMenu(this, menuItemView);

        // Inflar o menu dropdown
        popup.getMenuInflater().inflate(R.menu.dropdown_menu, popup.getMenu());

        // Listener para os itens do menu
        popup.setOnMenuItemClickListener(menuItem -> {
            int itemId = menuItem.getItemId();

            if (itemId == R.id.menu_produtos) {
                Toast.makeText(this, "Produtos selecionado", Toast.LENGTH_SHORT).show();
                return true;
            } else if (itemId == R.id.menu_clientes) {
                Toast.makeText(this, "Clientes selecionado", Toast.LENGTH_SHORT).show();
                return true;
            } else if (itemId == R.id.menu_pedidos) {
                Toast.makeText(this, "Pedidos selecionado", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });

        popup.show();
    }

    private void abrirCadastroUsuario() {
        startActivity(new Intent(this, UpdateUserActivity.class));
    }

    private void abrirEdicaoUsuario(Usuario usuario) {
        Intent intent = new Intent(this, UpdateUserActivity.class);
        intent.putExtra("usuario", usuario);
        Log.d("Response", "Usuário: " +usuario);
        startActivity(intent);
    }

    private void carregarUsuarios() {
        UsuarioRepository.getAllUsuarios(ListaUsuariosActivity.this, new UsuarioRepository.UsuarioCallback() {
            @Override
            public void onSuccess(List<Usuario> usuarios) {
                for (Usuario users : usuarios) {
                    listaUsuarios.add(new Usuario(users.getIdUsuario(), users.getEmail(), users.getSenhaHash(), users.getCargo()));
                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onError(String errorMessage) {

            }
        });

    }
}