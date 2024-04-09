package application;
	
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import controllers.GuestBookRoomController;
import controllers.WelcomePageController;
import javafx.application.Application;
import javafx.stage.Stage;
import models.Room;
import models.RoomManager;
import models.RoomTypeName;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/WelcomePage.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            
			Scene scene = new Scene(root,500,300);

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
