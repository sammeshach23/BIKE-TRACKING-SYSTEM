package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import org.bson.Document;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClients;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.Initializable;

public class SignUpController implements Initializable {

    @FXML
    private Button RegisterButton;

    @FXML
    private TextField email;

    @FXML
    private TextField location;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField password;

    @FXML
    private TextField phone;

    @FXML
    private TextField username;

    public void loginPage() throws IOException {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("SignIn.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Sign In");
        stage.show();
    }
    
    public boolean isValid() {
    	String lo = location.getText().trim();
		String us = username.getText().trim();
	    String ps = password.getText();
	    String ph = phone.getText().trim();
	    String em = email.getText().trim();
		if(us.isEmpty() || ps.isEmpty() || lo.isEmpty() || ph.isEmpty() || em.isEmpty()) {
			return false;
		}return true;
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

    private static final String DATABASE_NAME = "BikeTracker";
    private static final String COLLECTION_NAME = "users";

    public synchronized void signUp() {
        try (var mongoClient = MongoClients.create("mongodb://localhost:27017")) {
        	if(isValid()) {
	            var database = mongoClient.getDatabase(DATABASE_NAME);
	            var collection = database.getCollection(COLLECTION_NAME);
	            var userDocument = new Document()
	                    .append("username", username.getText())
	                    .append("password", password.getText())
	                    .append("email", email.getText())
	                    .append("phone", phone.getText())
	                    .append("location", location.getText());
	            collection.insertOne(userDocument);
	            
	            //System.out.println("User registered successfully.");
	            
	            Stage stage = (Stage) loginButton.getScene().getWindow();
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
		        Parent root = loader.load();
		        HomeController home = loader.getController();
		        home.setUser(username.getText());
	            Scene scene = new Scene(root);
	            stage.setScene(scene);
	            stage.setTitle("Home Page");
	            stage.show();
        	}else {
        		showInvalid();
        	}
        } catch (MongoException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // Initialization code, if needed
    }
}
