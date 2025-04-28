package com.example.belaartes.ui.cliente;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.belaartes.R;

public class TermsActivity extends AppCompatActivity {

    private Button btnReturn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terms);
        initializeUI();
        setupListeners();
    }
    private void initializeUI() {
        this.btnReturn = findViewById(R.id.btn_terms_close);
    }
    private void setupListeners(){
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
