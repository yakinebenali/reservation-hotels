package com.example.hotels;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder> {

    private List<Reservations> reservationsList;

   public ReservationAdapter(List<Reservations> reservations) {
        this.reservationsList = reservations;
    }

    @Override
    public ReservationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reservation, parent, false);
        return new ReservationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ReservationViewHolder holder, int position) {
         Reservations reservation = reservationsList.get(position);
        String statut = reservation.getStatut();

        holder.nomHotel.setText(reservation.getNom_hotel());
        holder.dateReservation.setText(reservation.getDate_reservation());
        holder.statut.setText("Statut: " + statut);
        holder.total.setText(String.valueOf(reservation.getTotal()));
    }

    @Override
    public int getItemCount() {
        return reservationsList.size();
    }


    public static class ReservationViewHolder extends RecyclerView.ViewHolder {
        public TextView nomHotel;
        public TextView dateReservation;
        public TextView statut;
        public TextView total;

        public ReservationViewHolder(View view) {
            super(view);
            nomHotel = view.findViewById(R.id.nomHotel);
            dateReservation = view.findViewById(R.id.dateReservation);
            statut = view.findViewById(R.id.statut);
            total=view.findViewById(R.id.total);
        }
    }
}
