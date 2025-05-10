package com.example.belaartes.ui.admin;

import android.Manifest;
import android.util.Base64;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.belaartes.R;
import com.example.belaartes.data.abstractClass.ProductExtends;
import com.example.belaartes.data.model.dto.ProdutoDto;
import com.example.belaartes.data.model.entities.Produto;
import com.example.belaartes.data.repository.ProdutoRepository;
import com.example.belaartes.data.util.MaskUtils;
import com.example.belaartes.ui.comum.MenuHelper;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

public class CriarProdutoActivity extends ProductExtends {

    private byte[] imageBytes;
    private int idProduct;
    private String imgProductBase64;
    private ImageView btnMenu, imgProduto;
    private Button saveProduct;
    private EditText productName, productDescription, productPrice, productAmount;
    private TextView screenTitle;
    private Produto productSelected;

    private ImageButton btn_voltar;

    private void verificarPermissaoGaleria() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_MEDIA_IMAGES}, 100);
            } else {
                abrirGaleria();
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            } else {
                abrirGaleria();
            }
        }
    }

    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, getREQUEST_IMAGE_PICK());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_produto);
        initializeUI();
        setupListeners();
        initMask();

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
                Toast.makeText(CriarProdutoActivity.this, "Buscando por: " + query, Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == getREQUEST_IMAGE_PICK() && resultCode == RESULT_OK && data != null) {
            try {
                Uri imageUri = data.getData();
                if (imageUri == null) {
                    Toast.makeText(this, "Erro: URI é null", Toast.LENGTH_SHORT).show();
                    return;
                }

                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                if (inputStream == null) {
                    Toast.makeText(this, "Erro: inputStream é null", Toast.LENGTH_SHORT).show();
                    return;
                }

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                if (bitmap == null) {
                    Toast.makeText(this, "Erro: bitmap é null", Toast.LENGTH_SHORT).show();
                    return;
                }

                imgProduto.setImageBitmap(bitmap);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
                imageBytes = stream.toByteArray();

                Toast.makeText(this, "Imagem carregada com sucesso!", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                Toast.makeText(this, "Erro ao carregar imagem: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            abrirGaleria();
//        } else {
//            Toast.makeText(this, "Permissão negada para acessar imagens", Toast.LENGTH_SHORT).show();
//        }
//    }
//


    private void initializeUI() {
        this.screenTitle = findViewById(R.id.txtTitulo);
        imgProduto = findViewById(R.id.cadastro_produto_img);
        screenTitle.setText("Cadastrar produtos");
        this.productName = findViewById(R.id.edtNome);
        this.productDescription = findViewById(R.id.edtDescricao);
        this.productPrice = findViewById(R.id.edtPreco);
        this.productAmount = findViewById(R.id.edtQuantidade);
        this.saveProduct = findViewById(R.id.btnSalvar);
        this.btn_voltar =findViewById(R.id.btn_voltar);
        this.productSelected = new Produto();
        this.idProduct = 0;
        this.imgProductBase64 = null;

    }

    private void initMask() {
        this.productPrice.addTextChangedListener(MaskUtils.insertMoneyMask(productPrice));
    }

    private boolean checkDate() {
        if (imageBytes == null) {
            runOnUiThread(() -> {
                Toast.makeText(CriarProdutoActivity.this, "Imagem obrigatoria", Toast.LENGTH_SHORT).show();
            });
            return false;
        }
        if (productName.getText().toString().isEmpty()) {
            productName.setError("Adicione um nome do produto!");
            return false;
        }
        if (productDescription.getText().toString().isEmpty()) {
            productDescription.setError("Adicione uma descrição para o produto");
            return false;
        }
        if (productPrice.getText().toString().isEmpty()) {
            productPrice.setError("Adicione um valor para o produto");
            return false;
        }

        return true;
    }

    protected ProdutoDto saveProductDto() {
        String precoTexto = productPrice.getText().toString().replaceAll("[^\\d,]", "").replace(",", ".");
        BigDecimal price = new BigDecimal(precoTexto);
        return new ProdutoDto(
                productName.getText().toString(),
                productDescription.getText().toString(),
                "Bolsa",
                price,
                convertImageToBase64(imageBytes),
                1
        );
    }

    private void setupListeners() {
        saveProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkDate()) {
                    createProduct(saveProductDto());
                }
            }
        });
        // saveProduct.setOnClickListener(v ->{createProduct(saveProductDto());});
        imgProduto.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, getREQUEST_IMAGE_PICK());
        });
        btn_voltar.setOnClickListener(
                v -> {
                    finish();
                }
        );
    }
}
