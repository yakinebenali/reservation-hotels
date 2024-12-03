package com.example.hotels;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import android.text.Editable;
import android.text.TextWatcher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.ArrayList;
import java.util.List;

public class HotelListActivity extends BaseActivity {

    private HotelAdapter hotelAdapter;
    private List<Hotel> fullHotelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_list);

        setupBottomNavigation(R.id.home);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        EditText searchInput = findViewById(R.id.searchInput);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        HotelApi hotelApi = RetrofitClient.getRetrofitInstance().create(HotelApi.class);
        Call<List<Hotel>> call = hotelApi.getHotels();
        call.enqueue(new Callback<List<Hotel>>() {
            @Override
            public void onResponse(Call<List<Hotel>> call, Response<List<Hotel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    fullHotelList = response.body();
                    hotelAdapter = new HotelAdapter(HotelListActivity.this, fullHotelList);
                    recyclerView.setAdapter(hotelAdapter);
                } else {
                    Toast.makeText(HotelListActivity.this, "Erreur de récupération des hôtels", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Hotel>> call, Throwable t) {
                Toast.makeText(HotelListActivity.this, "Erreur réseau: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                filterHotelsByAddress(s.toString());
            }
        });
    }


    private void filterHotelsByAddress(String query) {
        if (fullHotelList == null || hotelAdapter == null) return;

        List<Hotel> filteredList = new ArrayList<>();
        for (Hotel hotel : fullHotelList) {
            if (hotel.getAdresse() != null && hotel.getAdresse().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(hotel);
            }
        }

        hotelAdapter.updateData(filteredList);
    }


    @Override
    protected int getSelectedItemId() {
        return R.id.home;
    }
}
