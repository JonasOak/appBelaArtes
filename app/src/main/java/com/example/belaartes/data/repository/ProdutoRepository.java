package com.example.belaartes.data.repository;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.belaartes.data.api.VolleySingleton;
import com.example.belaartes.data.model.entities.Produto;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProdutoRepository {

    private static final String BASE_URL = "http://10.0.2.2:8080/produtos";

    public interface ProdutoCallback {
        void onSuccess(List<Produto> produtos);
        void onError(String mensagemErro);
    }

    public static void getAllProdutos(Context context, final ProdutoCallback callback) {
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                BASE_URL,
                null,
                response -> {
                    List<Produto> lista = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            Produto produto = new Produto();
                            produto.setIdProduto(obj.getInt("id"));
                            produto.setNome(obj.getString("nome"));
                            produto.setDescricao(obj.getString("descricao"));
                            produto.setCategoria(obj.getString("categoria"));
                            produto.setPreco(new BigDecimal(obj.getString("preco")));
                            produto.setImagem(obj.getString("imagemBase64"));
                            produto.setEstoque(obj.getInt("estoque"));

                            lista.add(produto);
                        } catch (Exception e) {
                            e.printStackTrace();
                            callback.onError("Erro ao converter produto");
                        }
                    }
                    callback.onSuccess(lista);
                },
                error -> callback.onError("Erro na requisição: " + error.toString())
        );

        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }
}
