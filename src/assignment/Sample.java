package assignment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

//import com.google.maps.GeoApiContext;
//import com.google.maps.GeocodingApi;
//import com.google.maps.errors.ApiException;
//import com.google.maps.model.GeocodingResult;
//import com.google.maps.model.ComponentFilter;


public class Sample implements DataFormatter {
	private static final Properties fileNames = new Properties();

	public static void initialise() throws FileNotFoundException, IOException {
		fileNames.load(new FileInputStream("binFiles.properties"));
		FileDataHandler.readProperties(fileNames);
	}
	
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		try {
			FileWriter fwriter = new FileWriter("sample.txt");
			String[] asd = {"Airah", "airah@gmail.com", "CSYM025"};
			for(int i = 0; i < asd.length; i++) {
				fwriter.write(asd[i] + ",");
			}
			fwriter.write("\n");
			
			fwriter.close();
			
			System.out.println("does it get here?");
			
			
//			initialise();
		
			//Properties
//			ImportData da = new ImportData("/Users/airah/Desktop/JavaAssignmentFiles/House_Rent_Dataset.csv", "property");
//			da.importAllProperties();
//			ImportData da = new ImportData();
//			da.createProperty("Condo", "Unfurnished", "NN2 5ER", "25/12/2022", "y", 2000, 3, 3, 1000, 50.26115, -0.85293);		
//			da.createProperty("Flat", "Semi-fnurnished", "NN1 3AL", "25/12/2022", "n", 1200, 2, 2, 700, 55.26115, -0.35293);
//			da.createProperty("Two - Storey", "furnished", "59.26115,-0.85293", "NN1 3AL", "23/12/2022", 'y', 1500, 2, 2, 700);
//			DataHandler.readPropertyList();
//			DataHandler.writeToFile(da.getAllProperties());
			

			
			//Landmarks
//			ImportData da = new ImportData("/Users/airah/Desktop/JavaAssignmentFiles/PlacesOfInterestDataSet.csv", "landmark");
//			da.importAllLandmarks();
//			ImportData da = new ImportData();
//			da.createLandmark("Train Station", "NN1 1SP", 52.23710, -0.90631);
//			DataHandler.readLandmarkList();
//			DataHandler.writeToFile(da.getAllLandmarks());

			
			//customers
//			ImportData da = new ImportData();
//			da.createCustomer("Hamdi", "hamdi@email.com", "0903456789", "01/01/2006");
//			DataHandler.writeToFile(da.getAllCustomers());		
//			DataHandler.readCustomerList();
		} 	
		catch(Exception e) { 
			System.out.println(e.toString());
		}
	} 
	
	public static void calcLatLong(String postCode) throws IOException, InterruptedException {
		
		//use your google api key to create a GeoApiContext instance
		
//		GeoApiContext context = new GeoApiContext.Builder().apiKey("AIzaSyD4tGRs_MmWBuSmSuShgNmO_Swew5XU2mw").build();
//
//		//this will get geolocation details via zip 
//		GeocodingResult[]results = GeocodingApi.newRequest(context).components(ComponentFilter.postalCode(postCode)).await(); 
//		System.out.println(results[0]);
	}

}
