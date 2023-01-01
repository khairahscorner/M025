package assignment;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AppStartController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    
    private Admin adminUser;
    
    
    public void initialize() throws ClassNotFoundException, IOException {
    	  //initial things to run here
    	adminUser = DataHandler.readAdmins();
		
		System.out.println("admin is: \n" + adminUser);
    }
    
    public void loginBtnListener(ActionEvent e) throws IOException {
    	Alert alert = new Alert(AlertType.NONE);
		
		
    	if(usernameField.getText() == "" || passwordField.getText() == "") {
    		alert.setAlertType(AlertType.ERROR);
            alert.setTitle("Error logging in");
            alert.setContentText("Please fill in all details correctly");
            alert.show();
    	} else if(!adminUser.getUsername().equals(usernameField.getText()) || !adminUser.getPassword().equals(passwordField.getText())) {
    		alert.setAlertType(AlertType.ERROR);
            alert.setTitle("Error logging in");
            alert.setContentText("Your username or password is not registered as admin");
            alert.show();
    	}
    	else {
    		Parent parent = FXMLLoader.load(getClass().getResource("Dashboard.fxml")); 
		      
		      // Build the scene graph.
		      Scene scene = new Scene(parent); 
		
		      Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		      String css = getClass().getResource("styles.css").toExternalForm();
		      scene.getStylesheets().add(css);
		      
		      // Display our window, using the scene graph.
		      stage.setTitle("Dashboard - CSYM025 Lettings"); 
		      stage.setScene(scene);
		      stage.show(); 
    	}
    }


}
