package com.example.belaartes.ui.cliente;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;

import com.example.belaartes.R;
import com.example.belaartes.adapters.ProdutoCarouselAdapter;
import com.example.belaartes.data.model.entities.Produto;
import com.example.belaartes.data.repository.ProdutoRepository;
import com.example.belaartes.ui.comum.BaseClienteActivity;;import java.util.List;

public class HomeClienteActivity extends BaseClienteActivity {

    private ViewPager2 viewPagerProdutos;
    private Handler handler = new Handler();
    private Runnable runnable;
    private int currentPage = 0;
    private ProdutoCarouselAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_cliente);

        viewPagerProdutos = findViewById(R.id.viewPagerProdutos);

        carregarProdutos();
    }

    private void carregarProdutos() {
        ProdutoRepository.getAllProdutos(this, new ProdutoRepository.ProdutoCallback() {
            @Override
            public void onSuccess(List<Produto> produtos) {
                adapter = new ProdutoCarouselAdapter(HomeClienteActivity.this, produtos);
                viewPagerProdutos.setAdapter(adapter);

                iniciarAutoSlide(); // Agora funciona certinho
            }

            @Override
            public void onError(String error) {
                Toast.makeText(HomeClienteActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void iniciarAutoSlide() {
        runnable = new Runnable() {
            @Override
            public void run() {
                if (adapter != null) {
                    if (currentPage == adapter.getItemCount()) {
                        currentPage = 0; // Se chegar no final, volta pro começo
                    }
                    viewPagerProdutos.setCurrentItem(currentPage++, true);
                    handler.postDelayed(this, 5000); // Troca a cada 5 segundos
                }
            }
        };
        handler.postDelayed(runnable, 5000); // começa depois de 5 segundos
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}


