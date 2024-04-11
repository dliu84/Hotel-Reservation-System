package application;
	
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
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
import models.RoomTypeName;
import models.SqliteConnection;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
	private Socket socket;
	
	private DataOutputStream dout;
	private DataInputStream din;
	
	private TextArea ta = new TextArea();
	
//	private static final String UPDATE_ROOMS_AVAILABILITY = "UPDATE Rooms SET available = 1 WHERE available = 0";
//	private static final String DELETE_BILLS_SQL = "DELETE FROM Bills";
//	Connection conn;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/WelcomePage.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            
			Scene scene = new Scene(root,500,300);

			primaryStage.setScene(scene);
			primaryStage.show();
			
//			conn = SqliteConnection.Connector();
//			if (conn == null) {
//	    		System.out.println("Connection is not successful in Main.");
//	    		System.exit(1);
//	    	}
//	    	else {
//	    		System.out.println("Connection is successful in Main!!!");
//	    	} 
//	        
//	        PreparedStatement ps = null;
//	        
//	        try {
//	        	ps = conn.prepareStatement(UPDATE_ROOMS_AVAILABILITY);
//	        	boolean result = ps.execute();
//	       	 	if (result) {
//	    			System.out.println("Error updating Rooms table avilable column.");
//	    		}else {
//	    			System.out.println("The Rooms table avilable column is set to 1!!!!!!!!!!!!!");
//	    		}
//	        }catch(Exception e) {
//	        	e.printStackTrace();
//	        }
			
//			conn = SqliteConnection.Connector();
//			if (conn == null) {
//	    		System.out.println("Connection is not successful in Main.");
//	    		System.exit(1);
//	    	}
//	    	else {
//	    		System.out.println("Connection is successful in Main!!!");
//	    	} 
//	        
//	        PreparedStatement ps = null;
//	        
//	        try {
//	        	ps = conn.prepareStatement(DELETE_BILLS_SQL);
//	        	boolean result = ps.execute();
//	       	 	if (result) {
//	    			System.out.println("Error deleting bills rows.");
//	    		}else {
//	    			System.out.println("The Bills rows are deleted!!!!!!!!!!!!!");
//	    		}
//	        }catch(Exception e) {
//	        	e.printStackTrace();
//	        }
			
			
			try {
				socket = new Socket("localhost",8000);
				din=new DataInputStream(socket.getInputStream());
				
				dout = new DataOutputStream(socket.getOutputStream());
				
				new Thread(()->run()).start();
				
			}catch(IOException ex) {ex.printStackTrace();}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		try {
			while(true) {
				String text = din.readUTF();
				ta.appendText(text + "\n");
			}
		}catch(IOException ex) {ex.printStackTrace();}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
