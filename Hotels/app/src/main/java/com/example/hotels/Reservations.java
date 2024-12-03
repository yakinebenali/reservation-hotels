package com.example.hotels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Reservations {
    private int id_user;
    private int id_hotel;
    private String nom_hotel;
    private double prix_par_jour;
    private double total;
    private int nbre_jours;
    private String date_reservation;
    private String commentaire;
    private String statut;
    private String methode_paiement;

    // Constructeur
    public Reservations(int id_user, int id_hotel, String nom_hotel, double prix_par_jour,
                        double total, int nbre_jours, String date_reservation, String commentaire,
                        String methode_paiement, String statut) {
        this.id_user = id_user;
        this.id_hotel = id_hotel;
        this.nom_hotel = nom_hotel;
        this.prix_par_jour = prix_par_jour;
        this.total = total;
        this.statut = statut;
        this.nbre_jours = nbre_jours;
        this.date_reservation = date_reservation;
        this.commentaire = commentaire;
        this.methode_paiement = methode_paiement;
    }

    // Getters et Setters
    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_hotel() {
        return id_hotel;
    }

    public void setId_hotel(int id_hotel) {
        this.id_hotel = id_hotel;
    }

    public String getNom_hotel() {
        return nom_hotel;
    }

    public void setNom_hotel(String nom_hotel) {
        this.nom_hotel = nom_hotel;
    }

    public double getPrix_par_jour() {
        return prix_par_jour;
    }

    public void setPrix_par_jour(double prix_par_jour) {
        this.prix_par_jour = prix_par_jour;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getNbre_jours() {
        return nbre_jours;
    }

    public void setNbre_jours(int nbre_jours) {
        this.nbre_jours = nbre_jours;
    }

    public String getDate_reservation() {
        try {

            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            inputFormat.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));

            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            outputFormat.setTimeZone(java.util.TimeZone.getDefault());


            Date date = inputFormat.parse(this.date_reservation);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();

            return this.date_reservation;
        }
    }

    public void setDate_reservation(String date_reservation) {
        this.date_reservation = date_reservation;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getMethode_paiement() {
        return methode_paiement;
    }

    public void setMethode_paiement(String methode_paiement) {
        this.methode_paiement = methode_paiement;
    }
}
