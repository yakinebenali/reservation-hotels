package com.example.hotel.ui;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

@Route("") 
public class MainView extends VerticalLayout {
    public MainView() {
        add(new Label("Bienvenue à l'application !"));
    }
}
