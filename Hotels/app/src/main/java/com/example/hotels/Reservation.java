package com.example.hotels;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotels.ReservationAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
public class Reservation extends BaseActivity {

    private RecyclerView recyclerView;
    private ReservationAdapter adapter;
    private int userId;
    private TextView noReservationText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

         setupBottomNavigation(R.id.reservations);
  recyclerView = findViewById(R.id.recyclerViewReservations);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

         noReservationText = findViewById(R.id.noReservationText);

         userId = getUserId();
  fetchReservations(userId);
    }

    private int getUserId() {
        SharedPreferences preferences = getSharedPreferences("user_preferences", MODE_PRIVATE);
        int idUser = preferences.getInt("id_user", -1);
        Log.d("reservation", "ID de l'utilisateur: " + idUser);
        return idUser;
    }

    @Override
    protected int getSelectedItemId() {
        return R.id.reservations;
    }

    private void fetchReservations(int userId) {

        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        HotelApi reservationApi = retrofit.create(HotelApi.class);
   Call<List<Reservations>> call = reservationApi.getReservations(userId);

        call.enqueue(new Callback<List<Reservations>>() {
            @Override
            public void onResponse(Call<List<Reservations>> call, Response<List<Reservations>> response) {
                if (response.isSuccessful()) {
                    List<Reservations> reservations = response.body();
                    if (reservations != null && !reservations.isEmpty()) {
                          adapter = new ReservationAdapter(reservations);
                        recyclerView.setAdapter(adapter);
                        noReservationText.setVisibility(View.GONE);
                    } else {
                         noReservationText.setVisibility(View.VISIBLE);        }
                } else {
                     noReservationText.setVisibility(View.VISIBLE);         }
            }

            @Override
            public void onFailure(Call<List<Reservations>> call, Throwable t) {
                 noReservationText.setVisibility(View.VISIBLE);   }
        });
    }
}
