package com.example.hotels;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Profil extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
checkAuthentication();

       TextView editProfile = findViewById(R.id.txt_edit_profile);
        editProfile.setOnClickListener(v -> navigateToEditProfile());

        TextView paymentPage = findViewById(R.id.txt_payment);
        paymentPage.setOnClickListener(v -> navigateToPaymentPage());

        TextView logout = findViewById(R.id.txt_logout);
        logout.setOnClickListener(v -> logoutUser());
        setUserNameInProfile();
        setupBottomNavigation(R.id.connexion);
    }

    private void checkAuthentication() {
        SharedPreferences sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE);
        String token = sharedPreferences.getString("auth_token", null);

        if (token == null) {
            startActivity(new Intent(this, HotelListActivity.class));
            finish();
        }
    }
    private void setUserNameInProfile() {

        SharedPreferences user_nom = getSharedPreferences("user_nom", MODE_PRIVATE);
        String userName = user_nom.getString("nom", "Utilisateur"); // Default to "Utilisateur" if name is not found


        TextView userNameTextView = findViewById(R.id.txt_user_name);
        userNameTextView.setText("Bonjour: " + userName);
    }
    private void logoutUser() {
     SharedPreferences sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE);
        SharedPreferences userPreferences = getSharedPreferences("user_preferences", MODE_PRIVATE);
 String authToken = sharedPreferences.getString("auth_token", "not found");
        int idUser = userPreferences.getInt("id_user", -1);  // Récupérer l'ID utilisateur du bon fichier
        Log.d("LogoutDebug", "Avant suppression: auth_token=" + authToken + ", id_user=" + idUser);
  boolean authRemoved = sharedPreferences.edit().remove("auth_token").commit();
 boolean userIdRemoved = userPreferences.edit().remove("id_user").commit();

        Log.d("LogoutDebug", "Après suppression: auth_removed=" + authRemoved + ", id_user_removed=" + userIdRemoved);
     startActivity(new Intent(Profil.this, HotelListActivity.class));
        finish();
    }


    private void navigateToEditProfile() {
   startActivity(new Intent(Profil.this, UserProfileActivity.class));
    }

    private void navigateToPaymentPage() {
    startActivity(new Intent(Profil.this, PaymentPageActivity.class));
    }

    @Override
    protected int getSelectedItemId() {
        return R.id.connexion;
    }
}
