package com.example.belaartes.ui.cliente;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.belaartes.R;
import com.example.belaartes.data.model.entities.Cep;
import com.example.belaartes.data.model.entities.Cliente;
import com.example.belaartes.data.repository.CepRepository;
import com.example.belaartes.data.util.MaskUtils;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterClientActivity extends AppCompatActivity {
    private EditText name, cpf, phone, logradouro, numero, bairro, cep;
    private TextInputLayout tilCep;
    private Button next;
    private ImageButton finishScreen;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_register);
        initializeUi();
        initMask();
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
        this.tilCep = findViewById(R.id.til_cep);
        this.finishScreen = findViewById(R.id.imageView4);
    }

    private void initMask(){
        this.phone.addTextChangedListener(MaskUtils.insertPhoneMask(phone));
        this.cpf.addTextChangedListener(MaskUtils.insertCpfMask(cpf));
    }
    private void setupListeners() {
        finishScreen.setOnClickListener(v->{
            finish();
        });
        next.setOnClickListener(v -> {
            if (checkDate()) {
                Cliente client = new Cliente(
                        name.getText().toString(),
                        cpf.getText().toString(),
                        phone.getText().toString(),
                        logradouro.getText().toString(),
                        numero.getText().toString(),
                        bairro.getText().toString(),
                        cep.getText().toString()
                );
                Intent nextDate = new Intent(RegisterClientActivity.this, RegisterLoginActivity.class);
                nextDate.putExtra("clientRegister", client);
                startActivity(nextDate);
            }
        });

        tilCep.setEndIconOnClickListener(v -> {
            CepRepository.searchCep(RegisterClientActivity.this, cep.getText().toString(), new CepRepository.ViaCepCallback() {
                @Override
                public void onSuccess(Cep searchCep) {
                    if (searchCep != null) {
                        logradouro.setText(searchCep.getLogradouro());
                        bairro.setText(searchCep.getBairro());
                    }
                }

                @Override
                public void onError(String error) {
                    runOnUiThread(() -> Toast.makeText(RegisterClientActivity.this, "Cep não encontrado", Toast.LENGTH_SHORT).show());
                }
            });
        });
    }

    protected boolean checkPersonalInformation() {
        if (name.getText().toString().isEmpty() || name.getText().length() < 10) {
            name.setError("Preencha seu nome completo.");
            return false;
        }
        if (cpf.getText().toString().isEmpty()) {
            cpf.setError("CPF não pode ser vazio");
            return false;

        }

        if (phone.getText().toString().isEmpty() || phone.getText().length() < 8) {
            phone.setError("Telefone não pode ser vazio");
            return false;
        }
        return true;
    }

    protected boolean checkCityInformation() {
        if (logradouro.getText().toString().isEmpty()) {
            logradouro.setError("Logradouro não pode ser vazio");
            return false;
        }

        if (numero.getText().toString().isEmpty()) {
            numero.setError("Número não pode ser vazio");
            return false;
        }

        if (cep.getText().toString().isEmpty()) {
            cep.setError("CEP não pode ser vazio");
            return false;
        }
        return true;
    }

    private boolean checkDate() {
        if(checkPersonalInformation()){
            if(checkCityInformation()){
                return true ;
            }
        }
        return false;
    }

}
