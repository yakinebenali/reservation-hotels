package com.example.hotels;


import com.example.hotels.models.LoginRequest;
import com.example.hotels.models.LoginResponse;
import com.example.hotels.models.RegisterRequest;
import com.example.hotels.models.RegisterResponse;
import com.example.hotels.models.ReservationResponse;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface HotelApi {
        @GET("hotels")
        Call<List<Hotel>> getHotels();

    @POST("/hotels")
    Call<Void> addHotel(@Body Hotel hotel);

    @POST("/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);
    @POST("/register")
    Call<RegisterResponse> registerUser(@Body RegisterRequest registerRequest);
    @POST("/addReservation")
    Call<ReservationResponse> addReservation(@Body Reservations reservation);
    @GET("/reservations/{userId}")
    Call<List<Reservations>> getReservations(@Path("userId") int userId);
    @GET("/infouser/{id_user}")
    Call<User> getUserInfo(@Path("id_user") int idUser);
}
