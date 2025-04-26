package com.example.belaartes.ui.cliente;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.belaartes.R;
import com.example.belaartes.data.model.entities.Cep;
import com.example.belaartes.data.model.entities.Cliente;
import com.example.belaartes.data.repository.CepRepository;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterClientActivity extends AppCompatActivity {
    private EditText name, cpf, phone, logradouro, numero, bairro, cep;
    private Button next, searchCep;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_register);
        initializeUi();
        setupListeners();
    }

    private void initializeUi() {
        this.name = findViewById(R.id.register_name);
        this.cpf = findViewById(R.id.register_cpf);
        this.phone = findViewById(R.id.register_phone);
        this.logradouro = findViewById(R.id.register_city_logradouro);
        this.numero = findViewById(R.id.register_city_numero);
        this.bairro = findViewById(R.id.register_city_bairro);
        this.cep = findViewById(R.id.register_city_cep);
        this.next = findViewById(R.id.btn_register_proximo);
        this.searchCep = findViewById(R.id.btn_search_Cep);
    }

    private void setupListeners() {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkDate()){
                    Cliente client = new Cliente(name.getText().toString(), cpf.getText().toString(), phone.getText().toString(), logradouro.getText().toString(),numero.getText().toString() , bairro.getText().toString(), cep.getText().toString());
                    Intent nextDate = new Intent(RegisterClientActivity.this, RegisterLoginActivity.class);
                    nextDate.putExtra("clientRegister", client);
                    startActivity(nextDate);

                }
            }
        });
        searchCep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CepRepository.searchCep(RegisterClientActivity.this, cep.getText().toString(), new CepRepository.ViaCepCallback() {
                    @Override
                    public void onSuccess(Cep searchCep) {
                        if(searchCep != null){
                            logradouro.setText(searchCep.getLogradouro());
                            bairro.setText(searchCep.getBairro());
                        }
                    }

                    @Override
                    public void onError(String error) {
                        runOnUiThread(()->{
                            Toast.makeText(RegisterClientActivity.this, "Cep não encontrado", Toast.LENGTH_SHORT).show();
                        });
                    }
                });
            }
        });
    }


    private boolean checkDate() {
        if (name.getText() == null) {
            name.setError("Nome não pode ser vazio");
            return false;
        } else if (name.getText().length() <= 10) {
            name.setError("Nome deve ter mais de 10 caracteres");
            return false;
        }

        if (cpf.getText() == null) {
            cpf.setError("CPF não pode ser vazio");
            return false;

        }

        if (phone.getText() == null) {
            phone.setError("Telefone não pode ser vazio");
            return false;
        } else if (phone.getText().length() < 8) {
            phone.setError("Telefone deve ter pelo menos 8 caracteres");
            return false;
        }

        if (logradouro.getText() == null) {
            logradouro.setError("Logradouro não pode ser vazio");
            return false;
        }

        if (numero.getText() == null) {
            numero.setError("Número não pode ser vazio");
            return false;
        }

        if (cep.getText() == null) {
            cep.setError("CEP não pode ser vazio");
            return false;
        }
        return true;
    }

}
