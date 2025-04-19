package com.example.belaartes;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

public class MenuHelper {

    public static void mostrarMenu(Context context, View anchor) {
        PopupMenu popup = new PopupMenu(context, anchor);
        popup.getMenuInflater().inflate(R.menu.dropdown_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(menuItem -> {
            int id = menuItem.getItemId();
            if (id == R.id.menu_produtos) {
                Toast.makeText(context, "Produtos selecionado", Toast.LENGTH_SHORT).show();
                return true;
            } else if (id == R.id.menu_clientes) {
                Toast.makeText(context, "Clientes selecionado", Toast.LENGTH_SHORT).show();
                return true;
            } else if (id == R.id.menu_pedidos) {
                Toast.makeText(context, "Pedidos selecionado", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });

        popup.show();
    }
}
