package com.example.hotels;

import android.content.Context;  // Import de la classe Context
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import coil.ImageLoader;
import coil.request.ImageRequest;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelViewHolder> {

    private Context context;
    private List<Hotel> hotelList;

    public HotelAdapter(Context context, List<Hotel> hotelList) {
        this.context = context;
        this.hotelList = hotelList;
    }

    @Override
    public HotelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.hotel_item, parent, false);
        return new HotelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HotelViewHolder holder, int position) {
        Hotel hotel = hotelList.get(position);

        holder.nameTextView.setText(hotel.getNom());
        holder.addressTextView.setText(hotel.getAdresse());
        holder.ville.setText(hotel.getVille());
        holder.HeureOuverture.setText(hotel.getHeureOuverture());
        holder.HeureFermeture.setText(hotel.getHeureFermeture());
  ImageRequest imageRequest = new ImageRequest.Builder(context)
                .data(hotel.getLogoUrl())
                .target(holder.imageView)
                .build();

        ImageLoader imageLoader = new ImageLoader.Builder(context).build();
        imageLoader.enqueue(imageRequest);
 holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, HotelDetailActivity.class);
            intent.putExtra("hotel_nom", hotel.getNom());

            intent.putExtra("hotel_adresse", hotel.getAdresse());
            intent.putExtra("hotel_Ville", hotel.getVille());
            intent.putExtra("hotel_logo", hotel.getLogoUrl());
            intent.putExtra("hotel_image", hotel.getImageUrl());
            intent.putExtra("hotel_heure_ouverture", hotel.getHeureOuverture());
            intent.putExtra("hotel_heure_fermeture", hotel.getHeureFermeture());
            intent.putExtra("hotel_id", hotel.getId());
            context.startActivity(intent);
        });
    }



    public void updateData(List<Hotel> newHotelList) {
        this.hotelList = newHotelList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return hotelList.size();
    }

    public static class HotelViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView addressTextView,ville;
        ImageView imageView;
        TextView HeureOuverture,HeureFermeture;
        public HotelViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nom);
            addressTextView = itemView.findViewById(R.id.adresse);
            ville = itemView.findViewById(R.id.ville);
            imageView = itemView.findViewById(R.id.hotelImage);
            HeureOuverture = itemView.findViewById(R.id.hotelHeureOuverture);
            HeureFermeture = itemView.findViewById(R.id.hotelHeureFermeture);// Assurez-vous que vous avez un ImageView avec cet ID dans le layout
        }
    }
}
