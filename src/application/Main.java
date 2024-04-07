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
	
	/*private static final String DB_JDBC = "jdbc:sqlite:";
	private static final String DB_CONNECTION = "/Users/mayflower/Documents/APD545/practice/Project_HotelReservationSystem/src/";
	private static final String DB_NAME = "database.db";
	
	
	private static final String DB_TABLE_GUESTS = "Guests";
	private static final String DB_TABLE_BILLS = "Bills";
	private static final String DB_TABLE_LOGINS = "Logins";
	private static final String DB_TABLE_RESERVATIONS = "Reservations";
	private static final String DB_TABLE_RESERVATIONROOMS = "ReservationRooms";
	private static final String DB_TABLE_ROOMS = "Rooms";
	private static final String DB_TABLE_ROOMTYPENAMES = "RoomTypeNames";
	
	private static final String CREATE_TBL_QRY_GUESTS = "CREATE TABLE IF NOT EXISTS " + DB_TABLE_GUESTS +
	        " (guestID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
	        "title VARCHAR(20), " +
	        "firstName VARCHAR(50), " +
	        "lastName VARCHAR(50), " +
	        "address VARCHAR(100), " +
	        "phone INTEGER, " +
	        "email VARCHAR(100))";
	
	private static final String CREATE_TBL_QRY_BILLS = "CREATE TABLE IF NOT EXISTS " + DB_TABLE_BILLS +
	        " (billID INTEGER PRIMARY KEY AUTOINCREMENT, " +
	        "amount REAL)";
	
	private static final String CREATE_TBL_QRY_LOGINS = "CREATE TABLE IF NOT EXISTS " + DB_TABLE_LOGINS +
	        " (id INTEGER NOT NULL PRIMARY KEY, " +
	        "password VARCHAR(50))";
	
	private static final String CREATE_TBL_QRY_ROOM_TYPES = "CREATE TABLE IF NOT EXISTS " + DB_TABLE_ROOMTYPENAMES +
	        " (roomTypeName VARCHAR(50))";
	
	private static final String CREATE_TBL_QRY_ROOMS = "CREATE TABLE IF NOT EXISTS " + DB_TABLE_ROOMS +
	        " (roomID INTEGER PRIMARY KEY AUTOINCREMENT, " +
	        "price DOUBLE, " +
	        "roomType VARCHAR(20))";
	
	private static final String CREATE_TBL_QRY_RESERVATIONS = "CREATE TABLE IF NOT EXISTS " + DB_TABLE_RESERVATIONS +
	        " (bookID INTEGER PRIMARY KEY AUTOINCREMENT, " +
	        "bookDate DATE, " +
	        "checkInDate DATE, " +
	        "checkOutDate DATE, " +
	        "guestID INTEGER, " +
	        "billID INTEGER)";

	private static final String CREATE_TBL_QRY_RESERVATION_ROOMS = "CREATE TABLE IF NOT EXISTS " + DB_TABLE_RESERVATIONROOMS +
	        " (reservationID INTEGER, " +
	        "roomID INTEGER, " +
	        "FOREIGN KEY(reservationID) REFERENCES " + DB_TABLE_RESERVATIONS + "(bookID), " +
	        "FOREIGN KEY(roomID) REFERENCES " + DB_TABLE_ROOMS + "(roomID))";

	private static final String INST_QRY_ROOM_TYPES = "Insert into "+ DB_TABLE_ROOMTYPENAMES + " (roomTypeName) values (?)";
	
	private static final String INST_QRY_ROOMS = "INSERT INTO ROOMS (roomId, price, roomType) VALUES (?, ?, ?)";
	
	private static final String INST_QRY_LOGINS = "INSERT INTO " + DB_TABLE_LOGINS + " (id, password) VALUES (?, ?)";

//	private static final String SELECT_QRY = "Select * from "+DB_TABLE_Guests+" where id = ?";
	
	@Override
	public void start(Stage primaryStage) {

		try (Connection conn = DriverManager.getConnection(DB_JDBC + DB_CONNECTION + DB_NAME)){
			
			PreparedStatement ps = null;
			ps = conn.prepareStatement(CREATE_TBL_QRY_GUESTS);
			boolean result = ps.execute();
			if (result) {
				System.out.println("Guests database is not created.");
			}else {
				System.out.println("Guests database is created!!!!!!!!!!!!!!");
			}
			
			ps = conn.prepareStatement(CREATE_TBL_QRY_BILLS);
			boolean result2 = ps.execute();
			if (result2) {
				System.out.println("Bills database is not created.");
			}else {
				System.out.println("Bills database is created!!!!!!!!!!!!!!");
			}
			
			ps = conn.prepareStatement(CREATE_TBL_QRY_LOGINS);
			boolean result3 = ps.execute();
			if (result3) {
				System.out.println("Logins database is not created.");
			}else {
				System.out.println("Logins database is created!!!!!!!!!!!!!!");
			}
//			ps = conn.prepareStatement(INST_QRY_LOGINS);
//			ps.setInt(1, 9527);
//			ps.setString(2, "password");
//			ps.executeUpdate();
			
			ps = conn.prepareStatement(CREATE_TBL_QRY_ROOM_TYPES);
			boolean result4 = ps.execute();
			if (result4) {
				System.out.println("RoomTypes database is not created.");
			}else {
				System.out.println("RoomTypes database is created!!!!!!!!!!!!!!");
			}
			ps = conn.prepareStatement(INST_QRY_ROOM_TYPES);
			
//			for (RoomTypeName typeName : RoomTypeName.values()) {
//				ps.setString(1, typeName.name());
//				ps.executeUpdate();
//            }
			
			ps = conn.prepareStatement(CREATE_TBL_QRY_ROOMS);
			boolean result5 = ps.execute();
			if (result4) {
				System.out.println("Rooms database is not created.");
			}else {
				System.out.println("Rooms database is created!!!!!!!!!!!!!!");
			}
			
//			ps = conn.prepareStatement(INST_QRY_ROOMS);
//			insertRooms(ps);
			
			ps = conn.prepareStatement(CREATE_TBL_QRY_RESERVATIONS);
			boolean result6 = ps.execute();
			if (result6) {
				System.out.println("Reservations database is not created.");
			}else {
				System.out.println("Reservations database is created!!!!!!!!!!!!!!");
			}
			
			ps = conn.prepareStatement(CREATE_TBL_QRY_RESERVATION_ROOMS);
			boolean result7 = ps.execute();
			if (result7) {
				System.out.println("Reservation_rooms database is not created.");
			}else {
				System.out.println("Reservation_rooms database is created!!!!!!!!!!!!!!");
			}
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/WelcomePage.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
			
			WelcomePageController webcomePageController = loader.getController();
			
			Scene scene = new Scene(root);
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}*/
	
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

	private static void insertRooms(PreparedStatement insertStmt) throws SQLException {
        double singlePrice = 100.00;
        double doublePrice = 150.00;
        double deluxPrice = 200.00;
        double penthousePrice = 300.00;

        // Insert single rooms
        for (int i = 1; i <= 10; i++) {
            insertStmt.setInt(1, i);
            insertStmt.setDouble(2, singlePrice);
            insertStmt.setString(3, RoomTypeName.SINGLE.name());
            insertStmt.executeUpdate();
        }

        // Insert double rooms
        for (int i = 11; i <= 20; i++) {
            insertStmt.setInt(1, i);
            insertStmt.setDouble(2, doublePrice);
            insertStmt.setString(3, RoomTypeName.DOUBLE.name());
            insertStmt.executeUpdate();
        }

        // Insert delux rooms
        for (int i = 21; i <= 30; i++) {
            insertStmt.setInt(1, i);
            insertStmt.setDouble(2, deluxPrice);
            insertStmt.setString(3, RoomTypeName.DELUX.name());
            insertStmt.executeUpdate();
        }

        // Insert penthouse rooms
        for (int i = 31; i <= 40; i++) {
            insertStmt.setInt(1, i);
            insertStmt.setDouble(2, penthousePrice);
            insertStmt.setString(3, RoomTypeName.PENTHOUSE.name());
            insertStmt.executeUpdate();
        }
    }

}
