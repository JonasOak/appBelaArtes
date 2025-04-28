package com.example.belaartes.data.repository;

import static com.example.belaartes.data.session.CheckoutSession.listCart;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.belaartes.data.api.VolleySingleton;
import com.example.belaartes.data.model.entities.ItemPedido;
import com.example.belaartes.data.session.ClientSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class OrderRepository {
    private static final String BASE_URL = "http://186.247.89.58:8080/v1/pedidos";

    public interface RegisterOrderCallback {
        void onSuccess(String order);

        void onError(String error);
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
                        callback.onError(errorMsg);
                    }
            );

            // Adiciona o request à fila
            VolleySingleton.getInstance(context).addToRequestQueue(request);

        } catch (Exception e) {
            callback.onError(e.getMessage());
        }
    }

}
