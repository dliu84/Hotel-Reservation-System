package controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AdminAvailableRoomsController {

    @FXML
    private TableView<?> availableRoomsTable;

    @FXML
    private Button bookButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField noOfRooms;

    @FXML
    private TableColumn<?, ?> roomIdColumn;

    @FXML
    private TableColumn<?, ?> roomTypeColumn;

    @FXML
    void handleBook(ActionEvent event) {

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

