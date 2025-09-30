package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {
    private int id;
    private String password;
    
    Connection conn;
    
    public Login() {
    	conn = SqliteConnection.Connector();
    	if (conn == null) {
    		System.out.println("connection is not successful in login model");
    		System.exit(1);
    	}
    }

    public boolean isDbConnected() {
    	try {
			return !conn.isClosed();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
    }
    
    public boolean isLoggedIn(int id, String password) throws SQLException {
    	PreparedStatement ps = null;
    	ResultSet resultSet = null;
    	String query = "select * from Logins where id = ? and password = ?";
    	
    	try {
    		ps = conn.prepareStatement(query);
    		ps.setInt(1, id);
    		ps.setString(2, password);
    		
    		resultSet = ps.executeQuery();
    		
    		if(resultSet.next()) {
    			return true;
    		}
    		else {
    			return false;
    		}
    		
    	}catch(Exception e){
    		return false;
    	}finally {
    		ps.close();
    		resultSet.close();
    	}
    }
    
    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}




