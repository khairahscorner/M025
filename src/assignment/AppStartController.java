package assignment;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AppStartController {
    
    @FXML
    private Button  loginButton;
    
    @FXML
    private TextField usernameField;
    
    @FXML
    private TextField passwordField;
    
    
    public void initialize() {
    	  //initial things to run here
    }
    
    public void loginBtnListener(ActionEvent e) throws IOException {
    	Parent parent = FXMLLoader.load(
	               getClass().getResource("Dashboard.fxml")); 
	      
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
