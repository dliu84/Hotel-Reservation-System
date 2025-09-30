package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Room;

public class WelcomePageController implements Initializable {

    @FXML
    private Button admin;

    @FXML
    private Button guest;
    
    private List<Room> rooms;

    @FXML
    void handleAdmin(ActionEvent event) {
    	try {
    		((Node)event.getSource()).getScene().getWindow().hide();
   			Stage primaryStage = new Stage();
   			FXMLLoader loader = new FXMLLoader();
   			AnchorPane root = loader.load(getClass().getResource("/views/AdminLogin.fxml").openStream());
   			Scene scene = new Scene(root);
   			primaryStage.setScene(scene);
   			primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleGuest(ActionEvent event) {
    	try {
    		((Node)event.getSource()).getScene().getWindow().hide();
   			Stage primaryStage = new Stage();
   			FXMLLoader loader = new FXMLLoader();
   			AnchorPane root = loader.load(getClass().getResource("/views/GuestBookRoom.fxml").openStream());
   			Scene scene = new Scene(root);
   			primaryStage.setScene(scene);
   			primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
}
