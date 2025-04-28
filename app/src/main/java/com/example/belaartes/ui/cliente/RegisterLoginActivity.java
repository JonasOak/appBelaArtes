package com.example.belaartes.ui.cliente;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.belaartes.R;
import com.example.belaartes.data.model.entities.Cliente;
import com.example.belaartes.data.model.entities.Usuario;
import com.example.belaartes.data.repository.ClientRepository;
import com.example.belaartes.ui.auth.LoginActivity;

public class RegisterLoginActivity extends AppCompatActivity {

    private EditText email, password, repeatPassword;
    private Button finish, showPasswordAndRepeatPassword;
    private boolean showPassword;

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
        this.showPasswordAndRepeatPassword = findViewById(R.id.register_login_show_password);

        this.showPassword = false;
    }

    private void setupListeners() {
        showPasswordAndRepeatPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonShowPassword();
            }
        });
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkDate(email.getText().toString().trim(), password.getText().toString().trim(), repeatPassword.getText().toString().trim())) {
                    Intent intent = getIntent();
                    Cliente getDateClient = (Cliente) getIntent().getSerializableExtra("clientRegister");
                    getDateClient.setUsuario(getUserFormat());
                    registerUser(getDateClient);
                    Log.d("Register login", "Resposta: " + getDateClient);
                }
            }
        });
    }


    protected Usuario getUserFormat() {
        return new Usuario(email.getText().toString().trim(), password.getText().toString().trim());
    }
    private void buttonShowPassword(){
        if(!showPassword){
            password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            repeatPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            this.showPassword = true;
        } else {
            password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            repeatPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            this.showPassword = false;
        }
        password.setSelection(password.getText().length());
    }
    private boolean checkDate(String email, String password, String repeatPassword) {
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
                        Intent activityLogin = new Intent(RegisterLoginActivity.this, LoginActivity.class);
                        startActivity(activityLogin);
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
