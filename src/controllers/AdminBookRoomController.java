package controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AdminBookRoomController {

    @FXML
    private Button addGuestInfoButton;

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
    private Slider discountSlider;

    @FXML
    private CheckBox doubleCheckBox;

    @FXML
    private TextField doubleField;

    @FXML
    private TextField noOfGuestField;

    @FXML
    private CheckBox pentHouseCheckBox;

    @FXML
    private TextField pentHouseField;

    @FXML
    private CheckBox singleCheckBox;

    @FXML
    private TextField singleField;

    @FXML
    void handleAddGuestInfo(ActionEvent event) throws IOException {
    	 try {
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/GuestInfo.fxml"));
             Parent root = loader.load();
             Stage stage = new Stage();
             stage.setScene(new Scene(root));
             stage.show();
         } catch (IOException e) {
             e.printStackTrace();
             // Handle exception gracefully
         }
    }

    @FXML
    void handleCancel(ActionEvent event) throws IOException {
    	Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	
    	AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/views/AdminMainMenu.fxml"));
		
    	Scene scene = new Scene(root,336,334);
		primaryStage.setScene(scene);
		primaryStage.show();
    	
    }

}

