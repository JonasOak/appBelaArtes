package com.example.belaartes.data.repository;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.belaartes.data.api.VolleySingleton;
import com.example.belaartes.data.model.entities.Cliente;

import org.json.JSONException;
import org.json.JSONObject;

public class ClientRepository {
    private static final String BASE_URL = "http://10.0.2.2:8080/clientes";
    // http://10.0.2.2:8080/clientes (localhost)
    // http://186.247.89.58:8080/clientes (servidor Eduardo)

    public interface RegisterCallback {
        void onSuccess(String response);

        void onError(String error);
    }


    public static void register(Context context, Cliente newClient, RegisterCallback callback) {
        String url = BASE_URL;

        JSONObject requestData = new JSONObject();
        try {
            JSONObject requestUser = new JSONObject();
            // Dados da class usuario
            requestUser.put("email", newClient.getUsuario().getEmail());
            requestUser.put("senhaHash", newClient.getUsuario().getSenhaHash());
            requestUser.put("cargo", "CLIENTE");
            // Dados da class cliente
            requestData.put("usuario", requestUser);
            requestData.put("nome", newClient.getNome());
            requestData.put("cpf", newClient.getCpf());
            requestData.put("telefone", newClient.getTelefone());
            requestData.put("logradouro", newClient.getLogradouro());
            requestData.put("numero", newClient.getNumero());
            requestData.put("bairro", newClient.getBairro());
            requestData.put("cep", newClient.getCep());
            requestData.put("complemento", newClient.getComplemento());

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    requestData,
                    response -> {
                        try {
                            String message = response.getString("message");
                            callback.onSuccess(message);
                        } catch (JSONException e) {
                            callback.onError(e.getMessage());
                        }
                    },
                    error -> {
                        String errorMsg = "Erro ao cadastrar cliente";
                        if (error.networkResponse != null) {
                            errorMsg += " (status: " + error.networkResponse.statusCode + ")";
                        }
                        callback.onError(errorMsg);
                    }
            );

            VolleySingleton.getInstance(context).addToRequestQueue(request);

        } catch (JSONException e) {
            callback.onError("Erro ao montar JSON: " + e.getMessage());
        }
    }
}
