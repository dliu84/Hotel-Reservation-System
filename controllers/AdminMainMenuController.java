package controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AdminMainMenuController {

    @FXML
    private Button availableRoomsButton;

    @FXML
    private Button billServiceButton;

    @FXML
    private Button bookRoomButton;

    @FXML
    private Button currBookingsButton;

    @FXML
    private Button exitButton;

    @FXML
    void handleAvailableRooms(ActionEvent event) {
    	 try {
    		((Node)event.getSource()).getScene().getWindow().hide();
   			Stage primaryStage = new Stage();
   			FXMLLoader loader = new FXMLLoader();
   			AnchorPane root = loader.load(getClass().getResource("/views/AdminAvailableRooms.fxml").openStream());
   			Scene scene = new Scene(root);
   			primaryStage.setScene(scene);
   			primaryStage.show();
         } catch (IOException e) {
             e.printStackTrace();
             // Handle exception gracefully
         }
    }

    @FXML
    void handleBillService(ActionEvent event) {
    	 try {
    		((Node)event.getSource()).getScene().getWindow().hide();
  			Stage primaryStage = new Stage();
  			FXMLLoader loader = new FXMLLoader();
  			AnchorPane root = loader.load(getClass().getResource("/views/AdminBillService.fxml").openStream());
  			Scene scene = new Scene(root);
  			primaryStage.setScene(scene);
  			primaryStage.show();
         } catch (IOException e) {
             e.printStackTrace();
             // Handle exception gracefully
         }
    }

    @FXML
    void handleBookRoom(ActionEvent event) throws IOException {
    	 try {
    		((Node)event.getSource()).getScene().getWindow().hide();
 			Stage primaryStage = new Stage();
 			FXMLLoader loader = new FXMLLoader();
 			AnchorPane root = loader.load(getClass().getResource("/views/AdminBookRoom.fxml").openStream());
 			Scene scene = new Scene(root);
 			primaryStage.setScene(scene);
 			primaryStage.show();
         } catch (IOException e) {
             e.printStackTrace();
             // Handle exception gracefully
         }
    }

    @FXML
    void handleCurrBookings(ActionEvent event) {
    	 try {
    		 	((Node)event.getSource()).getScene().getWindow().hide();
    			Stage primaryStage = new Stage();
    			FXMLLoader loader = new FXMLLoader();
    			AnchorPane root = loader.load(getClass().getResource("/views/AdminCurrentBookings.fxml").openStream());
    			Scene scene = new Scene(root);
    			primaryStage.setScene(scene);
    			primaryStage.show();
         } catch (IOException e) {
             e.printStackTrace();
             // Handle exception gracefully
         }
    }

    @FXML
    void handleExit(ActionEvent event) throws IOException {   	
    	((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		AnchorPane root = loader.load(getClass().getResource("/views/WelcomePage.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
    }
}
