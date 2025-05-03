package com.example.belaartes.data.abstractClass;

import android.widget.Toast;

import com.example.belaartes.data.model.dto.ProdutoDto;
import com.example.belaartes.data.repository.ProdutoRepository;
import com.example.belaartes.data.util.ProductUtil;


public class ProductExtends extends AppExtends implements ProductUtil {
    @Override
    public void createProduct(ProdutoDto product) {
      ProdutoRepository.criarProduto(ProductExtends.this, product, new ProdutoRepository.CriaProdutoCallback() {
          @Override
          public void onSuccess(String response) {
              runOnUiThread(() -> {
                  Toast.makeText(ProductExtends.this, response, Toast.LENGTH_SHORT).show();
              });
          }

          @Override
          public void onError(String mensagemErro) {
              runOnUiThread(() -> {
                  Toast.makeText(ProductExtends.this, mensagemErro, Toast.LENGTH_SHORT).show();
              });
          }
      });
    }
}
