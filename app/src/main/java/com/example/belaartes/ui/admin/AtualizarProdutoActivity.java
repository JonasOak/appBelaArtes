package com.example.belaartes.ui.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.example.belaartes.data.model.entities.Produto;
import com.example.belaartes.data.repository.ProdutoRepository;
import com.example.belaartes.ui.comum.MenuHelper;
import com.example.belaartes.R;

import java.math.BigDecimal;
import java.util.List;

public class AtualizarProdutoActivity extends AppCompatActivity {
    private int idProduct;
    private String imgProductBase64;
    private ImageView btnMenu;
    private Button saveProduct;
    private EditText productName, productDescription, productPrice, productAmount;
    private TextView screenTitle;
    private Produto productSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_produto);
        initializeUI();
        fillInTheField();
        setupListeners();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setQueryHint("Buscar produtos...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Ação ao apertar "buscar"
                Toast.makeText(AtualizarProdutoActivity.this, "Buscando por: " + query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Atualiza em tempo real (ex: filtrar lista)
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_menu) {
            View anchor = findViewById(R.id.toolbar);
            MenuHelper.mostrarMenu(this, anchor);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeUI(){
        this.screenTitle = findViewById(R.id.txtTitulo);
        screenTitle.setText("Atualizar produto");
        this.productName = findViewById(R.id.edtNome);
        this.productDescription = findViewById(R.id.edtDescricao);
        this.productPrice = findViewById(R.id.edtPreco);
        this.productAmount = findViewById(R.id.edtQuantidade);
        this.saveProduct = findViewById(R.id.btnSalvar);
        this.productSelected = new Produto();
        this.idProduct = 0;
        this.imgProductBase64 = null;

    }
    private void fillInTheField(){
        Intent getProductSave = getIntent();
        this.productSelected= (Produto) getProductSave.getSerializableExtra("produto");
        if(productSelected != null){
            this.idProduct = productSelected.getIdProduto();
            this.productName.setText(productSelected.getNome());
            this.productDescription.setText(productSelected.getDescricao());
            this.productPrice.setText(String.valueOf(productSelected.getPreco()));
            this.productAmount.setText(String.valueOf(productSelected.getEstoque()));
            this.imgProductBase64 = productSelected.getImagem();
        }

    }

    private void setupListeners(){
        saveProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Produto saveProduct = new Produto(productSelected.getIdProduto(), productName.getText().toString(), productDescription.getText().toString(), productSelected.getCategoria(), new BigDecimal(productPrice.getText().toString()), productSelected.getImagem(), Integer.valueOf(productAmount.getText().toString()));

                ProdutoRepository.atualizarProduto(AtualizarProdutoActivity.this, saveProduct, new ProdutoRepository.ProdutoCallback() {
                    @Override
                    public void onSuccess(List<Produto> produtos) {
                        runOnUiThread(()->{
                            Toast.makeText(AtualizarProdutoActivity.this, "Produto atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                        });
                    }

                    @Override
                    public void onError(String mensagemErro) {
                        runOnUiThread(()->{
                            Toast.makeText(AtualizarProdutoActivity.this, "Erro ao atualizar produto!", Toast.LENGTH_SHORT).show();
                        });
                    }
                });
            }
        });

    }

}
