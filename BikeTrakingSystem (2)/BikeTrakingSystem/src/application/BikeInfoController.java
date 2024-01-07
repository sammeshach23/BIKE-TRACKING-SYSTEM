package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCursor;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class BikeInfoController implements Initializable {
	
    @FXML
    private TableColumn<Document, String> bkname;

    @FXML
    private Button close;

    @FXML
    private TableColumn<Document, String> location;

    @FXML
    private TableColumn<Document, Double> maintenance;

    @FXML
    private TableView<Document> passengerTable;

    private String user = "";

    public void setUser(String username) {
        this.user = username;
        load();
    }

    public void close() throws IOException {
        Stage stage = (Stage) close.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
        Parent root = loader.load();
        HomeController home = loader.getController();
        home.setUser(user);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Home Page");
        stage.show();
    }

    private void load() {
    	bkname.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getString("model")));
        location.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getString("location")));
        maintenance.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getDouble("maintenanceAmount")).asObject());
        ObservableList<Document> bikeData = FXCollections.observableArrayList();
        try (var mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            var database = mongoClient.getDatabase("BikeTracker");
            var collection = database.getCollection("bikes");
            var query = new Document("user", user);
            FindIterable<Document> bikeDocuments = collection.find(query);

            try (MongoCursor<Document> cursor = bikeDocuments.iterator()) {
                while (cursor.hasNext()) {
                    bikeData.add(cursor.next());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        passengerTable.setItems(bikeData);
    }
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        
    }
}
