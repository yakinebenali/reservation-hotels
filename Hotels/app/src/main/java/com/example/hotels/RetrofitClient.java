package com.example.hotels;

import android.content.Context;
import android.util.Log;

import com.squareup.picasso.Picasso;
import com.jakewharton.picasso.OkHttp3Downloader;

import retrofit2.Retrofit; // Retrofit
import retrofit2.converter.gson.GsonConverterFactory; // Convertisseur JSON
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import java.util.concurrent.TimeUnit;  // Import ajouté pour TimeUnit

public class RetrofitClient {

    public static final String BASE_URL = "http://172.20.10.8:3000/";
    private static Retrofit retrofit;


    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            try {
                OkHttpClient okHttpClient = getHttpClient();

                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            } catch (Exception e) {
                Log.e("RetrofitClient", "Error while creating Retrofit instance", e);
            }
        }
        return retrofit;
    }

    private static OkHttpClient getHttpClient() {
        try {
            // Crée un OkHttpClient avec un logger HTTP
            return new OkHttpClient.Builder()
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)) // Log HTTP
                    .connectTimeout(30, TimeUnit.SECONDS)  // Utilisation de TimeUnit
                    .readTimeout(30, TimeUnit.SECONDS)  // Utilisation de TimeUnit
                    .build();
        } catch (Exception e) {
            Log.e("RetrofitClient", "Error while creating OkHttpClient", e);
            throw new RuntimeException(e);
        }
    }




}
