package com.example.hotels.models;


public class ReservationResponse {
    private String message;
    private int reservation_id;


    public ReservationResponse(String message, int reservation_id) {
        this.message = message;
        this.reservation_id = reservation_id;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(int reservation_id) {
        this.reservation_id = reservation_id;
    }
}
