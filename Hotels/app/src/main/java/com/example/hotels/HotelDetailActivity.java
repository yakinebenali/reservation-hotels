package com.example.hotels;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hotels.models.ReservationResponse;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HotelDetailActivity extends AppCompatActivity {

    private TextView nom, adresse, ville;
    private ImageView image, backButton,logo;
    private Button reserverButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_detail);

        nom = findViewById(R.id.hotelNom);
        adresse = findViewById(R.id.hotelAdresse);
        ville = findViewById(R.id.hotelVille);
        image = findViewById(R.id.hotelImage);
        logo = findViewById(R.id.hotel_logo);
        backButton = findViewById(R.id.backButton);
        reserverButton = findViewById(R.id.reserverButton);

        String hotelNom = getIntent().getStringExtra("hotel_nom");
        String hotelAdresse = getIntent().getStringExtra("hotel_adresse");
        String hotelVille = getIntent().getStringExtra("hotel_Ville");
        String hotelImage = getIntent().getStringExtra("hotel_image");
        String hotel_logo = getIntent().getStringExtra("hotel_logo");
        final int idHotel = getIntent().getIntExtra("hotel_id", -1); // Récupérer l'ID de l'hôtel

        if (hotelNom != null) {
            nom.setText(hotelNom);
        }
        if (hotelAdresse != null) {
            adresse.setText(hotelAdresse);
        }
        if (hotelVille != null) {
            ville.setText(hotelVille);
        }

        if (hotelImage != null && !hotelImage.isEmpty()) {
            Picasso.get().load(hotelImage).into(image);
        } else {
            image.setImageResource(R.drawable.hotel);
        }
        if (hotel_logo != null && !hotel_logo.isEmpty()) {
            Picasso.get().load(hotel_logo).into(logo);
        } else {
            logo.setImageResource(R.drawable.hotel);
        }
        reserverButton.setOnClickListener(v -> {

            Reservations reservation = createReservation(hotelNom, hotelAdresse, hotelVille, idHotel);
   makeReservation(reservation);
        });

        backButton.setOnClickListener(v -> finish());
    }

    private Reservations createReservation(String hotelNom, String hotelAdresse, String hotelVille, int idHotel) {
        SharedPreferences preferences = getSharedPreferences("user_preferences", MODE_PRIVATE);
        int idUser = preferences.getInt("id_user", -1);  // La valeur par défaut est -1 si l'ID n'existe pas
        Log.d("HotelDetailActivity", "ID de l'utilisateur: " + idUser);


        double prixParJour = 100.0;
        int nbreJours = 3;
        double total = prixParJour * nbreJours;
        String dateReservation = "2024-12-01";
        String commentaire = "Aucun commentaire";
        String methodePaiement = "Carte de crédit";
        String statut = "En attente";


        return new Reservations(idUser, idHotel, hotelNom, prixParJour, total, nbreJours,
                dateReservation,statut, commentaire, methodePaiement);
    }

    private void makeReservation(Reservations reservation) {

        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        HotelApi hotelApi = retrofit.create(HotelApi.class);


        Call<ReservationResponse> call = hotelApi.addReservation(reservation);
        call.enqueue(new Callback<ReservationResponse>() {
            @Override
            public void onResponse(Call<ReservationResponse> call, Response<ReservationResponse> response) {
                if (response.isSuccessful()) {
                     ReservationResponse reservationResponse = response.body();
                    if (reservationResponse != null) {
                        Toast.makeText(HotelDetailActivity.this, "Réservation réussie ! ID: " + reservationResponse.getReservation_id(),
                                Toast.LENGTH_SHORT).show();

                         Intent intent = new Intent(HotelDetailActivity.this, Reservation.class);
                        intent.putExtra("reservation_id", reservationResponse.getReservation_id()); // Passer l'ID de la réservation
                        startActivity(intent);
                        finish();        }
                } else {
                    Toast.makeText(HotelDetailActivity.this, "Connecter puis ressayer", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReservationResponse> call, Throwable t) {
                Toast.makeText(HotelDetailActivity.this, "Échec de la connexion", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
