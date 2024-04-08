package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Bill;
import models.Guest;
import models.Reservation;
import models.Room;

import models.RoomTypeName;
import models.SqliteConnection;

public class GuestBookRoomController implements Initializable{

	@FXML
    private TextField addressField;

    @FXML
    private TextField availableRoomField;

    @FXML
    private Button cancelButton;

    @FXML
    private DatePicker checkInDate;

    @FXML
    private DatePicker checkOutDate;

    @FXML
    private CheckBox deluxCheckBox;

    @FXML
    private TextField deluxField;

    @FXML
    private CheckBox doubleCheckBox;

    @FXML
    private TextField doubleField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField noOfGuestField;

    @FXML
    private CheckBox pentHouseCheckBox;

    @FXML
    private TextField pentHouseField;

    @FXML
    private TextField phoneField;

    @FXML
    private Label priceOfDelux;

    @FXML
    private Label priceOfDouble;

    @FXML
    private Label priceOfPentHouse;

    @FXML
    private Label priceOfSingle;

    @FXML
    private CheckBox singleCheckBox;

    @FXML
    private TextField singleField;

    @FXML
    private TextField titleField;
   
    @FXML
    private List<Room> rooms = new ArrayList<>();
    List<Room> bookedRooms = new ArrayList<>();
    LocalDate checkInDateValue;
    LocalDate checkOutDateValue;
    long days;
    Connection conn;
    Reservation reservation;
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	conn = SqliteConnection.Connector();
    	if (conn == null) {
    		System.out.println("Connection is not successful in GuestBookRoomController.");
    		System.exit(1);
    	}
    	else {
    		System.out.println("Connection is successful in GuestBookRoomController!!!");
    	}
    	
    	PreparedStatement ps = null;
        ResultSet resultSet = null;
        String query = "SELECT COUNT(*) AS room_count FROM Rooms";

