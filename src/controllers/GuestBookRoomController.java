package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    
    List<Room> bookedRooms = new ArrayList<>();
    LocalDate checkInDateValue;
    LocalDate checkOutDateValue;
    long days;
    int discount = 0;
    double originalTotal;
    Connection conn;
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	conn = SqliteConnection.Connector();
    	if (conn == null) {
    		System.out.println("Connection is not successful in AdminBookRoomController.");
    		System.exit(1);
    	}
    	else {
    		System.out.println("Connection is successful in AdminBookRoomController!!!");
    	}
    	
    	PreparedStatement ps = null;
        ResultSet resultSet = null;
        //String query = "SELECT COUNT(*) AS room_count FROM Rooms";
        String query = "SELECT COUNT(*) AS room_count FROM Rooms WHERE available = 1";

        try {
            ps = conn.prepareStatement(query);
            resultSet = ps.executeQuery();
            if (resultSet.next()) {
                int roomCount = resultSet.getInt("room_count");
                System.out.println("In AdminBookRoomController, the total rooms in database: " + roomCount);
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
        
		 checkInDate.valueProperty().addListener((observable, oldValue, newValue) -> {
			
		        System.out.println("Check-in date changed: " + newValue);
		        
		    });
		    
	    
	    checkOutDate.valueProperty().addListener((observable, oldValue, newValue) -> {
	        
	        System.out.println("Check-out date changed: " + newValue); 
	    });
	    
        emailField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isValidEmail(newValue)) {
                
                emailField.setStyle("-fx-border-color: red;");
            } else {
                emailField.setStyle(""); 
            }
        });

        firstNameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isValidName(newValue)) {
                
                firstNameField.setStyle("-fx-border-color: red;");
            } else {
                firstNameField.setStyle(""); 
            }
        });

        lastNameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isValidName(newValue)) {
                
                lastNameField.setStyle("-fx-border-color: red;");
            } else {
                lastNameField.setStyle(""); 
            }
        });
        
        addressField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isValidAddress(newValue)) {
                
                addressField.setStyle("-fx-border-color: red;");
            } else {
                addressField.setStyle(""); 
            }
        });

        phoneField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isValidPhoneNumber(newValue)) {
                
                phoneField.setStyle("-fx-border-color: red;");
            } else {
                phoneField.setStyle(""); 
            }
        });

        titleField.textProperty().addListener((observable, oldValue, newValue) -> {
        	 if (!isValidTitle(newValue)) {
                 
                 titleField.setStyle("-fx-border-color: red;");
             } else {
                 titleField.setStyle(""); 
             }
        });	
	}
    
    private boolean isValidEmail(String email) {
        
        return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }
    
    private boolean isValidName(String name) {
       
        return name.matches("[a-zA-Z]+");
    }
    
    private boolean isValidAddress(String address) {
        
    	return address.matches("^[0-9A-Za-z\\s\\.,#\\-]+$");
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        // Basic phone number validation (digits only)
    	return phoneNumber.matches("\\d+");
    }
    
    private boolean isValidTitle(String title) {
       
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
		 
		 days = ChronoUnit.DAYS.between(checkInDateValue, checkOutDateValue);
		 System.out.println("the days are: " + days);
		 		 
		 if (singleCheckBox.isSelected()) {
			 try {
			        noOfSingle = Integer.parseInt(singleField.getText().trim());
			        if (noOfSingle <= 0) {
			            showAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid positive number.");
			            return;
			        }

			        // Retrieve SINGLE rooms from the database
			        List<Room> singleRooms = retrieveRoomsFromDatabase(RoomTypeName.SINGLE, noOfSingle); // This method should retrieve SINGLE rooms from the database

			        if (singleRooms.size() < noOfSingle) {
			            showAlert(Alert.AlertType.ERROR, "Error", "There are not enough SINGLE rooms available.");
			            return;
			        }

			        // Add the retrieved SINGLE rooms to the bookedRooms list
			        bookedRooms.addAll(singleRooms);
			        
			     // Update the availability of SINGLE rooms in the Rooms table
			        updateRoomAvailability(singleRooms);
			        
			        amount += Double.parseDouble(priceOfSingle.getText().trim()) * noOfSingle;
			        
			    } catch (NumberFormatException e) {
			        showAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid positive number.");
			        return;
			    }
			}

		 
		 if (doubleCheckBox.isSelected()) {
			 try {
				 noOfDouble = Integer.parseInt(doubleField.getText().trim());
				 
				 if (noOfDouble <= 0) {
			            showAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid positive number.");
			            return;
			        }

			        // Retrieve SINGLE rooms from the database
			        List<Room> doubleRooms = retrieveRoomsFromDatabase(RoomTypeName.DOUBLE, noOfDouble); // This method should retrieve SINGLE rooms from the database

			        if (doubleRooms.size() < noOfDouble) {
			            showAlert(Alert.AlertType.ERROR, "Error", "There are not enough DOUBLE rooms available.");
			            return;
			        }

			        // Add the retrieved SINGLE rooms to the bookedRooms list
			        bookedRooms.addAll(doubleRooms);
			        
			     // Update the availability of SINGLE rooms in the Rooms table
			        updateRoomAvailability(doubleRooms);

			        amount += Double.parseDouble(priceOfDouble.getText().trim()) * noOfDouble;
				 
			 }catch (NumberFormatException e){
	    		showAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid positive number.");
	    		return;
	    	}
		 }
		 if (deluxCheckBox.isSelected()) {
			 try {
				 noOfDelux = Integer.parseInt(deluxField.getText().trim());
				 
				 if (noOfDelux <= 0) {
			            showAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid positive number.");
			            return;
			        }

			        // Retrieve SINGLE rooms from the database
			        List<Room> deluxRooms = retrieveRoomsFromDatabase(RoomTypeName.DELUX, noOfDelux); // This method should retrieve SINGLE rooms from the database

			        if (deluxRooms.size() < noOfDelux) {
			            showAlert(Alert.AlertType.ERROR, "Error", "There are not enough DELUX rooms available.");
			            return;
			        }

		        // Add the retrieved SINGLE rooms to the bookedRooms list
		        bookedRooms.addAll(deluxRooms);
		        
		     // Update the availability of SINGLE rooms in the Rooms table
		        updateRoomAvailability(deluxRooms);

				 amount += Double.parseDouble(priceOfDelux.getText().trim()) * noOfDelux;
				 
			 }catch (NumberFormatException e){
 	    		showAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid positive number.");
 	    		return;
 	    	}
		 }
		 
		 if (pentHouseCheckBox.isSelected()) {
			 try {
				 noOfPentHouse = Integer.parseInt(pentHouseField.getText().trim());
				 
				 if (noOfPentHouse <= 0) {
			            showAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid positive number.");
			            return;
			        }

			        // Retrieve SINGLE rooms from the database
			        List<Room> pentHouseRooms = retrieveRoomsFromDatabase(RoomTypeName.PENTHOUSE, noOfPentHouse); // This method should retrieve SINGLE rooms from the database

			        if (pentHouseRooms.size() < noOfPentHouse) {
			            showAlert(Alert.AlertType.ERROR, "Error", "There are not enough Pent House rooms available.");
			            return;
			        }

		        // Add the retrieved SINGLE rooms to the bookedRooms list
		        bookedRooms.addAll(pentHouseRooms);
		        
		     // Update the availability of SINGLE rooms in the Rooms table
		        updateRoomAvailability(pentHouseRooms);

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
		
		 originalTotal = amount * days;
		 
		 bill = new Bill(amount * days * (100 - discount) / 100);
		 
		 System.out.println("the days are: " + days + "\n");
		 System.out.println("the number of rooms is: " + bookedRooms.size());
		 System.out.println("the bill is: " + bill.getAmount());
		 System.out.println("the discount is: " + discount + "%");
		 
		 String title;
    	String fName;
    	String lName;
    	String address;
    	int phone;
    	String email;
    	
    	if (titleField.getText().trim().isEmpty() || firstNameField.getText().trim().isEmpty() ||
    			lastNameField.getText().trim().isEmpty() || addressField.getText().trim().isEmpty() ||
    			phoneField.getText().trim().isEmpty() || emailField.getText().trim().isEmpty()) {
    		showAlert(Alert.AlertType.ERROR, "Error", "Please fill in guest information.");
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
    	
    	Guest guest = new Guest(title, fName, lName, address, phone, email); 
    	
    	System.out.println("Admin book room - Guest is: \n" + 
    			"guest title: " + guest.getTitle() + "\n" + 
    			"guest name: " + guest.getFirstName() + " " + guest.getLastName() + "\n" + 
    			"guest address: " + guest.getAddress() + "\n" + 
    			"guest phone: " + guest.getPhone() + "\n" + 
    			"guest email: " + guest.getEmail());
    	
    	
    	 int guestID = -1;
    	 int billID = -1;
    	
    	try {
    		// insert into Guests table
    		PreparedStatement psGuest = conn.prepareStatement("INSERT INTO Guests (title, firstName, lastName, address, phone, email) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
    		
    		psGuest.setString(1, guest.getTitle());
    		psGuest.setString(2, guest.getFirstName());
    		psGuest.setString(3, guest.getLastName());
    		psGuest.setString(4, guest.getAddress());
    		psGuest.setInt(5, guest.getPhone());
    		psGuest.setString(6, guest.getEmail());
    		psGuest.executeUpdate();
    		
    		// Retrieve the auto-generated guestID
    	    ResultSet generatedGuestKeys = psGuest.getGeneratedKeys();
    	   
    	    if (generatedGuestKeys.next()) {
    	        guestID = generatedGuestKeys.getInt(1);
    	    } else {
    	        // Handle the case where the auto-generated key was not retrieved
    	        throw new SQLException("Failed to retrieve auto-generated guest ID.");
    	    }
    	    
    	    // Insert bill into Bills table
    	    PreparedStatement psBill = conn.prepareStatement("INSERT INTO Bills (amount, status) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
    	    psBill.setDouble(1, bill.getAmount());
    	    psBill.setString(2,  "outstanding");
    	    psBill.executeUpdate();

    	    // Retrieve the auto-generated billID
    	    ResultSet generatedBillKeys = psBill.getGeneratedKeys();
    	    
    	    if (generatedBillKeys.next()) {
    	        billID = generatedBillKeys.getInt(1);
    	    } else {
    	        // Handle the case where the auto-generated key was not retrieved
    	        throw new SQLException("Failed to retrieve auto-generated bill ID.");
    	    }
    	        	    
    	    // Insert reservation into Reservations table (including guestID and billID)
            PreparedStatement psReservation = conn.prepareStatement("INSERT INTO Reservations (bookDate, checkInDate, checkOutDate, guestID, billID) VALUES (?, ?, ?, ?, ?)");
            psReservation.setDate(1, new java.sql.Date(System.currentTimeMillis())); 
            psReservation.setDate(2, java.sql.Date.valueOf(checkInDate.getValue())); 
            psReservation.setDate(3, java.sql.Date.valueOf(checkOutDate.getValue()));             
            psReservation.setInt(4, guestID);
            psReservation.setInt(5, billID);
            psReservation.executeUpdate();

            // Retrieve the auto-generated reservationID
            ResultSet generatedReservationKeys = psReservation.getGeneratedKeys();
            int reservationID = -1;
            if (generatedReservationKeys.next()) {
                reservationID = generatedReservationKeys.getInt(1);
            } else {
                // Handle the case where the auto-generated key was not retrieved
                throw new SQLException("Failed to retrieve auto-generated reservation ID.");
            }

            // Insert rooms into ReservationRooms table
            for (Room room : bookedRooms) {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO ReservationRooms (reservationID, roomID) VALUES (?, ?)");
                ps.setInt(1, reservationID);
                ps.setInt(2, room.getRoomID());
                ps.executeUpdate();
                ps.close();
            }
            
            if ((noOfSingle + noOfDouble + noOfDelux + noOfPentHouse) >= (noOfGuest % 2 == 0 ? noOfGuest % 2 : noOfGuest / 2 + 1)) {
   			 
   			 Reservation reservation = new Reservation(
   	                    new Date(), // Book date (current date)
   	                    java.sql.Date.valueOf(checkInDateValue), // Check-in date
   	                    java.sql.Date.valueOf(checkOutDateValue), // Check-out date
   	                    guest, // Guest object (to be filled later)
   	                    bookedRooms, // Room object
   	                    bill // Bill object
   	            );
   			 
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
    		
    	} catch (SQLException e) {
    	    e.printStackTrace();
    	    // Handle any SQL exceptions here
    	}  	
    }

    @FXML
    void handleCancel(ActionEvent event) throws IOException {
    	Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	
    	AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/views/WelcomePage.fxml"));
		
    	Scene scene = new Scene(root);
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
    
    public int getDiscount() {
    	return discount;
    }
    
    public double getOriginalTotal() {
    	return originalTotal;
    }

    public List<Room> retrieveRoomsFromDatabase(RoomTypeName roomType, int count) {
        List<Room> rooms = new ArrayList<>();

        // SQL query to retrieve rooms of a specific type from the database
        String query = "SELECT * FROM Rooms WHERE roomType = ? AND available = 1 LIMIT ?";

        try (
            Connection connection = SqliteConnection.Connector();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            // Set parameters for the prepared statement
            preparedStatement.setString(1, roomType.toString());
            preparedStatement.setInt(2, count);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Process the result set
            while (resultSet.next()) {
                // Assuming Room object can be constructed from the result set
                Room room = new Room(
                    resultSet.getInt("roomID"),
                    resultSet.getDouble("price"),
                    RoomTypeName.valueOf(resultSet.getString("roomType"))
                );
                rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any SQL exceptions here
        }

        return rooms;
    }
    
    public void updateRoomAvailability(List<Room> rooms) {
        // SQL query to update the availability of rooms in the Rooms table
        String query = "UPDATE Rooms SET available = 0 WHERE roomID = ?";

        try (
            Connection connection = SqliteConnection.Connector();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            for (Room room : rooms) {
                preparedStatement.setInt(1, room.getRoomID());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any SQL exceptions here
        }
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
                  "With " +  discount + "%" + " discount" + "\n\n" +
                  "We have sent a copy of reservation to your email address " + reservation.getGuest().getEmail() + "\n\n" +
                  "Thank you for booking with ABC Hotel!");                 
      }
}
