package com.example.hotels.models;

public class LoginResponse {
    private String token;
    private int id_user;
    private String nom;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getIdUser() {
        return id_user; // Le getter correspond au champ 'id_user'
    }

    public void setIdUser(int id_user) {
        this.id_user = id_user;
    }

    public String getNom() {
        return nom;
    }


    public void setNom(String nom) {
        this.nom = nom;
    }
}
