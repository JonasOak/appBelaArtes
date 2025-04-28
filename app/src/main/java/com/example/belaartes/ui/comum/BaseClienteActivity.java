package com.example.belaartes.ui.comum;

import static android.view.View.GONE;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.belaartes.R;
import com.example.belaartes.ui.auth.LoginActivity;
import com.example.belaartes.ui.cliente.CarrinhoComprasActivity;
import com.example.belaartes.ui.cliente.CatalogoProdutosActivity;
import com.example.belaartes.ui.cliente.HomeClienteActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public abstract class BaseClienteActivity extends AppCompatActivity {
    protected BottomNavigationView bottomNavigationView;
    private EditText edtBuscar;
    private ImageView imgSearch;
    protected abstract int getSelectedBottomNavigationItemId();

    @Override
    protected void onResume() {
        super.onResume();

        if (this instanceof CarrinhoComprasActivity || this instanceof LoginActivity) {
            edtBuscar.setVisibility(View.GONE);
            imgSearch.setVisibility(View.GONE);
        } else {
            edtBuscar.setVisibility(View.VISIBLE);
            imgSearch.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.menu_cliente);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        edtBuscar = findViewById(R.id.edtBuscar);
        imgSearch = findViewById(R.id.icBuscar);

        int selectedItemId = getSelectedBottomNavigationItemId();
        if (selectedItemId != 0) {
            bottomNavigationView.setSelectedItemId(selectedItemId);
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == getSelectedBottomNavigationItemId()) {
                return true;
            }

            Intent intent = null;
            if (itemId == R.id.nav_home) {
                intent = new Intent(this, HomeClienteActivity.class);
            } else if (itemId == R.id.nav_produtos) {
                intent = new Intent(this, CatalogoProdutosActivity.class);
            } else if (itemId == R.id.nav_carrinho) {
                intent = new Intent(this, CarrinhoComprasActivity.class);
            } else if (itemId == R.id.nav_conta) {
                intent = new Intent(this, LoginActivity.class);
            }

            if (intent != null) {
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
                return true;
            }
            return false;
        });
    }

    @Override
    public void setContentView(int layoutResID) {
        View content = LayoutInflater.from(this).inflate(layoutResID, findViewById(R.id.containerTela), false);
        findViewById(R.id.containerTela).setVisibility(View.VISIBLE);
        ((android.widget.FrameLayout) findViewById(R.id.containerTela)).addView(content);
    }
}
