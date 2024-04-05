package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Guest;
import models.Reservation;
import models.Room;
import models.RoomManager;
import models.RoomTypeName;

public class AdminBookRoomGuestInfoController {

    @FXML
    private TextField addressField;

    @FXML
    private Button bookButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField emailField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField titleField;
    
    private Reservation reservation;
    
    private Guest guest;
    
    private List<Room> rooms = new ArrayList<>();
    
    int discount;
    double originalTotal;
    
    AdminBookRoomController abController= new AdminBookRoomController();

    public void initData(Reservation reservation, int discount, double originalTotal) {
        this.reservation = reservation;
        this.discount = discount;
        this.originalTotal = originalTotal;
        // Populate guest information fields with the data from reservation
    }

    @FXML
    private void initialize() {
    	RoomManager roomManager = RoomManager.getInstance();
        rooms = roomManager.getRooms();
    	
    	
        // Add event listeners for validation
        emailField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isValidEmail(newValue)) {
                // Invalid email, handle error
                emailField.setStyle("-fx-border-color: red;");
            } else {
                emailField.setStyle(""); // Reset style
            }
        });

        firstNameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isValidName(newValue)) {
                // Invalid name, handle error
                firstNameField.setStyle("-fx-border-color: red;");
            } else {
                firstNameField.setStyle(""); // Reset style
            }
        });

        lastNameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isValidName(newValue)) {
                // Invalid name, handle error
                lastNameField.setStyle("-fx-border-color: red;");
            } else {
                lastNameField.setStyle(""); // Reset style
            }
        });
        
        addressField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isValidAddress(newValue)) {
                // Invalid name, handle error
                addressField.setStyle("-fx-border-color: red;");
            } else {
                addressField.setStyle(""); // Reset style
            }
        });

        phoneField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isValidPhoneNumber(newValue)) {
                // Invalid phone number, handle error
                phoneField.setStyle("-fx-border-color: red;");
            } else {
                phoneField.setStyle(""); // Reset style
            }
        });

        titleField.textProperty().addListener((observable, oldValue, newValue) -> {
        	 if (!isValidTitle(newValue)) {
                 // Invalid name, handle error
                 titleField.setStyle("-fx-border-color: red;");
             } else {
                 titleField.setStyle(""); // Reset style
             }
        });
    }

    private boolean isValidEmail(String email) {
        // Basic email validation
        return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }
    
    private boolean isValidName(String name) {
        // Basic name validation
        return name.matches("[a-zA-Z]+");
    }
    
    private boolean isValidAddress(String address) {
        // Basic name validation
        return address.matches("[a-zA-Z]+");
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        // Basic phone number validation (digits only)
        return phoneNumber.matches("\\d+");
    }
    
    private boolean isValidTitle(String title) {
        // Basic name validation
        return title.matches("[a-zA-Z]+");
    }
    
    @FXML
    void handleBook(ActionEvent event) {
    	
    	String title;
    	String fName;
    	String lName;
    	String address;
    	int phone;
    	String email;
    	
    	if (titleField.getText().trim().isEmpty() || firstNameField.getText().trim().isEmpty() ||
    			lastNameField.getText().trim().isEmpty() || addressField.getText().trim().isEmpty() ||
    			phoneField.getText().trim().isEmpty() || emailField.getText().trim().isEmpty()) {
    		showAlert(Alert.AlertType.ERROR, "Error", "Please fill in your information.");
    		return;
    	}
    	
    	try {
    		title = titleField.getText().trim();
    	}catch(NumberFormatException e){
    		showAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid title.");
    		return;
    	}
    	
    	try {
    		fName = firstNameField.getText().trim();
    	}catch(NumberFormatException e){
    		showAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid first name.");
    		return;
    	}
    	
    	try {
    		lName = lastNameField.getText().trim();
    	}catch(NumberFormatException e){
    		showAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid last name.");
    		return;
    	}	
    	
    	try {
    		address = addressField.getText().trim();
    	}catch(NumberFormatException e){
    		showAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid address.");
    		return;
    	}
    	
    	try {
    		phone = Integer.parseInt(phoneField.getText().trim());
    	}catch(NumberFormatException e){
    		showAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid phone number.");
    		return;
    	}
    	
    	try {
    		email = emailField.getText().trim();
    	}catch(NumberFormatException e){
    		showAlert(Alert.AlertType.ERROR, "Error", "Please enter a email address.");
    		return;
    	}
    	
    	if (!(isValidTitle(title) && isValidName(fName) && isValidName(lName) && isValidAddress(address)  
    			&& isValidPhoneNumber(phoneField.getText().trim()) && isValidEmail(email))) {
    
    		showAlert(Alert.AlertType.ERROR, "Error", "Please verify your input.");
    		return;
    	}
    	
    	// construct a Guest object
    	Guest guest = new Guest(title, fName, lName, address, phone, email); 
    	
    	reservation.setGuest(guest);
    	
    	List<Room> bookedRooms = reservation.getRooms();
    	
    	for (Room room : bookedRooms) {
    		rooms.remove(room);
    	}
    	
    	System.out.println("the number of rooms after guest booking is: " + rooms.size());
    	
    	showAlertWithRoomTypes(Alert.AlertType.INFORMATION, "ABC Hotel reservation inforamtion", reservation, bookedRooms);
    	
    }

    @FXML
    void handleCancel(ActionEvent event) {
    	Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    
 // helper function to show the Alert on the screen
    public void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    public void showAlertWithRoomTypes(Alert.AlertType alertType, String title, Reservation reservation, List<Room> bookedRooms) {
    	
    	 Map<RoomTypeName, Integer> roomTypeCount = new HashMap<>();
         Map<RoomTypeName, Double> roomTypePrice = new HashMap<>();

         // Count occurrences of each room type and store their prices
         for (Room room : bookedRooms) {
             RoomTypeName roomType = room.getRoomType();
             roomTypeCount.put(roomType, roomTypeCount.getOrDefault(roomType, 0) + 1);
             roomTypePrice.put(roomType, room.getPrice());
         }

         StringBuilder roomTypesInfo = new StringBuilder();
         for (Map.Entry<RoomTypeName, Integer> entry : roomTypeCount.entrySet()) {
             RoomTypeName roomType = entry.getKey();
             int count = entry.getValue();
             double price = roomTypePrice.get(roomType);
             roomTypesInfo.append(roomType).append(": ").append(count).append(" room(s) - $").append(price).append(" per room\n");
         }
    	
        showAlert(alertType, title,
                "Congratulations!\n\n" +
                "Your booking info with ABC Hotel is as following:\n\n" +
                "Name of the guest: " + reservation.getGuest().getTitle() + " " + 
                reservation.getGuest().getFirstName() + " " + reservation.getGuest().getLastName() + "\n\n" +
                "Check in date: " + reservation.getCheckInDate() + "\n" +
                "Check out date: " + reservation.getCheckOutDate() + "\n" + "\n" +
                "Rooms:\n" +
                roomTypesInfo.toString() + "\n" +
                "The total amount is: $" + reservation.getBill().getAmount() + " with "+ 
                discount + "%" + " discount " + 
                "($" + originalTotal * discount / 100 + ")" + "\n\n" +           
                "We have sent a copy of reservation to your email address " + reservation.getGuest().getEmail() + "\n\n" +
                "Thank you for booking with ABC Hotel!");
                
    }

}

