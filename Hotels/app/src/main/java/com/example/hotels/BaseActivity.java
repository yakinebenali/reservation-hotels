package com.example.hotels;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupBottomNavigation(getSelectedItemId());
    }


    protected void setupBottomNavigation(int selectedItemId) {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        if (bottomNavigationView != null) {
              bottomNavigationView.setSelectedItemId(selectedItemId);
  bottomNavigationView.setOnItemSelectedListener(item -> {
                int itemId = item.getItemId();

                if (itemId == R.id.home) {
                    if (selectedItemId != R.id.home) {
                        startActivity(new Intent(this, HotelListActivity.class));
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        finish();
                    }
                    return true;

                } else if (itemId == R.id.reservations) {
                    if (selectedItemId != R.id.reservations) {
                       Intent reservationIntent = new Intent(this, Reservation.class);
                        startActivity(reservationIntent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        finish();
                    }
                    return true;

                } else if (itemId == R.id.connexion) {
                    SharedPreferences sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE);
                    String token = sharedPreferences.getString("auth_token", null);

                  Intent connexionIntent;
                    if (token != null) {
                        connexionIntent = new Intent(this, Profil.class);
                    } else {
                        connexionIntent = new Intent(this, Connexion.class);
                    }
                    startActivity(connexionIntent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                    return true;

                } else {
                    return false;
                }
            });
        }
    }


    protected abstract int getSelectedItemId();
}
