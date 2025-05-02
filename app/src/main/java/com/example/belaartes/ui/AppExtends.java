package com.example.belaartes.ui;

import static com.example.belaartes.data.session.ClientSession.clientSession;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.belaartes.data.util.ClientUtil;
import com.example.belaartes.ui.auth.LoginActivity;

public class AppExtends extends AppCompatActivity implements ClientUtil {
    @Override
    public void logout() {
        clientSession = null;
        if(clientSession == null){
            Intent screenLogin = new Intent(AppExtends.this, LoginActivity.class);
            startActivity(screenLogin);
            finish();
        }
    }
}
