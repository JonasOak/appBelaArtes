package com.example.belaartes.data.model.entities;

public class Cliente {

    private int idCliente;
    private String nome;
    private String cpf;
    private String telefone;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cep;
    private String complemento;
    private Usuario usuario;

    public Cliente() {
    }

    public Cliente(int idCliente, String nome, String cpf, String telefone, String logradouro, String numero, String bairro, String cep, String complemento, Usuario usuario) {
        this.idCliente = idCliente;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.cep = cep;
        this.complemento = complemento;
        this.usuario = usuario;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public Usuario getUsuario() {return usuario;}

    public void setUsuario(Usuario usuario) {this.usuario = usuario;}
}