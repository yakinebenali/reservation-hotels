package com.example.hotel.ui;

import com.example.hotel.model.ReservationHotel;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

@Route("reservations")
public class ReservationHotelView extends VerticalLayout {

    private final RestTemplate restTemplate;
    private final Grid<ReservationHotel> grid = new Grid<>(ReservationHotel.class);

    public ReservationHotelView(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;

        grid.setColumns("idReservation", "nomHotel", "idUser", "statut");
        grid.setItems(fetchReservations());

     
        Button homeButton = new Button("Accueil", e -> 
            getUI().ifPresent(ui -> ui.navigate("hotel"))
        );

        Button refreshButton = new Button("Rafraîchir", e -> grid.setItems(fetchReservations()));

        HorizontalLayout navBar = new HorizontalLayout(homeButton, refreshButton);
        navBar.getStyle()
            .set("margin-bottom", "20px")
            .set("justify-content", "space-between");

        add(navBar, grid);
        setSpacing(true);

        grid.addItemClickListener(event -> openEditDialog(event.getItem()));
    }

    private List<ReservationHotel> fetchReservations() {
        ResponseEntity<List<ReservationHotel>> response = restTemplate.exchange(
                "http://172.20.10.8:8080/reservation",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ReservationHotel>>() {}
        );
        return response.getBody();
    }

    private void openEditDialog(ReservationHotel reservation) {
        Dialog dialog = new Dialog();

        ComboBox<String> statusComboBox = new ComboBox<>("Statut");
        statusComboBox.setItems("En cours", "Accepté", "Annulé");
        statusComboBox.setValue(reservation.getStatut());

        Button saveButton = new Button("Enregistrer", e -> {
            reservation.setStatut(statusComboBox.getValue());
            updateReservation(reservation);
            grid.setItems(fetchReservations()); 
            dialog.close();
        });

    
        Button cancelButton = new Button("Annuler", e -> dialog.close());

        HorizontalLayout buttonsLayout = new HorizontalLayout(saveButton, cancelButton);
        dialog.add(new VerticalLayout(statusComboBox, buttonsLayout));
        dialog.open();
    }

    private void updateReservation(ReservationHotel reservation) {
        restTemplate.postForObject(
                "http://172.20.10.8:8080/reservation/update",
                reservation,
                Void.class
        );
    }
}
