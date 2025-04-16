package com.example.belaartes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.belaartes.data.model.entities.Produto;
import com.example.belaartes.data.model.entities.Usuario;
import com.example.belaartes.data.repository.ProdutoRepository;
import com.example.belaartes.data.repository.UsuarioRepository;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView textUsuarios;
    private TextView textProdutos;
    private ImageView imageProduto;

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

        textUsuarios = findViewById(R.id.textUsuarios);
        textProdutos = findViewById(R.id.textProdutos);
        imageProduto = findViewById(R.id.imageProduto);

        buscarUsuarios();
        buscarProdutos();
    }

    private void buscarUsuarios() {
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
    }

    private void buscarProdutos() {
        ProdutoRepository.getAllProdutos(this, new ProdutoRepository.ProdutoCallback() {
            @Override
            public void onSuccess(List<Produto> produtos) {
                StringBuilder resultado = new StringBuilder();
                if (!produtos.isEmpty()) {
                    Produto p = produtos.get(0); // Exibe só o primeiro por enquanto
                    resultado.append("ID: ").append(p.getIdProduto()).append("\n");
                    resultado.append("Nome: ").append(p.getNome()).append("\n");
                    resultado.append("Descrição: ").append(p.getDescricao()).append("\n");
                    resultado.append("Categoria: ").append(p.getCategoria()).append("\n");
                    resultado.append("Preço: ").append(p.getPreco()).append("\n");
                    resultado.append("Estoque: ").append(p.getEstoque()).append("\n");

                    if (p.getImagem() != null) {
                        byte[] imageBytes = Base64.decode(p.getImagem(), Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                        imageProduto.setImageBitmap(bitmap);
                    }
                }
                textProdutos.setText(resultado.toString());
                Log.d("API", "Recebeu " + produtos.size() + " produtos");

            }

            @Override
            public void onError(String errorMessage) {
                textProdutos.setText("Erro: " + errorMessage);
            }
        });
    }
}