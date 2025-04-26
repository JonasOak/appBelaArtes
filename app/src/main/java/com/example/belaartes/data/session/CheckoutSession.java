package com.example.belaartes.data.session;

import com.example.belaartes.data.model.entities.ItemPedido;

import java.util.ArrayList;
import java.util.List;

public class CheckoutSession {
    public static List<ItemPedido> listCart = new ArrayList<>();

    public static List<ItemPedido> getListCart() {
        return listCart;
    }

    public static void setListCart(List<ItemPedido> listCart) {
        CheckoutSession.listCart = listCart;
    }
}
