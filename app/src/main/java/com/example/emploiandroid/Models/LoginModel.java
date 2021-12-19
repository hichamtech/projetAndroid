package com.example.emploiandroid.Models;

public class LoginModel {
    private  int id;
    private String role;
    private String token;
    private String status;
    private String  email;
    private  String nom;

    public LoginModel(String email,String role,String status, String token) {
        this.id = id;
        this.role = role;
        this.token = token;
        this.status = status;
        this.email = email;
    }
    public LoginModel(int id, String nom, String email) {
        this.id = id;
        this.nom = nom;
        this.email = email;

    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
