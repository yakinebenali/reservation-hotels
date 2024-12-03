package com.example.hotels;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotels.models.LoginRequest;
import com.example.hotels.models.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Connexion extends BaseActivity {

    private EditText emailInput, passwordInput;
    private Button btnLogin;
    private TextView txtRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);
 SharedPreferences sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE);
        String token = sharedPreferences.getString("auth_token", null);
        if (token != null) {

            startActivity(new Intent(Connexion.this, Profil.class));
            finish();
            return;
        }

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        btnLogin = findViewById(R.id.btn_login);
        txtRegister = findViewById(R.id.txt_register);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          Intent intent = new Intent(Connexion.this, Register.class); // Inscription est la nouvelle activité à créer
                startActivity(intent);
            }
        });
    }

    @Override
    protected int getSelectedItemId() {
        return R.id.connexion;
    }

    private void loginUser() {
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(Connexion.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        HotelApi apiService = RetrofitClient.getRetrofitInstance().create(HotelApi.class);
        LoginRequest loginRequest = new LoginRequest(email, password);

        apiService.loginUser(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String token = response.body().getToken();
                    int id_user = response.body().getIdUser();
                    String nom=response.body().getNom();
                    Log.d("Connexion", "ID de l'utilisateur récupéré: " + id_user);

                 SharedPreferences sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE);
                    sharedPreferences.edit()
                            .putString("auth_token", token)
                            .apply();

                    SharedPreferences preferences = getSharedPreferences("user_preferences", MODE_PRIVATE);
                    preferences.edit()
                            .putInt("id_user", id_user)
                            .apply();
                    SharedPreferences user_nom = getSharedPreferences("user_nom", MODE_PRIVATE);
                    user_nom.edit()
                            .putString("nom", nom)
                            .apply();

                    Log.d("kiko", "Nom de l'utilisateur récupéré: " + nom);

                    Log.d("kiko", "ID de l'utilisateur récupéré: " + id_user);

                    Toast.makeText(Connexion.this, "Connexion réussie", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Connexion.this, Profil.class));
                    finish();
                } else {
                    Toast.makeText(Connexion.this, "Identifiants incorrects", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(Connexion.this, "Erreur de connexion : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
