package com.example.hotels;

import com.google.gson.annotations.SerializedName;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Hotel {
    @SerializedName("id_hotel")
    private int id_hotel;

    @SerializedName("nom")
    private String nom;

    @SerializedName("adresse")
    private String adresse;

    @SerializedName("ville")
    private String ville;

    @SerializedName("image_url")
    private String imageUrl;
    @SerializedName("logo_url")
    private String logoUrl;

    @SerializedName("heure_ouverture")
    private String heureOuverture;

    @SerializedName("heure_fermeture")
    private String heureFermeture;

    @SerializedName("numero_telephone")
    private String numeroTelephone;

    @SerializedName("email")
    private String email;

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    // Getters et Setters
    public int getId() {
        return id_hotel;
    }

    public void setId(int id_hotel) {
        this.id_hotel = id_hotel;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }
    public String getLogoUrl() {
        return logoUrl;
    }
    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getHeureOuverture() {
        return formatTime(heureOuverture);
    }

    public void setHeureOuverture(String heureOuverture) {
        this.heureOuverture = heureOuverture;
    }

    public String getHeureFermeture() {
        return formatTime(heureFermeture);
    }

    public void setHeureFermeture(String heureFermeture) {
        this.heureFermeture = heureFermeture;
    }

    public String getNumeroTelephone() {
        return numeroTelephone;
    }

    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String formatTime(String time) {
        try {
            LocalTime localTime = LocalTime.parse(time);
            return localTime.format(TIME_FORMATTER);
        } catch (Exception e) {
            System.err.println("Erreur lors du formatage de l'heure : " + time);
            return time; // Retourne l'heure brute en cas d'erreur
        }
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id_hotel=" + id_hotel +
                ", nom='" + nom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", ville='" + ville + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                ", heureOuverture='" + getHeureOuverture() + '\'' +
                ", heureFermeture='" + getHeureFermeture() + '\'' +
                ", numeroTelephone='" + numeroTelephone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
