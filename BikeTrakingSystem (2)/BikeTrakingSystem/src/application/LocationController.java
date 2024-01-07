package application;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.bson.Document;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LocationController implements Initializable{

    @FXML
    private TextField amount;

    @FXML
    private Button close;

    @FXML
    private Button submit;
    
    @FXML
    private ComboBox<String> choice;

    @SuppressWarnings("unused")
	private String user="";
	public void setUser(String username) {
		this.user=username;
		System.out.print(user);
		help();
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
    
	public void help() {
		try (var mongoClient = MongoClients.create("mongodb://localhost:27017")) {
		    MongoDatabase database = mongoClient.getDatabase("BikeTracker");
		    MongoCollection<Document> bikesCollection = database.getCollection("bikes");
		    Document query = new Document("user", user);
		    FindIterable<Document> bikeResults = bikesCollection.find(query);
		    String bikeModel = null;
		    for (Document result : bikeResults) {
		        bikeModel = result.getString("model");
		        //System.out.println("Bike Model: " + bikeModel);
		        choice.getItems().add(bikeModel);
                choice.setValue(bikeModel);
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}
	
	public void updateMaintenanceValue() {
	    try {
	        var mongoClient = MongoClients.create("mongodb://localhost:27017");
	        MongoDatabase database = mongoClient.getDatabase("BikeTracker");
	        MongoCollection<Document> bikesCollection = database.getCollection("bikes");
	        String selectedBikeModel = choice.getValue();
	        if (selectedBikeModel == null) {
	            System.out.println("No bike selected.");
	            return;
	        }
	        Document updateDoc = new Document("$set", new Document("location", amount.getText()));
	        bikesCollection.updateOne(new Document("user", user).append("model", selectedBikeModel), updateDoc, new UpdateOptions().upsert(true));
	        mongoClient.close();
	        close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

}
