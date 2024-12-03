package com.example.hotel.ui;
import com.example.hotel.model.Hotel;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.Route;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import com.vaadin.flow.component.html.Label;
import org.springframework.http.HttpStatus; 

@Route("hotel")
public class HotelView extends VerticalLayout {

    private final RestTemplate restTemplate;

    public HotelView(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    
        Div pageTitle = new Div();
        pageTitle.setText("List of Hotels");
        
        pageTitle.getStyle().set("font-size", "24px").set("font-weight", "bold");
        pageTitle.getStyle().set("margin-bottom", "20px");
        Button reservationButton = new Button("Réservation", e -> 
        getUI().ifPresent(ui -> ui.navigate("reservations"))
    );
    Button homeButton = new Button("Accueil", e -> 
        getUI().ifPresent(ui -> ui.navigate("hotel"))
    );
    HorizontalLayout navBar = new HorizontalLayout(homeButton, reservationButton);
    navBar.getStyle()
        .set("margin-bottom", "20px")
        .set("justify-content", "space-between");

    add(navBar, pageTitle);
        
         Button addHotelButton = new Button("Add Hotel");
        addHotelButton.addClickListener(e -> addHotelDialog());
        add(addHotelButton);
        Div gridContainer = new Div();
        gridContainer.getStyle()
            .set("display", "flex")
            .set("flex-wrap", "wrap")
            .set("gap", "20px")
            .set("justify-content", "space-evenly")
            .set("margin-top", "20px");
      List<Hotel> hotels = getHotels();
       for (Hotel hotel : hotels) {
            Div hotelCard = createHotelCard(hotel);
            hotelCard.getStyle()
                .set("flex", "1 1 calc(33% - 20px)") 
                .set("max-width", "300px");
            gridContainer.add(hotelCard);
        }
       add(gridContainer);
    }
    private void addHotelDialog() {
        // Création de la boîte de dialogue
        Dialog addDialog = new Dialog();
    
        // Création du formulaire pour ajouter un nouvel hôtel
        VerticalLayout formLayout = new VerticalLayout();
    
        // Champs pour les informations de l'hôtel
        TextField nameField = new TextField("Hotel Name");
        TextField addressField = new TextField("Hotel Address");
        TextField cityField = new TextField("City");
        TextField phoneField = new TextField("Phone");
        TextField emailField = new TextField("Email");
        TimePicker openingTimeField = new TimePicker("Opening Time");
        TimePicker closingTimeField = new TimePicker("Closing Time");
    
        // Champ d'upload pour le logo
        MemoryBuffer logoBuffer = new MemoryBuffer();
        Upload logoUpload = new Upload(logoBuffer);
        logoUpload.setAcceptedFileTypes("image/jpeg", "image/png");
        Label logoUploadStatus = new Label();
        AtomicReference<String> uploadedLogoUrl = new AtomicReference<>();
    
        logoUpload.addSucceededListener(event -> {
            try {
                // Envoi du fichier logo au backend
                String uploadUrl = "http://172.20.10.8:8080/hotels/upload-image";
                RestTemplate restTemplate = new RestTemplate();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.MULTIPART_FORM_DATA);
    
                MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
                body.add("file", new ByteArrayResource(logoBuffer.getInputStream().readAllBytes()) {
                    @Override
                    public String getFilename() {
                        return event.getFileName();
                    }
                });
    
                HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
                ResponseEntity<String> response = restTemplate.postForEntity(uploadUrl, requestEntity, String.class);
    
                if (response.getStatusCode() == HttpStatus.OK) {
                    uploadedLogoUrl.set(response.getBody());
                    logoUploadStatus.setText("Logo uploaded successfully!");
                } else {
                    logoUploadStatus.setText("Logo upload failed!");
                }
            } catch (IOException e) {
                e.printStackTrace();
                logoUploadStatus.setText("Error uploading logo: " + e.getMessage());
            }
        });
    
        MemoryBuffer imageBuffer = new MemoryBuffer();
        Upload imageUpload = new Upload(imageBuffer);
        imageUpload.setAcceptedFileTypes("image/jpeg", "image/png");
        Label imageUploadStatus = new Label();
        AtomicReference<String> uploadedImageUrl = new AtomicReference<>();
    
