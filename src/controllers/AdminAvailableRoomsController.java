package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    void handleCancel(ActionEvent event) {
    	Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

}

