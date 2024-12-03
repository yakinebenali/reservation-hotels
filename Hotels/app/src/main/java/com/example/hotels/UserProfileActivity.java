package com.example.hotels;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserProfileActivity extends AppCompatActivity {

    private TextView textViewNom, textViewNumero, textViewEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        textViewNom = findViewById(R.id.textViewUserName);
        textViewNumero = findViewById(R.id.textViewUserNumber);
        textViewEmail = findViewById(R.id.textViewUserEmail);


        HotelApi hotelApi = RetrofitClient.getRetrofitInstance().create(HotelApi.class);

        SharedPreferences preferences = getSharedPreferences("user_preferences", MODE_PRIVATE);
        int userId = preferences.getInt("id_user", -1);
        Log.d("HotelDetailActivity", "ID de l'utilisateur: " + userId);

        getUserInfo(userId);
    }

    private void getUserInfo(int userId) {
        HotelApi hotelApi = RetrofitClient.getRetrofitInstance().create(HotelApi.class);

        Call<User> call = hotelApi.getUserInfo(userId);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();
                    textViewNom.setText(user.getNom());
                    textViewNumero.setText(user.getNumero());
                    textViewEmail.setText(user.getEmail());
                } else {
                    Toast.makeText(UserProfileActivity.this, "Utilisateur non trouvé", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(UserProfileActivity.this, "Erreur réseau : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
