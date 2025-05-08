package com.example.belaartes.ui.comum;

import static com.example.belaartes.data.session.ClientSession.clientSession;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.belaartes.R;
import com.example.belaartes.data.abstractClass.AppExtends;
import com.example.belaartes.ui.admin.AdminActivity;
import com.example.belaartes.ui.admin.AdminOrderActivity;
import com.example.belaartes.ui.admin.AdminProdutosActivity;
import com.example.belaartes.ui.admin.ListaUsuariosActivity;
import com.example.belaartes.ui.auth.LoginActivity;

public class MenuHelper  {

    public static void mostrarMenu(Context context, View anchor) {

        PopupMenu popup = new PopupMenu(context, anchor);
        popup.getMenuInflater().inflate(R.menu.dropdown_menu, popup.getMenu());
        popup.setGravity(Gravity.END);

        popup.setOnMenuItemClickListener(menuItem -> {
            int id = menuItem.getItemId();
            if (id == R.id.menu_home_admin) {
                Intent intent = new Intent(context, AdminActivity.class);
                context.startActivity(intent);
                Toast.makeText(context, "Home selecionado", Toast.LENGTH_SHORT).show();

                return true;
            } else if (id == R.id.menu_produtos) {
                Intent intent = new Intent(context, AdminProdutosActivity.class);
                context.startActivity(intent);
                Toast.makeText(context, "Produtos selecionado", Toast.LENGTH_SHORT).show();
                return true;
            } else if (id == R.id.menu_clientes) {
                Intent intent = new Intent(context, ListaUsuariosActivity.class);
                context.startActivity(intent);
                Toast.makeText(context, "Clientes selecionado", Toast.LENGTH_SHORT).show();
                return true;
            } else if (id == R.id.menu_pedidos) {
                Intent screenOrder = new Intent(context, AdminOrderActivity.class);
                context.startActivity(screenOrder);
                Toast.makeText(context, "Pedidos selecionado", Toast.LENGTH_SHORT).show();
                return true;
            } else if (id == R.id.menu_sair) {
                clientSession = null;
                Intent screenLogin = new Intent(context, LoginActivity.class);
                context.startActivity(screenLogin);
                Toast.makeText(context, "Sair", Toast.LENGTH_SHORT).show();
            }
            return false;
        });

        popup.show();
    }
}

