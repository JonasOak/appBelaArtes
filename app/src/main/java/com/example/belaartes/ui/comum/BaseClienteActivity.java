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
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BaseClienteActivity extends AppCompatActivity {


    private BottomNavigationView bottomNavigationView;
    private EditText edtBuscar;
    private ImageView imgSearch;

    @Override
    protected void onResume() {
        super.onResume();

        if (this instanceof CarrinhoComprasActivity) {
            edtBuscar.setVisibility(View.GONE);
            imgSearch.setVisibility(View.GONE);
        } else {
            imgSearch.setVisibility(View.VISIBLE);
            edtBuscar.setVisibility(View.VISIBLE);

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.menu_cliente); // Usa o layout que tem o topo + menu inferior

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        edtBuscar = findViewById(R.id.edtBuscar);
        imgSearch = findViewById(R.id.icBuscar);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                runOnUiThread(()->{
                    Intent screenCheckout = new Intent(BaseClienteActivity.this, CatalogoProdutosActivity.class);
                    startActivity(screenCheckout);
                    Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
                });
                return true;
            } else if (itemId == R.id.nav_carrinho) {
                runOnUiThread(()->{
                    Intent screenCheckout = new Intent(BaseClienteActivity.this, CarrinhoComprasActivity.class);
                    startActivity(screenCheckout);
                    Toast.makeText(this, "Carrinho", Toast.LENGTH_SHORT).show();
                });
                return true;
            } else if (itemId == R.id.nav_conta) {
                runOnUiThread(()->{
                    Intent screenCheckout = new Intent(BaseClienteActivity.this, LoginActivity.class);
                    startActivity(screenCheckout);
                    Toast.makeText(this, "Login", Toast.LENGTH_SHORT).show();
                });
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
