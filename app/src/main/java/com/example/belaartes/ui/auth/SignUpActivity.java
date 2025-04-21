package com.example.belaartes.ui.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.belaartes.R;
import com.example.belaartes.data.api.VolleySingleton;
import com.example.belaartes.ui.cliente.CatalogoProdutosActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpActivity extends AppCompatActivity {

    EditText editEmail, editSenha;
    Button btnCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.signUpLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editEmail = findViewById(R.id.editEmail);
        editSenha = findViewById(R.id.editSenha);
        btnCadastrar = findViewById(R.id.btnCadastrar);

        btnCadastrar.setOnClickListener(v -> cadastrarUsuario());

    }

    private void cadastrarUsuario() {
        String email = editEmail.getText().toString().trim();
        String senha = editSenha.getText().toString().trim();

        if (email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }
        String url = "http://10.0.2.2:8080/usuarios";

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("email", email);
            jsonBody.put("senhaHash", senha);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                response -> {
                    Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences = getSharedPreferences("usuario", MODE_PRIVATE);
                    sharedPreferences.edit().putString("email", email).putString("senha", senha).apply();
                    /***************************************** FAZER ATENÇÃO ******************************************/
                    Intent intent = new Intent(SignUpActivity.this, CatalogoProdutosActivity.class); // substituir este último argumento
                    startActivity(intent);                                                                    // pela classe da tela home, quando for criada
                    finish();

                },
                error -> {
                    if (error.networkResponse != null && error.networkResponse.statusCode == 400) {
                        String errorMessage = new String(error.networkResponse.data);
                        if (errorMessage.contains("E-mail")) {
                            Toast.makeText(this, "Erro: " + errorMessage, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(this, "Erro ao cadastrar: " + errorMessage, Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(this, "Erro ao cadastrar: " + error.getMessage() + "Tente mais tarde", Toast.LENGTH_LONG).show();
                    }
                });
        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }
}