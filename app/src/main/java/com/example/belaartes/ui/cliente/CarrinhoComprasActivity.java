package com.example.belaartes.ui.cliente;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.belaartes.R;
import com.example.belaartes.data.model.entities.Produto;
import com.example.belaartes.data.repository.ProdutoRepository;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CarrinhoComprasActivity extends AppCompatActivity {
    private ImageView imgProduct;
    private TextView productName, productDescription, productAmount, productMoney, productSubTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_carrinho_compras);
        initializeUI();
        findCard(2);
    }

    private void initializeUI() {
        this.imgProduct = findViewById(R.id.imgProduto);
        this.productName = findViewById(R.id.txtNomeProduto);
        this.productDescription = findViewById(R.id.txtCorProduto);
        this.productAmount = findViewById(R.id.txtQuantidade);
        this.productMoney = findViewById(R.id.txtPrecoProduto);
        this.productSubTotal = findViewById(R.id.txtSubtotal);
    }

    protected double calculatePrice(List<Produto> listCart) {
        double priceAll = 0D;
        String priceSTR;
        if (listCart.isEmpty()) {
            return 0D;
        }
        for (Produto product : listCart) {
            priceAll = Double.valueOf(String.valueOf(product.getPreco())) * Double.valueOf(String.valueOf(product.getEstoque()));
        }

        return 0D;
    }

    private void convertBitMap(String imgBase64) {
        if (imgBase64 != null && !imgBase64.isEmpty()) {
            byte[] imagemBytes = Base64.decode(imgBase64, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imagemBytes, 0, imagemBytes.length);
            imgProduct.setImageBitmap(bitmap);
        } else {
            imgProduct.setImageResource(R.drawable.ic_launcher_foreground);
        }
    }

    /**
     * @param idCart Recebe o valor do cliente
     *               Deve alterar a configuração para carrinho de com
     */
    private void findCard(int idCart) {
        ProdutoRepository.buscarProdutoPorId(this, idCart, new ProdutoRepository.ProdutoCallbackUnico() {
            @Override
            public void onSuccess(Produto produto) {
                productName.setText(produto.getNome());
                productDescription.setText(produto.getDescricao());
                productAmount.setText(String.valueOf(produto.getEstoque())); // <- altera para a quantidade
                Locale brasil = new Locale("pt", "BR");
                NumberFormat formatoReal = NumberFormat.getCurrencyInstance(brasil);
                productMoney.setText(formatoReal.format(produto.getPreco()));
                convertBitMap(produto.getImagem());
            }

            @Override
            public void onError(String error) {
                runOnUiThread(() -> {
                    Toast.makeText(CarrinhoComprasActivity.this, error, Toast.LENGTH_LONG).show();
                });
            }
        });
    }


}