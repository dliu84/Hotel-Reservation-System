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

public class AdminCurrentBookingsController {

    @FXML
    private TableColumn<?, ?> bookingNoColumn;

    @FXML
    private Button cancelButton;

    @FXML
    private TableView<?> currentBookingsTable;

    @FXML
    private Button deleteButton;

    @FXML
    private TableColumn<?, ?> nameColumn;

    @FXML
    private TextField noOfBookingsField;

    @FXML
    private TableColumn<?, ?> noOfDaysColumn;

    @FXML
    private TableColumn<?, ?> noOfRoomsColumn;

    @FXML
    private TableColumn<?, ?> roomTypeColumn;

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

}


