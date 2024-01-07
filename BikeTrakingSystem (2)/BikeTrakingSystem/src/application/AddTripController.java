package application;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.bson.Document;

import com.mongodb.client.MongoClients;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddTripController implements Initializable {

    @FXML
    private TextField Dplace;

    @FXML
    private TextField Numberofdays;

    @FXML
    private TextField Splace;

    @FXML
    private TextField amount;

    @FXML
    private Button close;

    @FXML
    private Button submit;

    @SuppressWarnings("unused")
	private String user="";
	public void setUser(String username) {
		this.user=username;
		System.out.print(user);
	}
	
	public void confirmTrip() {
        try (var mongoClient = MongoClients.create("mongodb://localhost:27017")) {
        	var database = mongoClient.getDatabase("BikeTracker");
	        var collection = database.getCollection("Trips");
            String startingPlace = Splace.getText();
            String destination = Dplace.getText();
            int numberOfDays = Integer.parseInt(Numberofdays.getText());
            double tripAmount = Double.parseDouble(amount.getText());
            var tripDocument = new Document()
            		.append("user", user)
                    .append("startingPlace", startingPlace)
                    .append("destination", destination)
                    .append("numberOfDays", numberOfDays)
                    .append("amount", tripAmount);
            collection.insertOne(tripDocument);
            close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	public void close() throws IOException {
		Stage stage = (Stage) submit.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
        Parent root = loader.load();
        HomeController home = loader.getController();
        home.setUser(user);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Home Page");
        stage.show();
	}
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
