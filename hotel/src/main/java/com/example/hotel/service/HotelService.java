package com.example.hotel.service;

import com.example.hotel.model.Hotel;
import com.example.hotel.repository.HotelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }
    public void deleteHotelById(Long id_hotel) {
        hotelRepository.deleteById(id_hotel);
    }
    public Hotel updateHotel(Long id_hotel, Hotel updatedHotel) {
        if (hotelRepository.existsById(id_hotel)) {
            updatedHotel.setId(id_hotel); 
            return hotelRepository.save(updatedHotel);
        } else {
            throw new RuntimeException("Hotel not found with id " + id_hotel);
        }
    }
    public Hotel saveHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

}
