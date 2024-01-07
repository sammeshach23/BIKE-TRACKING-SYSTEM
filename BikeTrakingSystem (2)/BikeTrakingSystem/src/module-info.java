module BikeTrakingSystem {
	requires javafx.controls;
	requires javafx.fxml;
	requires mongo.java.driver;
	opens application to javafx.graphics, javafx.fxml;
}
