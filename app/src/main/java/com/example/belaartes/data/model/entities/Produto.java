package com.example.belaartes.data.model.entities;

import java.io.Serializable;
import java.math.BigDecimal;

public class Produto implements Serializable {

    private int idProduto;
    private String nome;
    private String descricao;
    private String categoria;
    private BigDecimal preco;
    private String imagem;
    private int estoque;

    public Produto() {
    }

    public Produto(int idProduto, String nome, String descricao, String categoria, BigDecimal preco, String imagem, int estoque) {
        this.idProduto = idProduto;
        this.nome = nome;
        this.descricao = descricao;
        this.categoria = categoria;
        this.preco = preco;
        this.imagem = imagem;
        this.estoque = estoque;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }
}
