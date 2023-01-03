package assignment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * The main class to run for creating a new admin. the admin file is loaded to write the newly created admin
 * @author airah
 *
 */
public class AdminCreate {
	private static final Properties fileNames = new Properties();

	public static void initialise() throws FileNotFoundException, IOException {
		fileNames.load(new FileInputStream("allFiles.properties"));
		DataHandler.readProperties(fileNames);
	}

	public static void main(String[] args) {
		try {
			initialise();	
			Admin admin = new Admin("admin", "admin");
			
			DataHandler.writeToFile(admin);
			System.out.println("Done");
		} 
		catch(Exception e) { 
			System.out.println(e.toString());
		}

	}

}
