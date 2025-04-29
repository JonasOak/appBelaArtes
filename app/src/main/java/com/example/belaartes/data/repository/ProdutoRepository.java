package com.example.belaartes.data.repository;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.belaartes.data.api.VolleySingleton;
import com.example.belaartes.data.model.entities.Produto;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProdutoRepository {

    private static final String BASE_URL = "http://186.247.89.58:8080/produtos";
    // http://10.0.2.2:8080/produtos (localhost)
    // http://186.247.89.58:8080/produtos (servidor Eduardo)
    public interface ProdutoCallback {
        void onSuccess(List<Produto> produtos);
        void onError(String mensagemErro);
    }

    public interface ProdutoCallbackUnico {
        void onSuccess(Produto produto);
        void onError(String error);
    }

    public interface ProdutoDeleteCallback {
        void onSuccess();
        void onError(String mensagemErro);
    }


    public static void criarProduto(Context context, Produto produto, final ProdutoCallback callback) {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("nome", produto.getNome());
            jsonBody.put("descricao", produto.getDescricao());
            jsonBody.put("categoria", produto.getCategoria());
            jsonBody.put("preco", produto.getPreco().toString());
            jsonBody.put("imagemBase64", produto.getImagem());
            jsonBody.put("estoque", produto.getEstoque());
        } catch (JSONException e) {
            e.printStackTrace();
            callback.onError("Erro ao criar JSON do produto");
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                BASE_URL,
                jsonBody,
                response -> getAllProdutos(context, callback), // atualiza após cadastrar
                error -> callback.onError("Erro ao criar produto: " + error.toString())
        );
        VolleySingleton.getInstance(context).addToRequestQueue(request);
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
                          //  produto.setEstoque(obj.getInt("estoque"));

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

    public static void buscarProdutoPorId(Context context, int id, final ProdutoCallbackUnico callback) {
        String url = BASE_URL + "/" + id;
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        Produto produto = new Produto();
                        produto.setIdProduto(response.getInt("id"));
                        produto.setNome(response.getString("nome"));
                        produto.setDescricao(response.getString("descricao"));
                        produto.setCategoria(response.getString("categoria"));
                        produto.setPreco(new BigDecimal(response.getString("preco")));
                        produto.setImagem(response.getString("imagemBase64"));
                        produto.setEstoque(response.getInt("estoque"));

                        callback.onSuccess(produto);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        callback.onError("Erro ao processar resposta do produto");
                    }
                },
                error -> callback.onError("Erro ao buscar produto: " + error.toString())
        );
        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }

    public static void atualizarProduto(Context context, Produto produto, final ProdutoCallback callback) {
        String url = BASE_URL + "/" + produto.getIdProduto();
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("nome", produto.getNome());
            jsonBody.put("descricao", produto.getDescricao());
            jsonBody.put("categoria", produto.getCategoria());
            jsonBody.put("preco", produto.getPreco().toString());
            jsonBody.put("imagemBase64", produto.getImagem());
            jsonBody.put("estoque", produto.getEstoque());
        } catch (JSONException e) {
            e.printStackTrace();
            callback.onError("Erro ao criar JSON para atualizar");
            return;
        }
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                jsonBody,
                response -> getAllProdutos(context, callback), // atualiza após alterar
                error -> callback.onError("Erro ao atualizar produto: " + error.toString())
        );
        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }

    public static void deleteProduto(Context context, int id, final ProdutoDeleteCallback callback) {
        String url = BASE_URL + "/" + id;

        StringRequest request = new StringRequest(
                Request.Method.DELETE,
                url,
                response -> callback.onSuccess(),
                error -> callback.onError("Erro ao deletar produto: " + error.toString())
        );

        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }
}
