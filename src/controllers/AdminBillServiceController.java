package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

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
import models.SqliteConnection;

public class AdminBillServiceController {

   @FXML
    private TextField bookingIdField;

//    @FXML
//    private TextField nameField;

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
//         String name = nameField.getText().trim();
         String phone = phoneField.getText().trim();

         // Build the SQL query based on the input provided
         String query = "SELECT * FROM Reservations WHERE 1=1"; // Start with a true condition

         if (!bookingId.isEmpty()) {
             query += " AND bookID = ?";
         }
//         if (!name.isEmpty()) {
//             query += " AND guestID IN (SELECT guestID FROM Guests WHERE firstName LIKE ? OR lastName LIKE ?)";
//         }
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
//             if (!name.isEmpty()) {
//                 preparedStatement.setString(parameterIndex++, "%" + name + "%");
//                 preparedStatement.setString(parameterIndex++, "%" + name + "%");
//             }
             if (!phone.isEmpty()) {
                 preparedStatement.setString(parameterIndex++, phone);
             }

             // Execute the query
             ResultSet resultSet = preparedStatement.executeQuery();

             // Process the result set or display it in a table, etc.
             // For example:
             while (resultSet.next()) {
            	 
                 int bookId = resultSet.getInt("bookID");
                 Date checkInDate = resultSet.getDate("checkInDate");
                 Date checkOutDate = resultSet.getDate("checkOutDate");
                 int guestID = resultSet.getInt("guestID");
                 int billID = resultSet.getInt("billID");
                 
//                 String firstName = resultSet.getString("firstName");
//                 String lastName = resultSet.getString("lastName");
//                 System.out.println("Booking ID: " + bookId + ", Guest Name: " + firstName + " " + lastName);
                 System.out.println("The booking id is: " + bookId);
                 System.out.println("The check in date is: " + checkInDate);
                 System.out.println("The check out date is: " + checkOutDate);
                 System.out.println("The guest id is: " + guestID);
                 System.out.println("The bill id is: " + billID);
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

}
