package com.example.belaartes.data.model.dto;

import java.math.BigDecimal;

public class ProdutoDto {
    private String nome;
    private String descricao;
    private String categoria;
    private BigDecimal preco;
    private String imagem;
    private Integer estoque;

    public ProdutoDto(String nome, String descricao, String categoria, BigDecimal preco, String imagem, Integer estoque) {
        this.nome = nome;
        this.descricao = descricao;
        this.categoria = categoria;
        this.preco = preco;
        this.imagem = imagem;
        this.estoque = estoque;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public String getImagem() {
        return imagem;
    }

    public Integer getEstoque() {
        return estoque;
    }
}
