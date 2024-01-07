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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignInController implements Initializable{
	
	@FXML
    private Button Register;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;
	
	public void onRegister() throws IOException {
		Stage stage = (Stage) loginButton.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Sign Up");
		stage.show();
	}
	
	private static final String DATABASE_NAME = "BikeTracker";
    private static final String COLLECTION_NAME = "users";
	
	public synchronized void login_home() {
	    try (var mongoClient = MongoClients.create("mongodb://localhost:27017")) {
	        var database = mongoClient.getDatabase(DATABASE_NAME);
	        var collection = database.getCollection(COLLECTION_NAME);
	        var query = new Document()
	                .append("username", username.getText())
	                .append("password", password.getText());
	        var userDocument = collection.find(query).first();
	        if (userDocument != null) {
	            Stage stage = (Stage) loginButton.getScene().getWindow();
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
		        Parent root = loader.load();
		        HomeController home = loader.getController();
		        home.setUser(username.getText());
	            Scene scene = new Scene(root);
	            stage.setScene(scene);
	            stage.setTitle("Home Page");
	            stage.show();
	        } else {
	        	showInvalid();
	            //System.out.println("Invalid username or password.");
	        }
	    } catch (MongoException e) {
	        e.printStackTrace();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
