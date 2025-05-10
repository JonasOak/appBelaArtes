package com.example.belaartes.ui.cliente;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.belaartes.R;
import com.example.belaartes.data.model.entities.Cliente;
import com.example.belaartes.data.model.entities.Usuario;
import com.example.belaartes.data.repository.ClientRepository;
import com.example.belaartes.ui.auth.LoginActivity;
import com.google.android.material.button.MaterialButton;

public class RegisterLoginActivity extends AppCompatActivity {

    private EditText email, password, repeatPassword;
    private MaterialButton finish;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_register_login);
        initializeUI();
        setupListeners();
    }

    private void initializeUI() {
        this.email = findViewById(R.id.register_login);
        this.password = findViewById(R.id.register_password);
        this.repeatPassword = findViewById(R.id.register_repeat_password);
        this.finish = findViewById(R.id.btn_register_finish);
    }

    private void setupListeners() {
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailText = email.getText().toString().trim();
                String passwordText = password.getText().toString().trim();
                String repeatPasswordText = repeatPassword.getText().toString().trim();

                if (checkData(emailText, passwordText, repeatPasswordText)) {
                    Cliente client = (Cliente) getIntent().getSerializableExtra("clientRegister");
                    if (client != null) {
                        client.setUsuario(new Usuario(emailText, passwordText));
                        registerUser(client);
                        Log.d("RegisterLogin", "Cliente pronto para cadastro: " + client);
                    } else {
                        Toast.makeText(RegisterLoginActivity.this, "Erro: dados do cliente não encontrados.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private boolean checkData(String email, String password, String repeatPassword) {
        if (email == null || email.isEmpty()) {
            this.email.setError("Email não pode ser vazio.");
            return false;
        }
        if (!email.contains("@")) {
            this.email.setError("Email inválido. Por favor, insira um email válido.");
            return false;
        }
        if (password.isEmpty()) {
            this.password.setError("A senha não pode ser vazia.");
            return false;
        }
        if (repeatPassword.isEmpty()) {
            this.repeatPassword.setError("A confirmação da senha não pode ser vazia.");
            return false;
        }
        if (!password.equals(repeatPassword)) {
            this.repeatPassword.setError("As senhas não coincidem. Por favor, tente novamente.");
            return false;
        }

        return true;
    }

    private void registerUser(Cliente newClient) {
        ClientRepository.register(this, newClient, new ClientRepository.RegisterCallback() {
            @Override
            public void onSuccess(String response) {
                runOnUiThread(() -> {
                    Toast.makeText(RegisterLoginActivity.this, response, Toast.LENGTH_LONG).show();
                    if (response.equals("Cliente cadastrado")) {
                        Intent loginIntent = new Intent(RegisterLoginActivity.this, LoginActivity.class);
                        startActivity(loginIntent);
                        finish();
                    }
                });
            }

            @Override
            public void onError(String error) {
                runOnUiThread(() -> {
                    Toast.makeText(RegisterLoginActivity.this, error, Toast.LENGTH_LONG).show();
                });
            }
        });
    }
}
