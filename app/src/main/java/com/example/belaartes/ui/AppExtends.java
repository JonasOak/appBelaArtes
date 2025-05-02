package com.example.belaartes.ui;

import static com.example.belaartes.data.session.ClientSession.clientSession;

import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.belaartes.data.repository.UsuarioRepository;
import com.example.belaartes.data.util.ClientUtil;
import com.example.belaartes.ui.auth.LoginActivity;

public class AppExtends extends AppCompatActivity implements ClientUtil {
    @Override
    public void logout() {
        clientSession = null;
        Intent screenLogin = new Intent(AppExtends.this, LoginActivity.class);
        startActivity(screenLogin);
        finish();
    }

    @Override
    public void disableAccount() {

    }

    @Override
    public void deleteAccount() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AppExtends.this);
        builder.setTitle("Deseja mesmo apagar sua conta?")
                .setMessage("Ao apagar sua conta, o acesso a ela será permanentemente perdido.")
                .setPositiveButton("Confirmar", (dialog, which) -> {
                    if(clientSession != null){
                        UsuarioRepository.deleteAccount(this, clientSession, new UsuarioRepository.DeleteCallback() {
                            @Override
                            public void onSuccess(String response) {
                                runOnUiThread(()->{
                                    logout();
                                    Toast.makeText(AppExtends.this, response, Toast.LENGTH_SHORT).show();
                                });
                            }

                            @Override
                            public void onError(String error) {
                                runOnUiThread(()->{
                                    Toast.makeText(AppExtends.this, error, Toast.LENGTH_SHORT).show();
                                });
                            }
                        });
                    }
                })
                .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                .show();

    }

}
