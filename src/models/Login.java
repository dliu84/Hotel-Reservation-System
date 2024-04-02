package models;

public class Login {
    private int id;
    private String password;

    public Login(int id, String password) {
        this.id = id;
        this.password = password;
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

/*import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Login {
    private StringProperty userName;
    private StringProperty password;

    public Login(String userName, String password) {
        this.userName = new SimpleStringProperty(userName);
        this.password = new SimpleStringProperty(password);
    }

	public final StringProperty userNameProperty() {
		return this.userName;
	}
	

	public final String getUserName() {
		return this.userNameProperty().get();
	}
	

	public final void setUserName(final String userName) {
		this.userNameProperty().set(userName);
	}
	

	public final StringProperty passwordProperty() {
		return this.password;
	}
	

	public final String getPassword() {
		return this.passwordProperty().get();
	}
	

	public final void setPassword(final String password) {
		this.passwordProperty().set(password);
	}
}*/