        imageUpload.addSucceededListener(event -> {
            try {
              String uploadUrl = "http://172.20.10.8:8080/hotels/upload-image";
                RestTemplate restTemplate = new RestTemplate();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.MULTIPART_FORM_DATA);
    
                MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
                body.add("file", new ByteArrayResource(imageBuffer.getInputStream().readAllBytes()) {
                    @Override
                    public String getFilename() {
                        return event.getFileName();
                    }
                });
    
                HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
                ResponseEntity<String> response = restTemplate.postForEntity(uploadUrl, requestEntity, String.class);
    
                if (response.getStatusCode() == HttpStatus.OK) {
                    uploadedImageUrl.set(response.getBody());
                    imageUploadStatus.setText("Image uploaded successfully!");
                } else {
                    imageUploadStatus.setText("Image upload failed!");
                }
            } catch (IOException e) {
                e.printStackTrace();
                imageUploadStatus.setText("Error uploading image: " + e.getMessage());
            }
        });
     formLayout.add(
            nameField,
            addressField,
            cityField,
            phoneField,
            emailField,
            openingTimeField,
            closingTimeField,
            logoUpload,
            logoUploadStatus,
            imageUpload,
            imageUploadStatus
        );
    
     Button saveButton = new Button("Save Hotel", e -> {
            
            if (nameField.isEmpty() || addressField.isEmpty() || cityField.isEmpty()) {
                Notification.show("Please fill all required fields!");
                return;
            }
    
            if (uploadedLogoUrl.get() == null || uploadedImageUrl.get() == null) {
                Notification.show("Please upload both logo and image!");
                return;
            }
    
      
            Hotel newHotel = new Hotel();
            newHotel.setNom(nameField.getValue());
            newHotel.setAdresse(addressField.getValue());
            newHotel.setVille(cityField.getValue());
            newHotel.setNumeroTelephone(phoneField.getValue());
            newHotel.setEmail(emailField.getValue());
            newHotel.setHeureOuverture(openingTimeField.getValue());
            newHotel.setHeureFermeture(closingTimeField.getValue());
            newHotel.setLogo(uploadedLogoUrl.get()); 
            newHotel.setImage(uploadedImageUrl.get()); 
    
            try {
                RestTemplate restTemplate = new RestTemplate();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
    
                HttpEntity<Hotel> requestEntity = new HttpEntity<>(newHotel, headers);
                ResponseEntity<Hotel> response = restTemplate.exchange(
                    "http://172.20.10.8:8080/hotels",
                    HttpMethod.POST,
                    requestEntity,
                    Hotel.class
                );
    
                if (response.getStatusCode() == HttpStatus.OK) {
                    Notification.show("Hotel saved successfully!");
                    addDialog.close();
                    updateHotelList(); 
                }
            } catch (Exception ex) {
                Notification.show("Error saving hotel: " + ex.getMessage());
            }
        });
    
  
        Button cancelButton = new Button("Cancel", e -> addDialog.close());
    
      
        formLayout.add(saveButton, cancelButton);
    
        addDialog.add(formLayout);
    
      
        addDialog.open();
    }
      private Div createHotelCard(Hotel hotel) {
    Div card = new Div();
    card.getStyle()
        .set("border", "1px solid #ddd")
        .set("border-radius", "8px")
        .set("box-shadow", "0 4px 8px rgba(0, 0, 0, 0.1)")
        .set("padding", "16px")
        .set("margin", "10px")
        .set("max-width", "300px"); 

    Image logoImage = new Image(hotel.getLogo(), "Hotel Logo");
    logoImage.setWidth("100%");
    logoImage.setHeight("200px");
    card.add(logoImage);
  Div nameDiv = new Div();
    nameDiv.setText(hotel.getNom());
    nameDiv.getStyle()
        .set("font-size", "20px")
        .set("font-weight", "bold")
        .set("margin-top", "10px");
    card.add(nameDiv);

    Div detailsDiv = new Div();
    detailsDiv.setText("Address: " + hotel.getAdresse() + ", " + hotel.getVille());
    detailsDiv.getStyle()
        .set("color", "gray")
        .set("font-size", "14px");
    card.add(detailsDiv);
  Div contactDiv = new Div();
    contactDiv.setText("Phone: " + hotel.getNumeroTelephone() + " | Email: " + hotel.getEmail());
    contactDiv.getStyle()
        .set("font-size", "14px");
    card.add(contactDiv);
  Div hoursDiv = new Div();
    hoursDiv.setText("Open: " + hotel.getHeureOuverture() + " - Close: " + hotel.getHeureFermeture());
    hoursDiv.getStyle()
        .set("font-size", "14px");
    card.add(hoursDiv);
  HorizontalLayout buttonLayout = new HorizontalLayout();
    Button editButton = new Button("Edit", e -> editHotel(hotel));
    Button deleteButton = new Button("Delete", e -> deleteHotel(hotel));
    buttonLayout.add(editButton, deleteButton);
    card.add(buttonLayout);

    return card;
}

    private List<Hotel> getHotels() {
        ResponseEntity<List<Hotel>> response = restTemplate.exchange(
            "http://172.20.10.8:8080/hotels",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<Hotel>>() {}
        );
        return response.getBody();
    }

  private void editHotel(Hotel hotel) {
     Dialog editDialog = new Dialog();
    VerticalLayout formLayout = new VerticalLayout();
    TextField nameField = new TextField("Hotel Name");
    nameField.setValue(hotel.getNom());
    TextField addressField = new TextField("Hotel Address");
    addressField.setValue(hotel.getAdresse());
    TextField cityField = new TextField("City");
    cityField.setValue(hotel.getVille());

    TextField phoneField = new TextField("Phone");
    phoneField.setValue(hotel.getNumeroTelephone());

    TextField emailField = new TextField("Email");
    emailField.setValue(hotel.getEmail());

   TimePicker openingTimeField = new TimePicker("Opening Time");
openingTimeField.setValue(hotel.getHeureOuverture());  

TimePicker closingTimeField = new TimePicker("Closing Time");
closingTimeField.setValue(hotel.getHeureFermeture());

    formLayout.add(nameField, addressField, cityField, phoneField, emailField, openingTimeField, closingTimeField);

    Button saveButton = new Button("Save Changes", e -> {

        hotel.setNom(nameField.getValue());
        hotel.setAdresse(addressField.getValue());
        hotel.setVille(cityField.getValue());
        hotel.setNumeroTelephone(phoneField.getValue());
        hotel.setEmail(emailField.getValue());
        hotel.setHeureOuverture(openingTimeField.getValue());
        hotel.setHeureFermeture(closingTimeField.getValue());

        restTemplate.exchange(
            "http://172.20.10.8:8080/hotels/" + hotel.getId(),
            HttpMethod.PUT,
            new HttpEntity<>(hotel),
            Void.class
        );
        editDialog.close();

        updateHotelList();
    });
    Button cancelButton = new Button("Cancel", e -> editDialog.close());

    formLayout.add(saveButton, cancelButton);

    editDialog.add(formLayout);

    editDialog.open();
}
    private void deleteHotel(Hotel hotel) {
  
        restTemplate.exchange(
            "http://172.20.10.8:8080/hotels/" + hotel.getId(),
            HttpMethod.DELETE,
            null,
            Void.class
        );
    
      List<Hotel> hotels = getHotels();
    
        removeAll();
 
        Div pageTitle = new Div();
        pageTitle.setText("List of Hotels");
        pageTitle.getStyle().set("font-size", "24px").set("font-weight", "bold");
        pageTitle.getStyle().set("margin-bottom", "20px");
        Button reservationButton = new Button("Réservation", e -> 
        getUI().ifPresent(ui -> ui.navigate("reservations"))
    );
    Button homeButton = new Button("Accueil", e -> 
        getUI().ifPresent(ui -> ui.navigate("hotel"))
    );
    HorizontalLayout navBar = new HorizontalLayout(homeButton, reservationButton);
    navBar.getStyle()
        .set("margin-bottom", "20px")
        .set("justify-content", "space-between");

    add(navBar, pageTitle);
        Button addHotelButton = new Button("Add Hotel");
        addHotelButton.addClickListener(e -> addHotelDialog());
        add(addHotelButton);
     Div gridContainer = new Div();
        gridContainer.getStyle()
            .set("display", "flex")
            .set("flex-wrap", "wrap")
            .set("gap", "20px")
            .set("justify-content", "space-evenly")
            .set("margin-top", "20px");
        for (Hotel updatedHotel : hotels) {
            Div hotelCard = createHotelCard(updatedHotel);
            hotelCard.getStyle()
            .set("flex", "1 1 calc(33% - 20px)")
            .set("max-width", "300px");
        gridContainer.add(hotelCard);
    }
            add(gridContainer);
        
    }
    
    private void updateHotelList() {

        List<Hotel> hotels = getHotels();
   removeAll();
   Div pageTitle = new Div();
        pageTitle.setText("List of Hotels");
        pageTitle.getStyle()
            .set("font-size", "24px")
            .set("font-weight", "bold")
            .set("margin-bottom", "20px");
            Button reservationButton = new Button("Réservation", e -> 
            getUI().ifPresent(ui -> ui.navigate("reservations"))
        );
        Button homeButton = new Button("Accueil", e -> 
            getUI().ifPresent(ui -> ui.navigate("hotel"))
        );
        HorizontalLayout navBar = new HorizontalLayout(homeButton, reservationButton);
        navBar.getStyle()
            .set("margin-bottom", "20px")
            .set("justify-content", "space-between");
    
        add(navBar, pageTitle);
      Button addHotelButton = new Button("Add Hotel");
        addHotelButton.addClickListener(e -> addHotelDialog());
        add(addHotelButton);
     Div gridContainer = new Div();
        gridContainer.getStyle()
            .set("display", "flex")
            .set("flex-wrap", "wrap")
            .set("gap", "20px")
            .set("justify-content", "space-evenly")
            .set("margin-top", "20px");
    
        for (Hotel hotel : hotels) {
            Div hotelCard = createHotelCard(hotel);
            hotelCard.getStyle()
                .set("flex", "1 1 calc(33% - 20px)")
                .set("max-width", "300px");
            gridContainer.add(hotelCard);
        }
    
        add(gridContainer);
    }
    
}

