package assignment;
	
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class AppStart extends Application {
	//java properties variable
	private static final Properties properties = new Properties();
	
	public static void initialise() throws FileNotFoundException, IOException {
		properties.load(new FileInputStream("binFiles.properties"));
		DataHandler.readProperties(properties);
		
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		initialise();
		launch(args);
	}

	
	@Override
	public void start(Stage openingStage) throws IOException {
		// Load the FXML file.
	      Parent root = FXMLLoader.load(getClass().getResource("AppStart.fxml")); 
	      
	      // Build the scene graph.
	      Scene scene = new Scene(root); 
	      
	      // add external stylesheet
	      
	      //scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
	      String css = getClass().getResource("styles.css").toExternalForm();
	      scene.getStylesheets().add(css);

	      
	      // Display our window, using the scene graph.
	      openingStage.setTitle("Welcome to CSYM025 Lettings"); 
	      openingStage.setScene(scene);
	      openingStage.show();
	}

}
