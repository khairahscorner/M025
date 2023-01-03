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

/**
 * the class is the entry point into the javafx app, handled by the AppStartController class
 * @author Airat Yusuff 22831467
 *
 */
public class AppStart extends Application {
	//java properties variable
	private static final Properties properties = new Properties();
	
	public static void main(String[] args) {
		try {
			initialise();
			launch(args);
		}
		catch(Exception e) {
			System.out.println(e.toString());
		}
	}

	/**
	 * reads a properties list from the file stream
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void initialise() throws FileNotFoundException, IOException {
		properties.load(new FileInputStream("allFiles.properties"));
		DataHandler.readProperties(properties);		
	}
	
	@Override
	public void start(Stage openingStage) throws IOException {
	      Parent root = FXMLLoader.load(getClass().getResource("AppStart.fxml")); 
	      Scene scene = new Scene(root); 
	      
	      // add external stylesheet
	      String css = getClass().getResource("styles.css").toExternalForm();
	      scene.getStylesheets().add(css);

	      openingStage.setTitle("Welcome to CSYM025 Lettings"); 
	      openingStage.setScene(scene);
	      openingStage.show();
	}

}