        try {
            ps = conn.prepareStatement(query);
            resultSet = ps.executeQuery();
            if (resultSet.next()) {
                int roomCount = resultSet.getInt("room_count");
                System.out.println("In GuestBookRoomController, the total rooms in database: " + roomCount);
                availableRoomField.setText(Integer.toString(roomCount));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
     // Add listener for checkInDate
		 checkInDate.valueProperty().addListener((observable, oldValue, newValue) -> {
			// Handle check-in date change
		        System.out.println("Check-in date changed: " + newValue);
		        
		    });
		    
	    // Add listener for checkOutDate
	    checkOutDate.valueProperty().addListener((observable, oldValue, newValue) -> {
	        // Handle check-out date change
	        System.out.println("Check-out date changed: " + newValue); 
	    });
	    

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
    void handleBook(ActionEvent event) throws IOException {
    	int noOfGuest = 0;
   		 int noOfSingle = 0;
   		 int noOfDouble = 0;
   		 int noOfDelux = 0;
   		 int noOfPentHouse = 0;
   		 double amount = 0;
   		 Bill bill;
   		 
   		 
   		 try{
   			 noOfGuest = Integer.parseInt(noOfGuestField.getText().trim());
   			 
   		 }catch (NumberFormatException e){
   	    		showAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid positive number for number of guests.");
   	    		return;
   	    	}
   		 
   		 try {
   		 checkInDateValue = checkInDate.getValue();
   		 checkOutDateValue = checkOutDate.getValue();
   		 
   		 }catch(NumberFormatException e){
	    		showAlert(Alert.AlertType.ERROR, "Error", "Please select check in / out date.");
	    		return;
	    	}
   		 
   		
   		 if (checkInDate.getValue() != null && checkOutDate.getValue() != null) {
   			    days = ChronoUnit.DAYS.between(checkInDateValue, checkOutDateValue);
   			    
   			} else {
   				showAlert(Alert.AlertType.ERROR, "Error", "Please select check in and check out dates.");
   	    		return;
   			}
   		 
   		 
   		// days = ChronoUnit.DAYS.between(checkInDateValue, checkOutDateValue);
   		 
   		
   		 
   		 if (singleCheckBox.isSelected()) {
   			 try {
   				 noOfSingle = Integer.parseInt(singleField.getText().trim());
   				 int counter = 0;
   				 for (int i = 0; i < rooms.size(); i++) {
   					 for (Room room : rooms) {
   						 if (room.getRoomType() == RoomTypeName.SINGLE && counter < noOfSingle) {
   							 bookedRooms.add(room);
   							 counter++;
   						 }
   					 }
   				 }
   				 
   				 amount += Double.parseDouble(priceOfSingle.getText().trim()) * noOfSingle;
   				 
   			 }catch (NumberFormatException e){
    	    		showAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid positive number.");
    	    		return;
    	    	}
   		 }
   		 if (doubleCheckBox.isSelected()) {
   			 try {
   				 noOfDouble = Integer.parseInt(doubleField.getText().trim());
   				 int counter = 0;
   				 for (int i = 0; i < rooms.size(); i++) {
   					 for (Room room : rooms) {
   						 if (room.getRoomType() == RoomTypeName.DOUBLE  && counter < noOfDouble) {
   							 bookedRooms.add(room);
   							 counter++;
   						 }
   					 }
   				 }

   				 amount += Double.parseDouble(priceOfDouble.getText().trim()) * noOfDouble;
   				 
   			 }catch (NumberFormatException e){
    	    		showAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid positive number.");
    	    		return;
    	    	}
   		 }
   		 if (deluxCheckBox.isSelected()) {
   			 try {
   				 noOfDelux = Integer.parseInt(deluxField.getText().trim());
   				 int counter = 0;
   				 for (int i = 0; i < rooms.size(); i++) {
   					 for (Room room : rooms) {
   						 if (room.getRoomType() == RoomTypeName.DELUX && counter < noOfDelux) {
   							 bookedRooms.add(room);
   							 counter++;
   						 }
   					 }
   				 }

   				 amount += Double.parseDouble(priceOfDelux.getText().trim()) * noOfDelux;
   				 
   			 }catch (NumberFormatException e){
     	    		showAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid positive number.");
     	    		return;
     	    	}
   		 }
   		 if (pentHouseCheckBox.isSelected()) {
   			 try {
   				 noOfPentHouse = Integer.parseInt(pentHouseField.getText().trim());
   				 int counter = 0;
   				 for (int i = 0; i < rooms.size(); i++) {
   					 for (Room room : rooms) {
   						 if (room.getRoomType() == RoomTypeName.PENTHOUSE && counter < noOfPentHouse) {
   							 bookedRooms.add(room);
   							 counter++;
   						 }
   					 }
   				 }

   				 amount += Double.parseDouble(priceOfPentHouse.getText().trim()) * noOfPentHouse;
   				 
   			 }catch (NumberFormatException e){
      	    		showAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid positive number.");
      	    		return;
      	    	}
   		 }
   		 
   		 if (!singleCheckBox.isSelected() && !doubleCheckBox.isSelected() && !deluxCheckBox.isSelected() && !pentHouseCheckBox.isSelected()) {
   			 showAlert(Alert.AlertType.ERROR, "Error", "Please select room type and enter number of rooms.");
   	    		return;
   		 }
   		
   		 bill = new Bill(amount * days);
   		 
   		 System.out.println("the days are: " + days + "\n");
   		 System.out.println("the bill is: " + bill.getAmount());
   		 
   		 
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
    	
   		 if ((noOfSingle + noOfDouble + noOfDelux + noOfPentHouse) >= (noOfGuest % 2 == 0 ? noOfGuest % 2 : noOfGuest / 2 + 1)) {
   			 
   			 Reservation reservation = new Reservation(
   	                    new Date(), // Book date (current date)
   	                    java.sql.Date.valueOf(checkInDateValue), // Check-in date
   	                    java.sql.Date.valueOf(checkOutDateValue), // Check-out date
   	                    null, // Guest object (to be filled later)
   	                    bookedRooms, // Room object
   	                    bill // Bill object
   	            );
   			 
   		// construct a Guest object
   	    	Guest guest = new Guest(title, fName, lName, address, phone, email); 
   	    	
   	    	reservation.setGuest(guest);
   	    	
   	    	List<Room> bookedRooms = reservation.getRooms();
   	    	
   	    	for (Room room : bookedRooms) {
   	    		rooms.remove(room);
   	    	}
   	    	
   	    	System.out.println("the number of rooms after guest booking is: " + rooms.size());
   	    	
   	    	showAlertWithRoomTypes(Alert.AlertType.INFORMATION, "ABC Hotel reservation inforamtion", reservation, bookedRooms);
   			 
   			 System.out.println("the booking room infor is: " + "\n" + reservation.getBookID() + "\n" +
   					 reservation.getBookDate() + "\n" +
   					 reservation.getCheckInDate() + "\n" + 
   					 reservation.getCheckOutDate() + "\n" +
   					 reservation.getRooms() + "\n" +
   					 reservation.getBill().getAmount());
   		 }
   		 else {
   			 showAlert(Alert.AlertType.ERROR, "Error", "One room can only server less than 2 guests. Please select room type and enter the number of rooms.");
	    		return;
   		 }
    }
    
    @FXML
    void handleAddGuestInfo(ActionEvent event) {
    	
//    	 try {
//    		 int noOfGuest = 0;
//    		 int noOfSingle = 0;
//    		 int noOfDouble = 0;
//    		 int noOfDelux = 0;
//    		 int noOfPentHouse = 0;
//    		 double amount = 0;
//    		 Bill bill;
//    		 
//    		 
//    		 try {
//    		 checkInDateValue = checkInDate.getValue();
//    		 checkOutDateValue = checkOutDate.getValue();
//    		 
//    		 }catch(NumberFormatException e){
// 	    		showAlert(Alert.AlertType.ERROR, "Error", "Please select check in / out date.");
// 	    		return;
// 	    	}
//    		 
//    		
//    		 if (checkInDate.getValue() != null && checkOutDate.getValue() != null) {
//    			    days = ChronoUnit.DAYS.between(checkInDateValue, checkOutDateValue);
//    			    
//    			} else {
//    				showAlert(Alert.AlertType.ERROR, "Error", "Please select check in and check out dates.");
//    	    		return;
//    			}
//    		 
//    		 
//    		// days = ChronoUnit.DAYS.between(checkInDateValue, checkOutDateValue);
//    		 
//    		 
//    		 try{
//    			 noOfGuest = Integer.parseInt(noOfGuestField.getText().trim());
//    			 
//    		 }catch (NumberFormatException e){
//    	    		showAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid positive number.");
//    	    		return;
//    	    	}
//    		 
//    		 if (singleCheckBox.isSelected()) {
//    			 try {
//    				 noOfSingle = Integer.parseInt(singleField.getText().trim());
//    				 int counter = 0;
//    				 for (int i = 0; i < rooms.size(); i++) {
//    					 for (Room room : rooms) {
//    						 if (room.getRoomType() == RoomTypeName.SINGLE && counter < noOfSingle) {
//    							 bookedRooms.add(room);
//    							 counter++;
//    						 }
//    					 }
//    				 }
//    				 
//    				 amount += Double.parseDouble(priceOfSingle.getText().trim()) * noOfSingle;
//    				 
//    			 }catch (NumberFormatException e){
//     	    		showAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid positive number.");
//     	    		return;
//     	    	}
//    		 }
//    		 if (doubleCheckBox.isSelected()) {
//    			 try {
//    				 noOfDouble = Integer.parseInt(doubleField.getText().trim());
//    				 int counter = 0;
//    				 for (int i = 0; i < rooms.size(); i++) {
//    					 for (Room room : rooms) {
//    						 if (room.getRoomType() == RoomTypeName.DOUBLE  && counter < noOfDouble) {
//    							 bookedRooms.add(room);
//    							 counter++;
//    						 }
//    					 }
//    				 }
//
//    				 amount += Double.parseDouble(priceOfDouble.getText().trim()) * noOfDouble;
//    				 
//    			 }catch (NumberFormatException e){
//     	    		showAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid positive number.");
//     	    		return;
//     	    	}
//    		 }
//    		 if (deluxCheckBox.isSelected()) {
//    			 try {
//    				 noOfDelux = Integer.parseInt(deluxField.getText().trim());
//    				 int counter = 0;
//    				 for (int i = 0; i < rooms.size(); i++) {
//    					 for (Room room : rooms) {
//    						 if (room.getRoomType() == RoomTypeName.DELUX && counter < noOfDelux) {
//    							 bookedRooms.add(room);
//    							 counter++;
//    						 }
//    					 }
//    				 }
//
//    				 amount += Double.parseDouble(priceOfDelux.getText().trim()) * noOfDelux;
//    				 
//    			 }catch (NumberFormatException e){
//      	    		showAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid positive number.");
//      	    		return;
//      	    	}
//    		 }
//    		 if (pentHouseCheckBox.isSelected()) {
//    			 try {
//    				 noOfPentHouse = Integer.parseInt(pentHouseField.getText().trim());
//    				 int counter = 0;
//    				 for (int i = 0; i < rooms.size(); i++) {
//    					 for (Room room : rooms) {
//    						 if (room.getRoomType() == RoomTypeName.PENTHOUSE && counter < noOfPentHouse) {
//    							 bookedRooms.add(room);
//    							 counter++;
//    						 }
//    					 }
//    				 }
//
//    				 amount += Double.parseDouble(priceOfPentHouse.getText().trim()) * noOfPentHouse;
//    				 
//    			 }catch (NumberFormatException e){
//       	    		showAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid positive number.");
//       	    		return;
//       	    	}
//    		 }
//    		 
//    		 if (!singleCheckBox.isSelected() && !doubleCheckBox.isSelected() && !deluxCheckBox.isSelected() && !pentHouseCheckBox.isSelected()) {
//    			 showAlert(Alert.AlertType.ERROR, "Error", "Please select room type and enter number of rooms.");
//    	    		return;
//    		 }
//    		
//    		 bill = new Bill(amount * days);
//    		 
//    		 System.out.println("the days are: " + days + "\n");
//    		 System.out.println("the bill is: " + bill.getAmount());
//    		 
//    		 
//    		 
//    		 
//    		 if ((noOfSingle + noOfDouble + noOfDelux + noOfPentHouse) >= (noOfGuest % 2 == 0 ? noOfGuest % 2 : noOfGuest / 2 + 1)) {
//    			 
//    			 Reservation reservation = new Reservation(
//    	                    new Date(), // Book date (current date)
//    	                    java.sql.Date.valueOf(checkInDateValue), // Check-in date
//    	                    java.sql.Date.valueOf(checkOutDateValue), // Check-out date
//    	                    null, // Guest object (to be filled later)
//    	                    bookedRooms, // Room object
//    	                    bill // Bill object
//    	            );
//    			 
//    			 System.out.println("the booking room infor is: " + "\n" + reservation.getBookID() + "\n" +
//    					 reservation.getBookDate() + "\n" +
//    					 reservation.getCheckInDate() + "\n" + 
//    					 reservation.getCheckOutDate() + "\n" +
//    					 reservation.getRooms() + "\n" +
//    					 reservation.getBill().getAmount());
//    			 
//    			 FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/GuestInfo.fxml"));
//                 Parent root = loader.load();
//                 
//                 GuestInfoController controller = loader.getController();
//                 controller.initData(reservation);
//                 
//                 Stage stage = new Stage();
//                 stage.setScene(new Scene(root));
//                 stage.show();
//    		 }
//    		 else {
//    			 showAlert(Alert.AlertType.ERROR, "Error", "One room can only server less than 2 guests. Please select room type and enter the number of rooms.");
// 	    		return;
//    		 }            
//            
//         } catch (IOException e) {
//             e.printStackTrace();
//             // Handle exception gracefully
//         }
    }

    @FXML
    void handleCancel(ActionEvent event) throws IOException {
    	Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	
    	AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/views/WelcomePage.fxml"));
		
    	Scene scene = new Scene(root,484,294);
		primaryStage.setScene(scene);
		primaryStage.show();
    	
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
               "The total amount is: $" + reservation.getBill().getAmount() + "\n\n" + 
               "We have sent a copy of reservation to your email address " + reservation.getGuest().getEmail() + "\n\n" +
               "Thank you for booking with ABC Hotel!");
               
   }
}
