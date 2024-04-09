package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.CurrentBooking;
import models.Reservation;
import models.Room;
import models.RoomTypeName;
import models.SqliteConnection;

public class AdminCurrentBookingsController implements Initializable {

	@FXML
    private TableColumn<CurrentBooking, Double> amountColumn;

    @FXML
    private TableColumn<CurrentBooking, Integer> bookingNoColumn;

    @FXML
    private Button cancelButton;

    @FXML
    private TableView<CurrentBooking> currentBookingsTable;

    @FXML
    private Button deleteButton;

    @FXML
    private TableColumn<CurrentBooking, String> nameColumn;

    @FXML
    private TextField noOfBookingsField;

    @FXML
    private TableColumn<CurrentBooking, Integer> noOfDaysColumn;
    
    Connection conn;
    int bookID;
    int guestID;
    Date checkInDate;
    Date checkOutDate;
    int days;
    int billID;
    String guestName;
    List<Integer> roomIDs = new ArrayList<>();
    List<Room> rooms = new ArrayList<>();
    ObservableList<CurrentBooking> bookings = FXCollections.observableArrayList();
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	conn = SqliteConnection.Connector();
    	
    	if (conn == null) {
    		System.out.println("Connection is not successful in current booking controller.");
    		System.exit(1);
    	}
    	else {
    		System.out.println("Connection is successful in current booking controllerr!!!");
    	}
    	
    	PreparedStatement ps = null;
        ResultSet resultSet = null;
        String query = "SELECT COUNT(*) AS reservation_count FROM Reservations";
        
        PreparedStatement psBookings = null;
        ResultSet resultSetBookings = null;
        String queryBookings = "SELECT * FROM Reservations";

        try {
            ps = conn.prepareStatement(query);
            resultSet = ps.executeQuery();
            if (resultSet.next()) {
                int reservationCount = resultSet.getInt("reservation_count");
                System.out.println("In current booking controller , the total reservations in database: " + reservationCount);
                noOfBookingsField.setText(Integer.toString(reservationCount));
            }
                
             psBookings = conn.prepareStatement(queryBookings);  
             resultSetBookings = psBookings.executeQuery();
             
             while (resultSetBookings.next()) {
            	 bookID = resultSetBookings.getInt("bookID");
            	 guestID = resultSetBookings.getInt("guestID");
            	 checkInDate = resultSetBookings.getDate("checkInDate");
            	 checkOutDate = resultSetBookings.getDate("checkOutDate");
            	 billID = resultSetBookings.getInt("billID");
            	 
            	 guestName = fetchGuestName(conn, guestID);
            	 
            	 double amount = fetchBillAmount(conn, billID);
            	             	 
                 System.out.println("Booking No: " + bookID + " guest name: " + guestName + " amount: " + amount + " days: " + 
                		 (checkOutDate.getTime() - checkInDate.getTime()) / (1000 * 60 * 60 * 24) + "\n\n");
                 
                 long diffInMillies = Math.abs(checkOutDate.getTime() - checkInDate.getTime());
                 int days = (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                 
              // Initialize the columns with appropriate types
                 bookingNoColumn.setCellValueFactory(new PropertyValueFactory<>("bookID"));
                 nameColumn.setCellValueFactory(new PropertyValueFactory<>("guestName"));
                 amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
                 noOfDaysColumn.setCellValueFactory(new PropertyValueFactory<>("days"));
                

                 bookings.add(new CurrentBooking(bookID, guestName, amount, days));
             }              
            
            
            currentBookingsTable.setItems(bookings);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (ps != null) ps.close();
                
                if (resultSetBookings != null) resultSetBookings.close();
                if (psBookings != null) psBookings.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
	}

    @FXML
    void handleCancel(ActionEvent event) throws IOException {
    	Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	
    	AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/views/AdminMainMenu.fxml"));
		
    	Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
    }

    @FXML
    void handleDelete(ActionEvent event) {

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
    
    public String fetchGuestName(Connection connection, int guestID) throws SQLException {
	    String guestName = "";
	    String query = "SELECT title, firstName, lastName, phone FROM Guests WHERE guestID = ?";
	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setInt(1, guestID);
	        ResultSet resultSet = preparedStatement.executeQuery();
	        if (resultSet.next()) {
	        	String title = resultSet.getString("title");
	            String firstName = resultSet.getString("firstName");
	            String lastName = resultSet.getString("lastName");
	            int phone = resultSet.getInt("phone");
	            guestName = title + ". " + firstName + " " + lastName + " - phone number: " + phone;
	        }
	    }
	    return guestName;
	}
    
    public List<Room> fetchRooms(Connection connection, int reservationID) throws SQLException {
//	    List<Integer> roomIDs = new ArrayList<>();
//  	    List<Room> rooms = new ArrayList<>();
  	    String query = "SELECT roomID FROM ReservationRooms WHERE reservationID = ?";
  	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
  	        preparedStatement.setInt(1, reservationID);
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


