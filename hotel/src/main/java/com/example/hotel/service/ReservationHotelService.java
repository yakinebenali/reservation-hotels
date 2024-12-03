package com.example.hotel.service;

import com.example.hotel.model.ReservationHotel;
import com.example.hotel.repository.ReservationHotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationHotelService {

    @Autowired
    private ReservationHotelRepository reservationHotelRepository;

    public ReservationHotelService(ReservationHotelRepository reservationHotelRepository) {
        this.reservationHotelRepository = reservationHotelRepository;
    }

    public List<ReservationHotel> getAllReservations() {
        return reservationHotelRepository.findAll();
    }

    public void updateReservation(ReservationHotel updatedReservation) {
     
        Optional<ReservationHotel> existingReservationOpt = reservationHotelRepository.findById(updatedReservation.getIdReservation());

        if (existingReservationOpt.isPresent()) {
            ReservationHotel existingReservation = existingReservationOpt.get();
            existingReservation.setStatut(updatedReservation.getStatut());
            existingReservation.setCommentaire(updatedReservation.getCommentaire());
            existingReservation.setMethodePaiement(updatedReservation.getMethodePaiement());

            reservationHotelRepository.save(existingReservation);
        } else {
            throw new IllegalArgumentException("La réservation avec l'ID " + updatedReservation.getIdReservation() + " n'existe pas.");
        }
    }
}
