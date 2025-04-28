package com.example.belaartes.data.repository;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.belaartes.data.model.entities.Cliente;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarException;

public class ClienteRepository {

    private static final String BASE_URL = "http://10.0.2.2:8080/produtos";

    public interface ClienteCallback {
        void onSuccess(List<Cliente> clientes);
        void onError(String mensagemErro);
    }

    public interface ClienteCallbackUnico {
        void onSuccess(Cliente cliente);
        void onError(String mensagemErro);
    }

    public interface ClienteDeleteCallback {
        void onSuccess();
        void onError(String mensagemErro);
    }


    public static void criarCliente(Context context, Cliente cliente, final ClienteCallback callback) {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("nome", cliente.getNome());
            jsonBody.put("cpf", cliente.getCpf());
            jsonBody.put("telefone", cliente.getTelefone());
            jsonBody.put("logradouro", cliente.getLogradouro());
            jsonBody.put("numero", cliente.getNumero());
            jsonBody.put("bairro", cliente.getBairro());
            jsonBody.put("cep", cliente.getCep());
            jsonBody.put("complemento", cliente.getComplemento());
            jsonBody.put("usuario", cliente.getUsuario());
        } catch (JSONException e) {
            e.printStackTrace();
            callback.onError("Erro ao criar JSON do cliente");
            return;
        }

//        JsonObjectRequest request = new JsonObjectRequest(
//                Request.Method.POST,
//                BASE_URL,
//                jsonBody
//        );
    }

    public static void getAllClientes(Context context, final ClienteCallback callback) {
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                BASE_URL,
                null,
                response -> {
                    List<Cliente> lista = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            Cliente cliente = new Cliente();
                            cliente.setIdCliente(obj.getInt("id"));
                            cliente.setNome(obj.getString("nome"));
                            cliente.setCpf(obj.getString("cpf"));
                            cliente.setTelefone(obj.getString("telefone"));
                            cliente.setLogradouro(obj.getString("logradouro"));
                            cliente.setNumero(obj.getString("numero"));
                            cliente.setBairro(obj.getString("bairro"));
                            cliente.setCep(obj.getString("cep"));
                            cliente.setComplemento(obj.getString("complemento"));
//                            cliente.setUsuario(obj.getJSONObject("usuario"));

                            lista.add(cliente);
                        } catch (Exception e) {
                            e.printStackTrace();
                            callback.onError("Erro ao converter cliente");
                        }
                    }
                    callback.onSuccess(lista);
                },
                error -> callback.onError("Erro na requisição: " + error.toString())
        );
    }
}
