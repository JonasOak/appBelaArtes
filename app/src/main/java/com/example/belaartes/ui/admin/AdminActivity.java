package com.example.belaartes.ui.admin;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.belaartes.R;
import com.example.belaartes.data.model.dto.AdminDto;
import com.example.belaartes.data.repository.AdminRepository;
import com.example.belaartes.ui.comum.MenuHelper;

public class AdminActivity extends AppCompatActivity {

    private TextView countProduct, countClient, countRequestService;
    private Button refreshServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
           initializeUI();
        getAllService();
        setupListeners();
    }

    private void initializeUI() {
        countClient = findViewById(R.id.admin_count_client);
        countProduct = findViewById(R.id.admin_count_product);
        countRequestService = findViewById(R.id.admin_count_request_service);
        refreshServices = findViewById(R.id.btn_admin_home_update);
    }

    private void setupListeners() {
        refreshServices.setOnClickListener(v -> getAllService());
    }

    private void getAllService() {
        AdminRepository.countService(this, new AdminRepository.CountClient() {
            @Override
            public void onSuccess(AdminDto count) {
                countClient.setText(String.valueOf(count.getCountClient()));
                countProduct.setText(String.valueOf(count.getCountProduct()));
                countRequestService.setText(String.valueOf(count.getCountRequestOrders()));
            }

            @Override
            public void onError(String error) {
                Toast.makeText(AdminActivity.this, "Erro ao buscar dados", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Menu superior (ícone de lista)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    // Ação ao clicar no ícone de menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_menu) {
            View anchor = findViewById(R.id.toolbar); // âncora para o PopupMenu
            MenuHelper.mostrarMenu(this, anchor);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

