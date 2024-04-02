package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    void handleCancel(ActionEvent event) {
    	Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void handleDelete(ActionEvent event) {

    }

}


