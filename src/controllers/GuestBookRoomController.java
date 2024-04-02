package controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import models.Reservation;
import models.Room;
import models.RoomManager;
import models.RoomTypeName;

public class GuestBookRoomController {

    @FXML
    private Button addGuestInfoButton;

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
    private TextField noOfGuestField;

    @FXML
    private CheckBox pentHouseCheckBox;

    @FXML
    private TextField pentHouseField;

    @FXML
    private CheckBox singleCheckBox;

    @FXML
    private TextField singleField;
    
    @FXML
    private Label priceOfDelux;

    @FXML
    private Label priceOfDouble;

    @FXML
    private Label priceOfPentHouse;

    @FXML
    private Label priceOfSingle;
   
    @FXML
    private List<Room> rooms = new ArrayList<>();
    List<Room> bookedRooms = new ArrayList<>();
//	LocalDate checkInDateValue = checkInDate.getValue();
//    LocalDate checkOutDateValue = checkOutDate.getValue();
    LocalDate checkInDateValue;
    LocalDate checkOutDateValue;
    long days;
       
    @FXML
    public void initialize() {
    	RoomManager roomManager = RoomManager.getInstance();
        rooms = roomManager.getRooms();
        System.out.println("number of rooms in WelcomePage is: " + rooms.size());
    	
    	 if (rooms != null) {
             availableRoomField.setText(Integer.toString(rooms.size()));
         } else {
             availableRoomField.setText("0"); // Default to 0 if rooms list is null
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
    }
    
   
    
    @FXML
    void handleAddGuestInfo(ActionEvent event) {
    	 try {
    		 int noOfGuest = 0;
    		 int noOfSingle = 0;
    		 int noOfDouble = 0;
    		 int noOfDelux = 0;
    		 int noOfPentHouse = 0;
    		 double amount = 0;
    		 Bill bill;
    		 
    		 
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
    		 
    		 
    		 try{
    			 noOfGuest = Integer.parseInt(noOfGuestField.getText().trim());
    			 
    		 }catch (NumberFormatException e){
    	    		showAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid positive number.");
    	    		return;
    	    	}
    		 
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
    		 
    		 
    		 
    		 
    		 if ((noOfSingle + noOfDouble + noOfDelux + noOfPentHouse) >= (noOfGuest % 2 == 0 ? noOfGuest % 2 : noOfGuest / 2 + 1)) {
    			 
    			 Reservation reservation = new Reservation(
    	                    new Date(), // Book date (current date)
    	                    java.sql.Date.valueOf(checkInDateValue), // Check-in date
    	                    java.sql.Date.valueOf(checkOutDateValue), // Check-out date
    	                    null, // Guest object (to be filled later)
    	                    bookedRooms, // Room object
    	                    bill // Bill object
    	            );
    			 
    			 System.out.println("the booking room infor is: " + "\n" + reservation.getBookID() + "\n" +
    					 reservation.getBookDate() + "\n" +
    					 reservation.getCheckInDate() + "\n" + 
    					 reservation.getCheckOutDate() + "\n" +
    					 reservation.getRooms() + "\n" +
    					 reservation.getBill().getAmount());
    			 
    			 FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/GuestInfo.fxml"));
                 Parent root = loader.load();
                 
                 GuestInfoController controller = loader.getController();
                 controller.initData(reservation);
                 
                 Stage stage = new Stage();
                 stage.setScene(new Scene(root));
                 stage.show();
    		 }
    		 else {
    			 showAlert(Alert.AlertType.ERROR, "Error", "One room can only server less than 2 guests. Please select room type and enter the number of rooms.");
 	    		return;
    		 }            
            
         } catch (IOException e) {
             e.printStackTrace();
             // Handle exception gracefully
         }
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

}
