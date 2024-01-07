package application;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.bson.Document;

import com.mongodb.MongoException;
import com.mongodb.client.MongoClients;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewBikeController implements Initializable {

    @FXML
    private TextField BikeMode;

    @FXML
    private TextField BikeNumber;

    @FXML
    private TextField Bikecolor;

    @FXML
    private Button close;

    @FXML
    private Button submit;

    
    public static void showInvalid() throws IOException {
		try {
			Stage stage = new Stage();
			Parent root = FXMLLoader.load(SignInController.class.getResource("INVALID.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
    
	public void submitThis() throws IOException {
        String bikeModel = BikeMode.getText();
        String bikeNumber = BikeNumber.getText();
        String bikeColor = Bikecolor.getText();

        if (!bikeModel.isEmpty() && !bikeNumber.isEmpty() && !bikeColor.isEmpty()) {
            insertBikeIntoDatabase(bikeModel, bikeNumber, bikeColor);
            close();
        } else {
        	showInvalid();
        }
    }

	public synchronized String getLocationByUsername(String username) throws IOException {
	    try (var mongoClient = MongoClients.create("mongodb://localhost:27017")) {
	        var database = mongoClient.getDatabase("BikeTracker");
	        var collection = database.getCollection("users");

	        var query = new Document("username", username);
	        var userDocument = collection.find(query).first();

	        if (userDocument != null) {
	            return userDocument.getString("location");
	        } else {
	            showInvalid();
	            return null;
	        }
	    } catch (MongoException e) {
	        e.printStackTrace();
	        return null;
	    }
	}

    private void insertBikeIntoDatabase(String model, String number, String color) {
        try (var mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            var database = mongoClient.getDatabase("BikeTracker");
            var collection = database.getCollection("bikes");
            var bikeDocument = new Document()
            		.append("user", user)
                    .append("model", model)
                    .append("number", number)
                    .append("location", getLocationByUsername(user))
                    .append("color", color)
            		.append("maintenanceAmount",0.0);
            collection.insertOne(bikeDocument);
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
    
    @SuppressWarnings("unused")
	private String user="";
	public void setUser(String username) {
		this.user=username;
		System.out.print(user);
	}
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
