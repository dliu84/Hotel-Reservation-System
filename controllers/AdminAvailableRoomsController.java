package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
import models.Room;
import models.RoomTypeName;
import models.SqliteConnection;

public class AdminAvailableRoomsController implements Initializable{

	@FXML
    private TableView<Room> availableRoomsTable;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField noOfRooms;

    @FXML
    private TableColumn<Room, Integer> roomIdColumn;
    
    @FXML
    private TableColumn<Room, Double> priceColumn;

    @FXML
    private TableColumn<Room, RoomTypeName> roomTypeColumn;
    
    Connection conn;
    
    ObservableList<Room> availableRooms = FXCollections.observableArrayList();

    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	conn = SqliteConnection.Connector();
    	
    	if (conn == null) {
    		System.out.println("Connection is not successful in available rooms controller.");
    		System.exit(1);
    	}
    	else {
    		System.out.println("Connection is successful in available rooms controllerr!!!");
    	}
    	
    	loadAvailableRooms();		
	}
    
    public void loadAvailableRooms() {
    	
    	PreparedStatement ps = null;
        ResultSet resultSet = null;
        String query = "SELECT COUNT(*) AS availableRooms_count FROM Rooms where available = 1";
        
        try {
        	ps = conn.prepareStatement(query);
            resultSet = ps.executeQuery();
            
            if (resultSet.next()) {
                int availableRoomsCount = resultSet.getInt("availableRooms_count");
                System.out.println("In available rooms controller , the number of available rooms in database is: " + availableRoomsCount);
                noOfRooms.setText(Integer.toString(availableRoomsCount));
            }
        }catch(SQLException e){
        	 e.printStackTrace();
        }
        	
    	PreparedStatement psAvailableRooms = null;
        ResultSet resultSetAvailableRooms = null;
        String queryAvailableRooms = "SELECT roomID, price, roomType FROM Rooms WHERE available = 1"; 
        
        try {
        	psAvailableRooms = conn.prepareStatement(queryAvailableRooms);
        	resultSetAvailableRooms = psAvailableRooms.executeQuery();
        	
        	while (resultSetAvailableRooms.next()) {
                int roomID = resultSetAvailableRooms.getInt("roomID");
                double price = resultSetAvailableRooms.getDouble("price");
                RoomTypeName roomType = RoomTypeName.valueOf(resultSetAvailableRooms.getString("roomType"));
                
             // Initialize the columns with appropriate types
                roomIdColumn.setCellValueFactory(new PropertyValueFactory<>("roomID"));
                priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
                roomTypeColumn.setCellValueFactory(new PropertyValueFactory<>("roomType"));

                // Create a Room object with the retrieved data
                availableRooms.add(new Room(roomID, price, roomType));

                // Add the room to the TableView
                availableRoomsTable.setItems(availableRooms);
            }
        }catch(SQLException e){
       	 e.printStackTrace();
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
}

