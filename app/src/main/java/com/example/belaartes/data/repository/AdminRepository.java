package com.example.belaartes.data.repository;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.belaartes.data.api.VolleySingleton;
import com.example.belaartes.data.model.entities.AdminDto;

import org.json.JSONException;
import org.json.JSONObject;

public class AdminRepository {
    private static final String BASE_URL = "http://186.247.89.58:8080/admin";

    public interface CountClient {
        void onSuccess(AdminDto count);

        void onError(String error);
    }

    public static void countService(Context context, CountClient callback) {
        String url = BASE_URL;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                AdminDto adminDto = new AdminDto(
                        response.getLong("countClient"),
                        response.getLong("countProduct"),
                        response.getLong("countRequestOrders")
                );
                callback.onSuccess(adminDto);
            } catch (JSONException e) {
                e.getMessage();
            }
        },
                error -> callback.onError("Erro na requisição: " + error.toString())

        );
        VolleySingleton.getInstance(context).addToRequestQueue(request);


    }


}
