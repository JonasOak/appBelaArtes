package com.example.belaartes.ui.cliente;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.belaartes.R;

public class CheckoutActivity extends AppCompatActivity {

    private EditText etNome, etCpf, etTelefone, etLogradouro, etNumero, etBairro, etCep, etComplemento;
    private Button btnProcurar;
    private TextView areaCopia, stsCopia;
    private ImageView btnCopia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_checkout);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        createNewElement();
        createNewElement();
    }

    @SuppressLint({"ResourceAsColor", "ResourceType"})
    public void createNewElement() {
//        Criação do bloco principal
        LinearLayout lLayoutFather = new LinearLayout(this);
        LinearLayout.LayoutParams paramsFather = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        paramsFather.setMargins(0, 20, 0, 0);
        lLayoutFather.setLayoutParams(paramsFather);
        lLayoutFather.setOrientation(LinearLayout.HORIZONTAL);
//        lLayoutFather.setBackgroundColor(R.color.rosa_tracejado);
        lLayoutFather.setBackgroundColor(Color.parseColor("#FFB6C1"));
//        lLayoutFather.setId(3); talvez desnecessário

//        Criação do elemento de imagem
        ImageView imageView = new ImageView(this);
        LinearLayout.LayoutParams paramsImg = new LinearLayout.LayoutParams(200, 200);
        paramsImg.setMargins(0, 0, 16, 0);
        imageView.setLayoutParams(paramsImg);
        imageView.setContentDescription("Imagem do produto 3");
        imageView.setImageResource(R.drawable.bolsa);

//        Criação do bloco secundário
        LinearLayout lLayoutChild = new LinearLayout(this);
        LinearLayout.LayoutParams paramsChild = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        paramsChild.setMargins(0, 10, 0, 0);
        lLayoutChild.setLayoutParams(paramsFather);
        lLayoutChild.setOrientation(LinearLayout.VERTICAL);

//        Criação do nome
        TextView textView1 = new TextView(this);
        LinearLayout.LayoutParams paramsText = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        textView1.setLayoutParams(paramsText);
//        textView1.setTextColor(R.color.white);
        textView1.setTextColor(Color.parseColor("#FFFFFF"));
        textView1.setTextSize(15);
        textView1.setText("Nome 3");

//        Criação do preço
        TextView textView2 = new TextView(this);
        textView2.setLayoutParams(paramsText);
//        textView2.setTextColor(R.color.white);
        textView2.setTextColor(Color.parseColor("#FFFFFF"));
        textView2.setTextSize(15);
        textView2.setText("R$");

//        Montagem dos blocos
        LinearLayout listaCompras = findViewById(R.id.listaCompras);
        listaCompras.addView(lLayoutFather);
        lLayoutFather.addView(imageView);
        lLayoutFather.addView(lLayoutChild);
        lLayoutChild.addView(textView1);
        lLayoutChild.addView(textView2);
    }
}