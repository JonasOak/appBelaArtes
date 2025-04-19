package com.example.belaartes.data.model.entities;

import java.io.Serializable;

public class Usuario implements Serializable {

    private int idUsuario;
    private String email;
    private String senhaHash;
    private String cargo;

    public Usuario() {
    }

    // Construtor para novo usuário
    public Usuario(String email, String senhaHash, String cargo) {
        this.email = email;
        this.senhaHash = senhaHash;
        this.cargo = cargo;
    }

    public Usuario(int idUsuario, String email, String senhaHash, String cargo) {
        this.idUsuario = idUsuario;
        this.email = email;
        this.senhaHash = senhaHash;
        this.cargo = cargo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}
