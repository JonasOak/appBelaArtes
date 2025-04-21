package com.example.belaartes.ui.admin;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.belaartes.R;
import com.example.belaartes.data.model.entities.Usuario;

public class CadastroUsuarioActivity extends AppCompatActivity {

    private EditText etEmail, etSenha;
    private Spinner spinnerCargo;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        // Inicializa views
        etEmail = findViewById(R.id.etEmail);
        etSenha = findViewById(R.id.etSenha);
        spinnerCargo = findViewById(R.id.spinnerCargo);
        Button btnSalvar = findViewById(R.id.btnSalvar);

        // Configura spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.cargos_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCargo.setAdapter(adapter);

        // Verifica se é edição
        usuario = (Usuario) getIntent().getSerializableExtra("usuario");
        if (usuario != null) {
            preencherCampos();
        }

        btnSalvar.setOnClickListener(v -> salvarUsuario());
    }

    private void preencherCampos() {
        etEmail.setText(usuario.getEmail());
        etSenha.setText(usuario.getSenhaHash());

        // Selecionar cargo no spinner
        for (int i = 0; i < spinnerCargo.getCount(); i++) {
            if (spinnerCargo.getItemAtPosition(i).toString().equals(usuario.getCargo())) {
                spinnerCargo.setSelection(i);
                break;
            }
        }
    }

    private void salvarUsuario() {
        String email = etEmail.getText().toString().trim();
        String senha = etSenha.getText().toString().trim();
        String cargo = spinnerCargo.getSelectedItem().toString();

        if (email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (usuario == null) {
            usuario = new Usuario(0, email, senha, cargo); // construtor de 4 parâmetros
        } else {
            usuario.setEmail(email);
            usuario.setSenhaHash(senha);
            usuario.setCargo(cargo);
        }

        setResult(RESULT_OK);
        finish();
    }
}