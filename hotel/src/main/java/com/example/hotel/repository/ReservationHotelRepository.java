package com.example.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hotel.model.ReservationHotel;


public interface ReservationHotelRepository extends JpaRepository<ReservationHotel, Integer> {

    
    
}
