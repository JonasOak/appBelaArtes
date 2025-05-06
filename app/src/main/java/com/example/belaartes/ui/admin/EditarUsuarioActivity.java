package com.example.belaartes.ui.admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.belaartes.R;
import com.example.belaartes.data.model.entities.Usuario;

public class EditarUsuarioActivity extends AppCompatActivity {

    private EditText etEmail, etSenha;
    private AutoCompleteTextView actvCargo;
    private Button btnSalvar;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_editar_usuario);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_usuario);

        // Configurar toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Editar Usuário");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Inicializar views
        etEmail = findViewById(R.id.etEmail);
        etSenha = findViewById(R.id.etSenha);
        actvCargo = findViewById(R.id.actvCargo);
        btnSalvar = findViewById(R.id.btnSalvar);

        // Obter usuário da intent
        usuario = (Usuario) getIntent().getSerializableExtra("usuario");
        if (usuario != null) {
            etEmail.setText(usuario.getEmail());
            etSenha.setText(usuario.getSenhaHash());
            actvCargo.setText(usuario.getCargo());
        }

        // Configurar AutoCompleteTextView para cargos
        String[] cargos = getResources().getStringArray(R.array.cargos_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, cargos);
        actvCargo.setAdapter(adapter);

        // Configurar botão salvar
        btnSalvar.setOnClickListener(v ->
                salvarAlteracoes()
        );
    }

    private void salvarAlteracoes() {
        Log.d("Response", "Usuario: " + usuario);
        String email = etEmail.getText().toString().trim();
        String senha = etSenha.getText().toString().trim();
        String cargo = actvCargo.getText().toString().trim();

        if (email.isEmpty() || senha.isEmpty() || cargo.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (usuario != null) {
            usuario.setEmail(email);
            usuario.setSenhaHash(senha);
            usuario.setCargo(cargo);
        }

        Intent resultIntent = new Intent();
        resultIntent.putExtra("usuario", usuario);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}