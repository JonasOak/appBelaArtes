package com.example.belaartes.data.repository;

import static com.example.belaartes.data.session.CheckoutSession.listCart;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.belaartes.data.api.VolleySingleton;
import com.example.belaartes.data.model.entities.Cliente;
import com.example.belaartes.data.model.entities.ItemPedido;
import com.example.belaartes.data.model.entities.Produto;
import com.example.belaartes.data.session.ClientSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository {
    private static final String BASE_URL = "http://186.247.89.58:8080/v1/pedidos";

    public interface RegisterOrderCallback {
        void onSuccess(String order);

        void onError(String error);
    }

    public interface OrdersProdutoCallback {
        void onSuccess(List<Produto> produtos);

        void onError(String mensagemErro);
    }

    public interface OrdersClientsCallBack {
        void onSuccess(List<Cliente> client);

        void onError(String mensagemErro);
    }


    public static void getAllProdutos(Context context, final OrdersProdutoCallback callback) {
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
                            produto.setIdProduto(obj.getInt("idProduto"));
                            produto.setNome(obj.getString("nome"));
                            produto.setDescricao(obj.getString("descricao"));
                            produto.setCategoria(obj.getString("categoria"));
                            produto.setPreco(new BigDecimal(obj.getString("preco")));
                            produto.setImagem(obj.getString("imagem"));
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

    public static void getAllClientOrders(Context context, final OrdersClientsCallBack callback) {
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                BASE_URL + "/clients",
                null,
                response -> {
                    List<Cliente> lista = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            Cliente client = new Cliente(obj.getInt("idCliente"), obj.getString("nome"), obj.getString("cpf"), obj.getString("cep"), obj.getString("logradouro"), obj.getString("numero"), obj.getString("bairro"), obj.getString("cep"), obj.getString("complemento"));
                            lista.add(client);
                        } catch (Exception e) {
                            e.printStackTrace();
                            callback.onError("Erro ao converter cliente");
                        }
                    }
                    callback.onSuccess(lista);
                },
                error -> callback.onError("Erro na requisição: " + error.toString())
        );

        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }

    public static void registerOrder(Context context, RegisterOrderCallback callback) {
        try {
            JSONArray requestArray = new JSONArray();
            for (ItemPedido orders : listCart) {
                JSONObject clientObject = new JSONObject();
                clientObject.put("idClient", ClientSession.clientSession.getIdCliente());
                clientObject.put("idProduct", orders.getProduto().getIdProduto());
                requestArray.put(clientObject);
            }

            JsonArrayRequest request = new JsonArrayRequest(
                    Request.Method.POST,
                    BASE_URL,
                    requestArray,
                    response -> {
                        callback.onSuccess("Pedidos registrados com sucesso!");
                    },
                    error -> {
                        String errorMsg = "Erro ao registrar pedidos";
                        if (error.networkResponse != null) {
                            errorMsg += " (status: " + error.networkResponse.statusCode + ")";
                        }
//                        callback.onError(errorMsg);
                    }
            );

            // Adiciona o request à fila
            VolleySingleton.getInstance(context).addToRequestQueue(request);

        } catch (Exception e) {
            callback.onError(e.getMessage());
        }
    }

}
