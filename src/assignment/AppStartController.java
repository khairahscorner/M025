package assignment;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * the class controls the home page of the application where the user logs in. Includes user input validation
 * @author Airat YUsuff 22831467
 *
 */
public class AppStartController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    
    private Admin adminUser;
    
    /**
     * get admin stored in file
     */
    public void initialize() {
    	try {
        	adminUser = FileDataHandler.readAdmins(); 
        	System.out.println("admin is: \n" + adminUser);
    	}
    	catch(Exception e) {
    		Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("An error has occured in the app");
            alert.show();
            System.out.println(e.toString());
    	}
    }
    
    /**
     * handles click action of the login button, checks for input validation and logs into the dashboard
     * @param e	
     * @throws IOException
     */
    public void loginBtnListener(ActionEvent e) throws IOException {
    	Alert alert = new Alert(AlertType.NONE);
    	
    	if(usernameField.getText() == "" || passwordField.getText() == "") {
    		alert.setAlertType(AlertType.ERROR);
            alert.setTitle("Error logging in");
            alert.setContentText("Please fill in all details correctly");
            alert.show();
    	}
    	else if(!adminUser.getUsername().equals(usernameField.getText()) || !adminUser.getPassword().equals(passwordField.getText())) {
    		alert.setAlertType(AlertType.ERROR);
            alert.setTitle("Error logging in");
            alert.setContentText("Your username or password is not registered as admin");
            alert.show();
    	}
    	else {
    		Parent parent = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
    		Scene scene = new Scene(parent); 
    		
    		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		    String css = getClass().getResource("styles.css").toExternalForm();
		    scene.getStylesheets().add(css);
		    
		    stage.setTitle("Dashboard - CSYM025 Lettings"); 
		    stage.setScene(scene);
		    stage.show(); 
    	}
    }


}
