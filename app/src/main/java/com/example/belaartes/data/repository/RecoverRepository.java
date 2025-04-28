package com.example.belaartes.data.repository;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.belaartes.data.api.VolleySingleton;

public class RecoverRepository {
    private static final String BASE_URL = "http://186.247.89.58:8080/recover";

    public interface recoverMail{
        void onSuccess();
        void onError(String error);
    }

    public static void recoverPassword(Context context, String mail, recoverMail callback){
        String url = BASE_URL + "?email="+mail;
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                response -> {
                    callback.onSuccess();
                },
                error -> {
                    String errorMsg = "Erro ao recuperar senha";
                    if (error.networkResponse != null) {
                        errorMsg += " (status: " + error.networkResponse.statusCode + ")";
                    }
                    callback.onError(errorMsg);
                }
        );

        VolleySingleton.getInstance(context).addToRequestQueue(request);

    }



}
