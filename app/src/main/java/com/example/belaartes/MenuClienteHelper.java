package com.example.belaartes;

import android.content.Context;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

public class MenuClienteHelper {

    public static void mostrarMenu(Context context, View anchor) {
        PopupMenu popupMenu = new PopupMenu(context, anchor);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.dropdown_menu_cliente, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_cliente:
                    Toast.makeText(context, "Minha Conta", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.menu_pedidos:
                    Toast.makeText(context, "Meus Pedidos", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.menu_produtos:
                    Toast.makeText(context, "Início", Toast.LENGTH_SHORT).show();
                    return true;
                default:
                    return false;
            }
        });

        popupMenu.show();
    }
}

