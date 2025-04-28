package com.example.belaartes.ui.cliente;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.belaartes.R;
import com.example.belaartes.data.repository.RecoverRepository;

public class RecoverActivity extends AppCompatActivity {
    private Button btn_recover;
    private EditText emailRecover;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);
        initializeUi();
        setupListeners();
    }

    private void initializeUi() {
        this.btn_recover = findViewById(R.id.btn_recover_password);
        this.emailRecover = findViewById(R.id.edit_recover_password);
    }
    private void setupListeners(){
        btn_recover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkDate()){
                    RecoverRepository.recoverPassword(RecoverActivity.this, emailRecover.getText().toString().toString(), new RecoverRepository.recoverMail() {
                        @Override
                        public void onSuccess() {
                            runOnUiThread(()->{
                                Toast.makeText(RecoverActivity.this, "E-mail enviado com sucesso!", Toast.LENGTH_LONG).show();
                            });
                        }

                        @Override
                        public void onError(String error) {
                            runOnUiThread(()->{
                                Toast.makeText(RecoverActivity.this, error, Toast.LENGTH_LONG).show();
                            });
                        }
                    });
                }
            }
        });
    }
    private boolean checkDate() {
        if (emailRecover.getText().toString().isEmpty() || !emailRecover.getText().toString().contains("@")) {
            emailRecover.setError("Você precisar prencher o campo do email");
            return false;
        }
        return true;
    }


}
