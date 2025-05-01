package com.example.belaartes.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.belaartes.R;
import com.example.belaartes.ui.cliente.HomeClienteActivity;


public class SplashActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //Força tema claro
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        // Aguarda 2 segundos e depois redireciona para ListaProdutosActivity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
              Intent intent = new Intent(SplashActivity.this, HomeClienteActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}
