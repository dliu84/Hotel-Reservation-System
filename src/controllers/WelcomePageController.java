package controllers;

import java.io.IOException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import models.Room;
import models.RoomManager;

public class WelcomePageController {

    @FXML
    private Button admin;

    @FXML
    private Button guest;
    
    
    private List<Room> rooms;
    
//    public void setRooms(List<Room> rooms) {
//        this.rooms = rooms;
//        System.out.println("number of rooms in WelcomePage is: " + rooms.size());
//    }
    
    @FXML
    public void initialize() {
    	RoomManager roomManager = RoomManager.getInstance();
        rooms = roomManager.getRooms();
        System.out.println("number of rooms in WelcomePage is: " + rooms.size());
    	
    }

    @FXML
    void handleAdmin(ActionEvent event) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AdminLogin.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) admin.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleGuest(ActionEvent event) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/GuestBookRoom.fxml"));
            Parent root = loader.load();
            
            GuestBookRoomController guestBookRoomController = loader.getController();
           // guestBookRoomController.setRooms(rooms);
            
            Stage stage = (Stage) guest.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
