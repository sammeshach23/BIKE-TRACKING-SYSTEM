package application ;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HomeController implements Initializable{
	
	@FXML
    private Button AddMaintenence;

    @FXML
    private Button AddTrip;

    @FXML
    private Button BikeLocation;

    @FXML
    private Button NewBike;

    @FXML
    private Button UpdateLocation;

    @FXML
    private Button log;
	
	@SuppressWarnings("unused")
	private String user="";
	public void setUser(String username) {
		this.user=username;
		//System.out.print(user);
	}
	
	public void addtrip() throws IOException {
		Stage stage = (Stage) NewBike.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddTrip.fxml"));
        Parent root = loader.load();
        AddTripController home = loader.getController();
        home.setUser(user);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Home Page");
        stage.show();
	}
	
	public void userInfo() throws IOException {
		Stage stage = (Stage) NewBike.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("userinfo.fxml"));
        Parent root = loader.load();
        BikeInfoController home = loader.getController();
        home.setUser(user);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Home Page");
        stage.show();
	}
	
	public void Maintain() throws IOException {
		Stage stage = (Stage) NewBike.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Maintenance.fxml"));
        Parent root = loader.load();
        MaintenanceController home = loader.getController();
        home.setUser(user);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Home Page");
        stage.show();
	}
	
	public void Location() throws IOException {
		Stage stage = (Stage) NewBike.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Location.fxml"));
        Parent root = loader.load();
        LocationController home = loader.getController();
        home.setUser(user);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Home Page");
        stage.show();
	}
	
	public void NewBik() throws IOException {
		Stage stage = (Stage) NewBike.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("NewBike.fxml"));
        Parent root = loader.load();
        NewBikeController home = loader.getController();
        home.setUser(user);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Home Page");
        stage.show();
	}
	
	public void logout() throws IOException {
		Stage stage = (Stage) log.getScene().getWindow();
		//stage.close();
		String pla = "SignIn.fxml";
		FXMLLoader loader = new FXMLLoader(getClass().getResource(pla));
	    Parent root = loader.load();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Sign In");
		stage.show();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
}