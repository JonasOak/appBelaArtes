package com.example.belaartes.ui.admin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.belaartes.R;
import com.example.belaartes.data.model.dto.AdminDto;
import com.example.belaartes.data.repository.AdminRepository;

public class AdminActivity extends AppCompatActivity {
    private EditText countProduct, countClient, countRequestService;
    private Button refreshServices;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        initializeUI();
        getAllService();
        setupListeners();

    }

    private void initializeUI(){
        this.countClient = findViewById(R.id.admin_count_client);
        this.countProduct = findViewById(R.id.admin_count_product);
        this.countRequestService = findViewById(R.id.admin_count_request_service);
        this.refreshServices = findViewById(R.id.btn_admin_home_update);
    }

    private void setupListeners(){
        refreshServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllService();
            }
        });
    }


    private void getAllService(){
        AdminRepository.countService(this, new AdminRepository.CountClient() {
            @Override
            public void onSuccess(AdminDto count) {
                countClient.setText(String.valueOf(count.getCountClient()));
                countProduct.setText(String.valueOf(count.getCountProduct()));
                countRequestService.setText(String.valueOf(count.getCountRequestOrders()));
            }

            @Override
            public void onError(String error) {

            }
        });
    }
}
