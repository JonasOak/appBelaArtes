package com.example.belaartes.data.repository;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.belaartes.data.api.VolleySingleton;
import com.example.belaartes.data.model.entities.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {

    private static final String BASE_URL = "http://10.0.2.2:8080/usuarios";

    public interface UsuarioCallback {
        void onSuccess(List<Usuario> usuarios);
        void onError(String errorMessage);
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
}