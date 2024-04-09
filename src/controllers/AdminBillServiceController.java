package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Room;
import models.RoomTypeName;
import models.SqliteConnection;

public class AdminBillServiceController {

   @FXML
    private TextField bookingIdField;

    @FXML
    private TextField phoneField;
    
    @FXML
    private Button cancelButton;

    @FXML
    void handleCancel(ActionEvent event) throws IOException {
    	Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	
    	AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/views/AdminMainMenu.fxml"));
		
    	Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
    }

    @FXML
    void handleSearch(ActionEvent event) {
    	 String bookingId = bookingIdField.getText().trim();
         String phone = phoneField.getText().trim();

         // Build the SQL query based on the input provided
         String query = "SELECT * FROM Reservations WHERE 1=1"; // Start with a true condition

         if (!bookingId.isEmpty()) {
             query += " AND bookID = ?";
         }

         if (!phone.isEmpty()) {
             query += " AND guestID IN (SELECT guestID FROM Guests WHERE phone = ?)";
         }

         try (Connection connection = SqliteConnection.Connector();
              PreparedStatement preparedStatement = connection.prepareStatement(query)) {

             int parameterIndex = 1;

             // Set parameters for the prepared statement
             if (!bookingId.isEmpty()) {
                 preparedStatement.setString(parameterIndex++, bookingId);
             }

             if (!phone.isEmpty()) {
                 preparedStatement.setString(parameterIndex++, phone);
             }

             // Execute the query
             ResultSet resultSet = preparedStatement.executeQuery();

             while (resultSet.next()) {
            	 
                 int bookID = resultSet.getInt("bookID");
                 Date checkInDate = resultSet.getDate("checkInDate");
                 Date checkOutDate = resultSet.getDate("checkOutDate");
                 int guestID = resultSet.getInt("guestID");
                 int billID = resultSet.getInt("billID");
                 
                 System.out.println("The booking id is: " + bookID);
                 System.out.println("The check in date is: " + checkInDate);
                 System.out.println("The check out date is: " + checkOutDate);
                 System.out.println("The guest id is: " + guestID);
                 System.out.println("The bill id is: " + billID);
                 
                 String guestName = fetchGuestName(connection, guestID);
                 
                 System.out.println("The guest name is: " + guestName);
                 
                 double amount = fetchBillAmount(connection, billID);
                 
                 System.out.println("The bill amount is: " + amount);
                 
                 List<Room> bookedRoom = fetchRooms(connection, bookID);
                 
                 for (Room room : bookedRoom) {
                	 System.out.println("room info: " + room.getRoomID() + " " + room.getRoomType() + " " + room.getPrice());
                 }
                 
             }

         } catch (SQLException e) {
             e.printStackTrace();
             showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while searching for bookings.");
         }
    }
    
    
    // helper function to show the Alert on the screen
       public void showAlert(Alert.AlertType type, String title, String content) {
           Alert alert = new Alert(type);
           alert.setTitle(title);
           alert.setHeaderText(null);
           alert.setContentText(content);
           alert.showAndWait();
       }

       public String fetchGuestName(Connection connection, int guestID) throws SQLException {
    	    String guestName = "";
    	    String query = "SELECT firstName, lastName FROM Guests WHERE guestID = ?";
    	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
    	        preparedStatement.setInt(1, guestID);
    	        ResultSet resultSet = preparedStatement.executeQuery();
    	        if (resultSet.next()) {
    	            String firstName = resultSet.getString("firstName");
    	            String lastName = resultSet.getString("lastName");
    	            guestName = firstName + " " + lastName;
    	        }
    	    }
    	    return guestName;
    	}
       
       public double fetchBillAmount(Connection connection, int billID) throws SQLException {
   	    double amount = 0;
   	    String query = "SELECT amount FROM Bills WHERE billID = ?";
   	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
   	        preparedStatement.setInt(1, billID);
   	        ResultSet resultSet = preparedStatement.executeQuery();
   	        if (resultSet.next()) {
   	            amount = resultSet.getDouble("amount");
   	        }
   	    }
   	    return amount;
       }
       
       public List<Room> fetchRooms(Connection connection, int bookID) throws SQLException {
    	    List<Integer> roomIDs = new ArrayList<>();
      	    List<Room> rooms = new ArrayList<>();
      	    String query = "SELECT roomID FROM ReservationRooms WHERE bookID = ?";
      	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
      	        preparedStatement.setInt(1, bookID);
      	        ResultSet resultSet = preparedStatement.executeQuery();
      	        while (resultSet.next()) {
      	            roomIDs.add(resultSet.getInt("roomID"));
      	        }
      	    }
      	     	    
      	// Query to fetch rooms with price and roomType from the Rooms table
      	    String queryRooms = "SELECT roomID, price, roomType FROM Rooms WHERE roomID = ?";
      	    
      	    try (PreparedStatement preparedStatement = connection.prepareStatement(queryRooms)) {
      	        for (int id : roomIDs) {
      	            preparedStatement.setInt(1, id);
      	            ResultSet resultSet = preparedStatement.executeQuery();
      	            if (resultSet.next()) {
      	                int roomId = resultSet.getInt("roomID");
      	                double price = resultSet.getDouble("price");
      	                RoomTypeName roomType = RoomTypeName.valueOf(resultSet.getString("roomType"));
      	                // Create a Room object and add it to the list
      	                Room room = new Room(roomId, price, roomType);
      	                rooms.add(room);
      	            }
      	        }
      	    }
      	    
      	    return rooms;
      	}
       
}
