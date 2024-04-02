package controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AdminLoginController {

    @FXML
    private Button cancelButton;

    @FXML
    private TextField idField;

    @FXML
    private Button loginButton;

    @FXML
    private TextField passwordField;

    @FXML
    void handleCancel(ActionEvent event) throws IOException {
    	Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	
    	AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/views/WelcomePage.fxml"));
		
    	Scene scene = new Scene(root,484,294);
		primaryStage.setScene(scene);
		primaryStage.show();
    }

    @FXML
    void handleLogin(ActionEvent event) throws IOException {
    	Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	
    	AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/views/AdminMainMenu.fxml"));
		
    	Scene scene = new Scene(root,366,334);
		primaryStage.setScene(scene);
		primaryStage.show();
    }

}



