package com.example.belaartes;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.belaartes.data.model.entities.Usuario;
import com.example.belaartes.data.repository.UsuarioRepository;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnBuscarUsuarios;
    private TextView textUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnBuscarUsuarios = findViewById(R.id.btnBuscarUsuarios);
        textUsuarios = findViewById(R.id.textUsuarios);

        btnBuscarUsuarios.setOnClickListener(view -> {
            UsuarioRepository.getAllUsuarios(this, new UsuarioRepository.UsuarioCallback() {
                @Override
                public void onSuccess(List<Usuario> usuarios) {
                    StringBuilder resultado = new StringBuilder();
                    for (Usuario u : usuarios) {
                        resultado.append("ID: ").append(u.getIdUsuario()).append("\n");
                        resultado.append("Email: ").append(u.getEmail()).append("\n");
                        resultado.append("Cargo: ").append(u.getCargo()).append("\n\n");
                    }
                    textUsuarios.setText(resultado.toString());
                }

                @Override
                public void onError(String errorMessage) {
                    textUsuarios.setText("Erro: " + errorMessage);
                }
            });
        });

    }
}