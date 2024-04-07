package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Login;

public class AdminLoginController implements Initializable{

    @FXML
    private Button cancelButton;

    @FXML
    private TextField idField;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordField;
    
    @FXML
    private Label isConnected;
    
    public Login login = new Login();
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (login.isDbConnected()) {
//			isConnected.setText("database is connected");
			System.out.println("database.db is connected!!!!");
		}else {
//			isConnected.setText("database is NOT connected");
			System.out.println("database.db is not connected.");
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

    @FXML
    void handleLogin(ActionEvent event) throws IOException {
    	int id;
    	String password;
    	
    	if (idField.getText().trim().isEmpty() || passwordField.getText().trim().isEmpty()) {
    		showAlert(Alert.AlertType.ERROR, "ERROR", "Please enter User Id and Password.");
    		return;
    	}
    	
    	try {
    		id = Integer.parseInt(idField.getText().trim());
    		password = passwordField.getText().trim();
    	}catch(NumberFormatException e){
    		showAlert(Alert.AlertType.ERROR, "ERROR", "Please enter a valid integer for User Id, and valid text for Password.");
    		return;
    	} 
    	
    	
    	try {
			if(login.isLoggedIn(id, password)) {
				System.out.println("User id and password is correct.");
				
//				Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
//		    	AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/views/AdminMainMenu.fxml"));
//		    	Scene scene = new Scene(root,366,334);
//				primaryStage.setScene(scene);
//				primaryStage.show();
				
				((Node)event.getSource()).getScene().getWindow().hide();
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				AnchorPane root = loader.load(getClass().getResource("/views/AdminMainMenu.fxml").openStream());
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.show();
			}
			else {
//				isConnected.setText("User Id and Password are not correct. Please enter valid User Id and Password.");
				showAlert(Alert.AlertType.ERROR, "ERROR", "User Id and Password are not found in database. Please enter valid User Id and Password.");
	    		return;
			}
		} catch (NumberFormatException e) {
			
			//isConnected.setText("User Id and Password are not correct. Please enter valid User Id and Password.");
			e.printStackTrace();
			showAlert(Alert.AlertType.ERROR, "ERROR", "User Id and Password are not found in database. Please enter valid User Id and Password.");
    		return;
		} catch (SQLException e) {
			
			//isConnected.setText("User Id and Password are not correct. Please enter valid User Id and Password.");
			e.printStackTrace();
			showAlert(Alert.AlertType.ERROR, "ERROR", "User Id and Password are not found in database. Please enter valid User Id and Password.");
    		return;
		}
    	
//    	Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
//    	
//    	AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/views/AdminMainMenu.fxml"));
//		
//    	Scene scene = new Scene(root,366,334);
//		primaryStage.setScene(scene);
//		primaryStage.show();
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



