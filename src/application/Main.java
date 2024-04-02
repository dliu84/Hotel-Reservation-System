package application;
	
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
//			RoomManager roomManager = RoomManager.getInstance();
//	        List<Room> rooms = roomManager.getRooms();
	        
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/WelcomePage.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
			
			WelcomePageController webcomePageController = loader.getController();
			
			//webcomePageController.setRooms(rooms);
			
			//System.out.println("the nummber of rooms in Main is: " + rooms.size());
			
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
	
//	public static List<Room> generateRooms() {
//	    List<Room> rooms = new ArrayList<>();
//
//	    // Creating some fake rooms
//	    rooms.add(new Room(1, 100.00, RoomTypeName.SINGLE));
//	    rooms.add(new Room(2, 150.00, RoomTypeName.DOUBLE));
//	    rooms.add(new Room(3, 200.00, RoomTypeName.DELUX));
//	    rooms.add(new Room(4, 300.00, RoomTypeName.PENTHOUSE));
//	    rooms.add(new Room(5, 100.00, RoomTypeName.SINGLE));
//	    rooms.add(new Room(6, 150.00, RoomTypeName.DOUBLE));
//	    rooms.add(new Room(7, 200.00, RoomTypeName.DELUX));
//	    rooms.add(new Room(8, 300.00, RoomTypeName.PENTHOUSE));
//	    rooms.add(new Room(9, 100.00, RoomTypeName.SINGLE));
//	    rooms.add(new Room(10, 150.00, RoomTypeName.DOUBLE));
//	    rooms.add(new Room(11, 200.00, RoomTypeName.DELUX));
//	    rooms.add(new Room(12, 300.00, RoomTypeName.PENTHOUSE));
//	    
//
//	    return rooms;
//	}

}
