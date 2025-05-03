package com.example.belaartes.ui.auth;

import static com.example.belaartes.data.session.ClientSession.clientSession;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.belaartes.R;
import com.example.belaartes.data.model.entities.Cliente;
import com.example.belaartes.data.repository.UsuarioRepository;
import com.example.belaartes.data.session.ClientSession;
import com.example.belaartes.ui.admin.AdminActivity;
import com.example.belaartes.ui.admin.AdminProdutosActivity;
import com.example.belaartes.ui.cliente.HomeClienteActivity;
import com.example.belaartes.ui.cliente.RegisterClientActivity;
import com.example.belaartes.ui.comum.BaseClienteActivity;
import com.example.belaartes.ui.user.ActivityUserConfig;

public class LoginActivity extends BaseClienteActivity {

    private EditText editEmail, editSenha;
    private Button btnLogin;
    private Switch saveSession;
    private SharedPreferences preferences;


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
        saveSession = findViewById(R.id.switch1);

        configurarEventos();
        redirecionaTelaCadastro();
        checkLoggedIn();

        preferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        if (preferences.getBoolean("lembrar", false)) {
            editEmail.setText(preferences.getString("email", ""));
            editSenha.setText(preferences.getString("senha", ""));
            saveSession.setChecked(true);
        }

    }


    @Override
    protected int getSelectedBottomNavigationItemId() {
        return R.id.nav_conta;
    }

    public void configurarEventos() {
        btnLogin.setOnClickListener(v -> {
            String email = editEmail.getText().toString().trim();
            String senha = editSenha.getText().toString().trim();
            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }
            UsuarioRepository.Authenticate(this, email, senha, new UsuarioRepository.ClientLoginCallback() {
                @Override
                public void onSuccess(Cliente client) {
                    saveSessionConfig(email, senha);
                    ClientSession.setClientSession(client);
                    if (client.getUsuario().getCargo().equals("CLIENTE")) {
                        Intent intent = new Intent(LoginActivity.this, HomeClienteActivity.class);
                        startActivity(intent);
                        finish();
                    } else if(client.getUsuario().getCargo().equals("ADM")){
                        Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        runOnUiThread(() -> {
                            Toast.makeText(LoginActivity.this, "Erro desconhecido", Toast.LENGTH_SHORT).show();
                        });
                    }
                }

                @Override
                public void onError(String errorMessage) {
                    runOnUiThread(() -> {
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

    /**
     * Lembrar senha
     *
     * @param email
     * @param senha
     */
    private void saveSessionConfig(String email, String senha) {
        if (saveSession.isChecked()) {
            // Salva email e senha
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("email", email);
            editor.putString("senha", senha);
            editor.putBoolean("lembrar", true);
            editor.apply();
        } else {
            // Limpa os dados salvos
            preferences.edit().clear().apply();
        }
    }

    /**
     * Se o usuário estiver logado troca de tela
     */
    protected void checkLoggedIn() {
        if (clientSession != null) {
            if(clientSession.getUsuario().getCargo().equals("ADM")){
                Intent screenAdmin = new Intent(LoginActivity.this, AdminActivity.class);
                startActivity(screenAdmin);

            } else {
                Intent screenUserConfig = new Intent(LoginActivity.this, ActivityUserConfig.class);
                startActivity(screenUserConfig);
                finish();
            }
        }
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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}