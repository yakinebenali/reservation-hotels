package com.example.hotels;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnProfil;
    private ImageView coverImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnProfil = findViewById(R.id.btnProfil);
        coverImage = findViewById(R.id.coverImage);
        btnProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHotelListActivity();
            }
        });

        coverImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHotelListActivity();
            }
        });
    }


    private void openHotelListActivity() {
        startActivity(new Intent(MainActivity.this, HotelListActivity.class));
        finish();
    }
}