package com.example.belaartes.data.model.entities;

import java.io.Serializable;
import java.math.BigDecimal;

public class ItemPedido implements Serializable {
    private int idItemPedido;
    private Pedido pedido;
    private Produto produto;
    private Integer quantidade;
    private BigDecimal precoUnitario;

    public ItemPedido() {
    }

    public ItemPedido( Produto produto, Integer quantidade, BigDecimal precoUnitario) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }

    @Override
    public String toString() {
        return "ItemPedido{" +
                "idItemPedido=" + idItemPedido +
                ", pedido=" + pedido +
                ", produto=" + produto +
                ", quantidade=" + quantidade +
                ", precoUnitario=" + precoUnitario +
                '}';
    }

    public int getIdItemPedido() {
        return idItemPedido;
    }

    public void setIdItemPedido(int idItemPedido) {
        this.idItemPedido = idItemPedido;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }
}
