package com.example.hotel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.hotel.model.ReservationHotel;
import com.example.hotel.service.ReservationHotelService;
@RestController
public class ReservationHotelController {

    @Autowired
    private ReservationHotelService reservationHotelService;

    @GetMapping("/reservation")
    public List<ReservationHotel> getAllReservations() {
        return reservationHotelService.getAllReservations();
    }
    @PostMapping("/reservation/update")
public ResponseEntity<Void> updateReservation(@RequestBody ReservationHotel reservation) {
    reservationHotelService.updateReservation(reservation);
    return ResponseEntity.ok().build();
}

}



