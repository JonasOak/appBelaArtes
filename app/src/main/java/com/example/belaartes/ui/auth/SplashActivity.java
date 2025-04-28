package com.example.belaartes.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import com.example.belaartes.R;
import com.example.belaartes.ui.admin.AdminProdutosActivity;
import com.example.belaartes.ui.cliente.CarrinhoComprasActivity;
import com.example.belaartes.ui.cliente.CatalogoProdutosActivity;
import com.example.belaartes.ui.cliente.ProdutoDetalheActivity;
import com.example.belaartes.ui.cliente.RegisterClientActivity;

public class SplashActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Aguarda 2 segundos e depois redireciona para ListaProdutosActivity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
              Intent intent = new Intent(SplashActivity.this, CatalogoProdutosActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}
