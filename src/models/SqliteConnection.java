package models;

import java.sql.Connection;
import java.sql.DriverManager;

public class SqliteConnection {
	private static final String DB_JDBC = "jdbc:sqlite:";
	//private static final String DB_CONNECTION = "/Users/mayflower/Documents/APD545/practice/Project_HotelReservationSystem/";
	private static final String DB_NAME = "database.db";
	
	public static Connection Connector() {
		
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection(DB_JDBC + DB_NAME);
			return conn;
		}catch(Exception e){
			System.out.println(e);
			return null;
		}
	}
}
