package com.example.belaartes.ui.user;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.example.belaartes.R;
import com.example.belaartes.data.abstractClass.AppExtends;

public class ActivityUserConfig extends AppExtends {
    private Button btnDelete, btnDisableAccount, btnLogout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_config);
        initializeUi();
        setupListeners();
    }

    private void initializeUi(){
        this.btnDelete = findViewById(R.id.profile_delete_user);
        this.btnDisableAccount = findViewById(R.id.profile_disable_user);
        this.btnLogout = findViewById(R.id.profile_logout_user);

    }

    private void setupListeners() {
        btnLogout.setOnClickListener(v -> showConfirmationDialog(
                "Sair da conta",
                "Tem certeza que deseja sair?",
                this::logout
        ));

        btnDisableAccount.setOnClickListener(v -> showConfirmationDialog(
                "Desativar conta",
                "Deseja realmente desativar sua conta temporariamente?",
                this::disableAccount
        ));

        btnDelete.setOnClickListener(v -> showConfirmationDialog(
                "Excluir conta",
                "Essa ação é irreversível. Tem certeza que deseja excluir sua conta?",
                this::deleteAccount
        ));
    }


}
