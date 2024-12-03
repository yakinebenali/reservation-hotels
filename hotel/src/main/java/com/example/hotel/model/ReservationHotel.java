package com.example.hotel.model;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity
public class ReservationHotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_reservation;

    @Column(name = "idUser")
    private Integer idUser;

    @Column(name = "idHotel")
    private Integer idHotel;

    private String nomHotel;

    @Column(name = "prixParJour")
    private BigDecimal prixParJour;

    private BigDecimal total;
    private Integer nbreJours;
    private LocalDateTime dateReservation;
    private String statut;
    private String commentaire;
    private String methodePaiement;

    
    public Integer getIdReservation() {
        return id_reservation;
    }

    public void setIdReservation(Integer id_reservation) {
        this.id_reservation = id_reservation;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdHotel() {
        return idHotel;
    }

    public void setIdHotel(Integer idHotel) {
        this.idHotel = idHotel;
    }

    public String getNomHotel() {
        return nomHotel;
    }

    public void setNomHotel(String nomHotel) {
        this.nomHotel = nomHotel;
    }

    public BigDecimal getPrixParJour() {
        return prixParJour;
    }

    public void setPrixParJour(BigDecimal prixParJour) {
        this.prixParJour = prixParJour;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Integer getNbreJours() {
        return nbreJours;
    }

    public void setNbreJours(Integer nbreJours) {
        this.nbreJours = nbreJours;
    }

    public LocalDateTime getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(LocalDateTime dateReservation) {
        this.dateReservation = dateReservation;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getMethodePaiement() {
        return methodePaiement;
    }

    public void setMethodePaiement(String methodePaiement) {
        this.methodePaiement = methodePaiement;
    }
}
