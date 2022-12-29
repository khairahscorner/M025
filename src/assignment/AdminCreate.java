package assignment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class AdminCreate {
	private static final Properties fileNames = new Properties();

	public static void initialise() throws FileNotFoundException, IOException {
		fileNames.load(new FileInputStream("binFiles.properties"));
		DataHandler.readProperties(fileNames);
	}

	public static void main(String[] args) {
		try {
			initialise();	
			Admin admin = new Admin("admin", "admin");
			DataHandler.writeToFile(admin);
		} 
		catch(Exception e) { 
			System.out.println(e.toString());
		}

	}

}
