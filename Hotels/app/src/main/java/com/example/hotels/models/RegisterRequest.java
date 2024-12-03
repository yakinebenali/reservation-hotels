package com.example.hotels.models;

public class RegisterRequest {
    private String nom;
    private String email;
    private String password;
    private String numero_telephone;

    public RegisterRequest(String nom, String email, String password, String numero_telephone) {
        this.nom = nom;
        this.email = email;
        this.password = password;
        this.numero_telephone = numero_telephone;
    }

}
