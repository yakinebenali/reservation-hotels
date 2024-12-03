package com.example.hotel.controller;

import com.example.hotel.model.Hotel;
import com.example.hotel.service.HotelService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.http.HttpStatus;

import java.nio.file.Path;


@RestController
@RequestMapping("/hotels")
public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }


    @GetMapping
    public List<Hotel> getAllHotels() {
        return hotelService.getAllHotels();
    }
    @DeleteMapping("/{id_hotel}")
    public ResponseEntity<String> deleteHotel(@PathVariable Long id_hotel) {
        hotelService.deleteHotelById(id_hotel);
        return ResponseEntity.ok("Hôtel supprimé avec succès.");
    }
    @PutMapping("/{id_hotel}")
    public ResponseEntity<Hotel> updateHotel(@PathVariable Long id_hotel, @RequestBody Hotel hotel) {
        Hotel updatedHotel = hotelService.updateHotel(id_hotel, hotel);
        return ResponseEntity.ok(updatedHotel);
    }

    private final String uploadDir = "C:\\Users\\dell\\gestion_hotel\\public\\images\\";

    @PostMapping("/upload-image")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
         
            String fileName = System.currentTimeMillis() + "-" + file.getOriginalFilename();

        
            Path filePath = Paths.get(uploadDir, fileName);

           
            Files.createDirectories(filePath.getParent()); 
            Files.write(filePath, file.getBytes());

            String fileUrl = "http://172.20.10.8:3000/images/" + fileName;

            return ResponseEntity.ok(fileUrl); 
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving file");
        }
    }
 @PostMapping
    public ResponseEntity<Hotel> addHotel(@RequestBody Hotel hotel) {
        Hotel savedHotel = hotelService.saveHotel(hotel);
        return ResponseEntity.ok(savedHotel);
    }
}


