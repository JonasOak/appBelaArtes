package com.example.belaartes.ui.admin;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;


import com.example.belaartes.data.model.dto.ProdutoDto;
import com.example.belaartes.data.model.entities.Produto;
import com.example.belaartes.data.repository.ProdutoRepository;
import com.example.belaartes.data.util.GalleryUtils;
import com.example.belaartes.data.util.MaskUtils;
import com.example.belaartes.ui.comum.MenuHelper;
import com.example.belaartes.R;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AtualizarProdutoActivity extends GalleryUtils {
    private byte[] imageBytes;

    private int idProduct;
    private String imgProductBase64;
    private ImageView btnMenu, imgProduto;
    private Button saveProduct;
    private ImageButton btnReturn;
    private EditText productName, productDescription, productPrice, productAmount;
    private TextView screenTitle;
    private Produto productSelected;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_produto);
        initializeUI();
        fillInTheField();
        setupListeners();
        initMask();

    }
    private void initMask(){
        this.productPrice.addTextChangedListener(MaskUtils.insertMoneyMask(productPrice));
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

    private void initializeUI() {
        this.screenTitle = findViewById(R.id.txtTitulo);
        imgProduto = findViewById(R.id.cadastro_produto_img);
        screenTitle.setText("Atualizar produtos");
        this.productName = findViewById(R.id.edtNome);
        this.productDescription = findViewById(R.id.edtDescricao);
        this.productPrice = findViewById(R.id.edtPreco);
        this.productAmount = findViewById(R.id.edtQuantidade);
        this.btnReturn = findViewById(R.id.btn_voltar);
        this.saveProduct = findViewById(R.id.btnSalvar);
        this.productSelected = new Produto();
        this.idProduct = 0;



    }



    private void exibirImagemBase64(String base64, ImageView imageView) {
        if (base64 != null && !base64.isEmpty()) {
            try {
                byte[] decodedBytes = Base64.decode(base64, Base64.NO_WRAP);
                Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                imgProduto.setImageBitmap(decodedBitmap);
            } catch (Exception e) {
                Toast.makeText(this, "Erro ao exibir imagem: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public Bitmap decodeBase64ToBitmap(String base64Image) {
        byte[] decodedBytes = Base64.decode(base64Image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }


    private void fillInTheField() {
        Intent getProductSave = getIntent();
        this.productSelected = (Produto) getProductSave.getSerializableExtra("produto");
        if (productSelected != null) {
            this.idProduct = productSelected.getIdProduto();
            this.productName.setText(productSelected.getNome());
            this.productDescription.setText(productSelected.getDescricao());
            NumberFormat formatoReal = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
            this.productPrice.setText(formatoReal.format(productSelected.getPreco()));
            this.productAmount.setText(String.valueOf(productSelected.getEstoque()));
            this.imgProductBase64 = productSelected.getImagem();
            if (productSelected.getImagem() != null && !productSelected.getImagem().isEmpty()) {
                Bitmap imagemBitmap = decodeBase64ToBitmap(productSelected.getImagem());
                imgProduto.setImageBitmap(imagemBitmap);
                imageBytes = Base64.decode(imgProductBase64, Base64.DEFAULT);
            } else {
                Log.d("Produto", "Imagem está vazia ou nula");
            }
        }

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
                Integer.valueOf(productAmount.getText().toString())
        );
    }


    private void setupListeners() {
        btnReturn.setOnClickListener(v->{finish();});
        //Abri galeria
        imgProduto.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, getREQUEST_IMAGE_PICK());

        });
        saveProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProdutoDto productUpdate = saveProductDto();
                productUpdate.setIdProduto(productSelected.getIdProduto());
                ProdutoRepository.atualizarProduto(AtualizarProdutoActivity.this, saveProductDto(),productSelected.getIdProduto(), new ProdutoRepository.ProdutoCallback() {
                    public void onSuccess(List<Produto> produtos) {
                        runOnUiThread(()->{
                            Toast.makeText(AtualizarProdutoActivity.this, "Produto atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                        });

                    }

                    @Override
                    public void onError(String mensagemErro) {
                        runOnUiThread(()->{
                            Toast.makeText(AtualizarProdutoActivity.this, "Erro ao atualizar um produto!", Toast.LENGTH_SHORT).show();
                        });
                    }
                });
            }
        });


//        saveProduct.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Produto saveProduct = new Produto(productSelected.getIdProduto(), productName.getText().toString(), productDescription.getText().toString(), productSelected.getCategoria(), new BigDecimal(productPrice.getText().toString()), productSelected.getImagem(), Integer.valueOf(productAmount.getText().toString()));
//
//                ProdutoRepository.atualizarProduto(AtualizarProdutoActivity.this, saveProduct, new ProdutoRepository.ProdutoCallback() {
//                    @Override
//                    public void onSuccess(List<Produto> produtos) {
//                        runOnUiThread(() -> {
//                            Toast.makeText(AtualizarProdutoActivity.this, "Produto atualizado com sucesso!", Toast.LENGTH_SHORT).show();
//                        });
//                    }
//
//                    @Override
//                    public void onError(String mensagemErro) {
//                        runOnUiThread(() -> {
//                            Toast.makeText(AtualizarProdutoActivity.this, "Erro ao atualizar produto!", Toast.LENGTH_SHORT).show();
//                        });
//                    }
//                });
//            }
//        });


    }

}
