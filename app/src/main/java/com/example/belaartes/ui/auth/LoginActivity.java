package com.example.belaartes.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.belaartes.R;
import com.example.belaartes.data.model.entities.Cliente;
import com.example.belaartes.data.model.entities.Usuario;
import com.example.belaartes.data.repository.UsuarioRepository;
import com.example.belaartes.data.session.ClientSession;
import com.example.belaartes.ui.cliente.CatalogoProdutosActivity;
import com.example.belaartes.ui.cliente.RegisterClientActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText editEmail, editSenha;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_login), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editEmail = findViewById(R.id.editEmail);
        editSenha = findViewById(R.id.editSenha);
        btnLogin = findViewById(R.id.btnLogin);

        configurarEventos();
        redirecionaTelaCadastro();
    }

    public void configurarEventos() {
        btnLogin.setOnClickListener(v -> {
            String email = editEmail.getText().toString().trim();
            String senha = editSenha.getText().toString().trim();

            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }
            UsuarioRepository.Authenticar(this, email, senha, new UsuarioRepository.ClientLoginCallback() {
                @Override
                public void onSuccess(Cliente client) {
                    ClientSession.setClientSession(client);
                    Log.d("Login response", "date " +client);
                    finish();
                }

                @Override
                public void onError(String errorMessage) {
                    runOnUiThread(()->{
                        Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                    });

                }
            });

//            UsuarioRepository.login(this, email, senha, new UsuarioRepository.LoginCallback() {
//                @Override
//                public void onSuccess(Usuario usuario) {
//                    Toast.makeText(LoginActivity.this, "Login bem-sucedido", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(LoginActivity.this, CatalogoProdutosActivity.class);
//                    //Receber o valor do id para fazer consulta dos dados do cliente
//                //    ClientSession.clientSession.setIdCliente(usuario.getIdUsuario());
//                    startActivity(intent);
//                    finish();
//                }
//
//                @Override
//                public void onError(String errorMessage) {
//                    Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
//                }
//            });
        });
    }

    public void redirecionaTelaCadastro() {
        Button btnCadastrar = findViewById(R.id.btnCadastrar);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterClientActivity.class);
                startActivity(intent);
            }
        });
    }

}