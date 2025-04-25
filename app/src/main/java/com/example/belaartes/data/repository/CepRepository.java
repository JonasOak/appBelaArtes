package com.example.belaartes.data.repository;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.belaartes.data.api.VolleySingleton;
import com.example.belaartes.data.model.entities.Cep;

import org.json.JSONException;

public class CepRepository {
    private static final String BASE_URL = "https://viacep.com.br/ws/";

    public interface ViaCepCallback{
        void onSuccess(Cep searchCep);
        void onError(String error);
    }


    public static void searchCep(Context context, String cep, final ViaCepCallback callback){
        String url = BASE_URL+cep+"/json/";
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        Cep cepFound = new Cep();
                        cepFound.setCep(response.getString("cep"));
                        cepFound.setBairro(response.getString("bairro"));
                        cepFound.setLocalidade(response.getString("localidade"));
                        cepFound.setUf(response.getString("uf"));
                        cepFound.setEstado(response.getString("estado"));
                        cepFound.setLogradouro(response.getString("logradouro"));
                        callback.onSuccess(cepFound);

                    } catch (JSONException e){
                        callback.onError(e.getMessage());
                    }
                },
                error -> callback.onError("Cep não encontrado")
        );
        VolleySingleton.getInstance(context).addToRequestQueue(request);

    }


}
