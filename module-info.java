module Project_HotelReservationSystem {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.base;
	requires javafx.graphics;
	requires java.sql;
	
	opens application to javafx.graphics, javafx.fxml;
	exports controllers to javafx.fxml;
	opens controllers to javafx.fxml;
}
