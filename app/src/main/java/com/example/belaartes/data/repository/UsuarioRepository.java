package com.example.belaartes.data.repository;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.belaartes.data.api.VolleySingleton;
import com.example.belaartes.data.model.entities.Cliente;
import com.example.belaartes.data.model.entities.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {

    private static final String BASE_URL = "http://186.247.89.58:8080/usuarios";
    // http://10.0.2.2:8080/usuarios (localhost)
    // http://186.247.89.58:8080/usuarios (servidor Eduardo)

    public interface UsuarioCallback {
        void onSuccess(List<Usuario> usuarios);

        void onError(String errorMessage);
    }

    public interface LoginCallback {
        void onSuccess(Usuario usuario);

        void onError(String errorMessage);
    }

    public interface ClientLoginCallback {
        void onSuccess(Cliente client);

        void onError(String errorMessage);
    }

    public interface DeleteCallback {
        void onSuccess(String response);

        void onError(String error);
    }
    public interface DisableCallback {
        void onSuccess(String response);

        void onError(String error);
    }
    public static void Authenticate(Context context, String email, String senha, ClientLoginCallback callback) {
        String url = BASE_URL + "/login";

        JSONObject loginData = new JSONObject();
        try {
            loginData.put("email", email);
            loginData.put("senha", senha);
        } catch (JSONException e) {
            callback.onError("Erro ao criar JSON de login");
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, loginData,
                response -> {
                    try {

                        Cliente client = new Cliente(
                                response.getInt("idCliente"),
                                response.getString("nome"),
                                response.getString("cpf"),
                                response.getString("telefone"),
                                response.getString("logradouro"),
                                response.getString("numero"),
                                response.getString("bairro"),
                                response.getString("cep"),
                                response.getString("complemento")
                        );
                        JSONObject usuarioJson = response.getJSONObject("usuario");

                        Usuario dateUser = new Usuario(
                                usuarioJson.getInt("idUsuario"),
                                usuarioJson.getString("email"),
                                usuarioJson.getString("senhaHash"),
                                usuarioJson.getString("cargo")
                        );
                        client.setUsuario(dateUser);
                        callback.onSuccess(client);
                    } catch (JSONException e) {
                        callback.onError("Erro ao interpretar resposta do servidor");
                    }
                },
                error -> {
                    if (error.networkResponse != null && error.networkResponse.statusCode == 401) {
                        String errorMessage;
                        try {
                            errorMessage = new String(error.networkResponse.data, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            errorMessage = "Erro de autenticação";
                        }
                        callback.onError(errorMessage);
                    } else {
                        callback.onError("Erro ao conectar com o servidor");
                    }

                }
        );

        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }


    public static void login(Context context, String email, String senha, LoginCallback callback) {
        String url = BASE_URL + "/login";

        JSONObject loginData = new JSONObject();
        try {
            loginData.put("email", email);
            loginData.put("senha", senha);
        } catch (JSONException e) {
            callback.onError("Erro ao criar JSON de login");
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, loginData,
                response -> {
                    try {
                        int idUsuario = response.getInt("idUsuario");
                        String emailResponse = response.getString("email");
                        String senhaHash = response.getString("senhaHash");
                        String cargo = response.getString("cargo");
                        Usuario usuario = new Usuario(idUsuario, emailResponse, senhaHash, cargo);
                        callback.onSuccess(usuario);
                    } catch (JSONException e) {
                        callback.onError("Erro ao interpretar resposta do servidor");
                    }
                },
                error -> {
                    if (error.networkResponse != null && error.networkResponse.statusCode == 401) {
                        callback.onError("Email ou senha inválidos");
                    } else {
                        callback.onError("Erro ao conectar com o servidor");
                    }
                }
        );

        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }

    public static void getAllUsuarios(Context context, UsuarioCallback callback) {
        String url = BASE_URL;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    List<Usuario> usuarios = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            Usuario usuario = new Usuario();
                            usuario.setIdUsuario(obj.getInt("idUsuario"));
                            usuario.setEmail(obj.getString("email"));
                            usuario.setSenhaHash(obj.getString("senhaHash"));
                            usuario.setCargo(obj.getString("cargo"));
                            usuarios.add(usuario);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    callback.onSuccess(usuarios);
                },
                error -> callback.onError(error.toString())
        );
        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }


    /**
     * Metado para apagar conta
     */
    public static void deleteAccount(Context context, Cliente client, DeleteCallback callback) {
        String url = BASE_URL + "/" + client.getIdCliente();
        if (client.getIdCliente() == 0) {
            return;
        } else {
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.DELETE,
                    url,
                    null,
                    response -> {
                        callback.onSuccess("Conta deletada com sucesso!");
                    },
                    error -> {
                        String errorMsg = "Erro ao excluir conta!";
                        if (error.networkResponse != null) {
                            errorMsg += " (status: " + error.networkResponse.statusCode + ")";
                        }
                        callback.onError(errorMsg);
                    }
            );
            VolleySingleton.getInstance(context).addToRequestQueue(request);
        }
    }

    /**
     * Metado para desativar conta
     */
    public static void disableAccount(Context context, Integer clientId, DisableCallback callback) {
        String url = BASE_URL + "/disable/" + clientId;
        if (clientId == 0) {
            return;
        } else {
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    null,
                    response -> {
                        callback.onSuccess("Conta desativada com sucesso!");
                    },
                    error -> {
                        String errorMsg = "Erro ao desativar conta!";
                        if (error.networkResponse != null) {
                            errorMsg += " (status: " + error.networkResponse.statusCode + ")";
                        }
                        callback.onError(errorMsg);
                    }
            );
            VolleySingleton.getInstance(context).addToRequestQueue(request);
        }
    }
}