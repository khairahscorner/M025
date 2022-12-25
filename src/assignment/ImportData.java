package assignment;

import java.io.*;
import java.util.Properties;
import java.util.Scanner;
import java.time.LocalDate;



public class ImportData implements DateFormatter {
	private PropertyList pList;
	private CustomerList cList;
	private LandmarkList lList;
	private RentalList rList;

	
	String lineInFile;
	int lineCount = 0;
	Scanner fileToImport;
	
	
	public ImportData() throws ClassNotFoundException, IOException {
		pList = DataHandler.readPropertyList(); // read existing propertiesList from file
		Property.setLastPropertyIndex(pList.getProperties().size());
		
		lList = DataHandler.readLandmarkList();
		Landmark.setLastIndex(lList.getLandmarks().size());
		
		cList = DataHandler.readCustomerList();
		Customer.setLastIndex(cList.getCustomers().size());
		
		rList = DataHandler.readRentalList();
		Rental.setLastRentalIndex(rList.getRentals().size());
//		System.out.println(Property.getLastPropertyIndex());
		//add more
	}

	
	public ImportData(String filename, String type) throws ClassNotFoundException, IOException {
		if(type == "landmark") {
			lList = DataHandler.readLandmarkList();
			Landmark.setLastIndex(lList.getLandmarks().size());
		}
		else if(type == "property") {
			pList = DataHandler.readPropertyList(); // read existing propertiesList from file
			Property.setLastPropertyIndex(pList.getProperties().size());
		}
		else if(type == "customer") {
			cList = DataHandler.readCustomerList();
			Customer.setLastIndex(cList.getCustomers().size());
		}
		
		File file = new File(filename);
	    fileToImport = new Scanner(file);
	}
	
	
	public void createProperty(String t, String f, String p, String d, String g, int s, int b, int c, double r, double l1, double l2) {
		Property ppty = new Property(t, f, p, d, g, s, b, c, r, l1, l2);
		pList.addProperty(ppty);
	}
	
	public void createProperty(String lineInFile) {	       
	      if(lineCount > 1) {

			   Property ppty = new Property();

		      // Tokenize the last line read from the file.
		      String[] fields = lineInFile.split(",");
		      
		      //remove quotes around the latLong fields (it split in two because of the comma)
		      fields[6] = fields[6].replaceAll("\"", "");
		      fields[7] = fields[7].replaceAll("\"", "");
		      for (int i = 0; i < fields.length; i++) {
		         switch(i) {
		         case 0: ppty.setDateListed(LocalDate.parse(fields[0], dateFormatter)); break;
		         case 1: ppty.setBedrooms(Integer.parseInt(fields[1])); break;
		         case 2: ppty.setBathrooms(Integer.parseInt(fields[2])); break;
		         case 3: ppty.setRentPerMonth(Double.parseDouble(fields[3])); break;
		         case 4: ppty.setSize(Integer.parseInt(fields[4])); break;
		         case 5: ppty.setPostcode(fields[5]); break;
		         case 6: ppty.setLatitude(Double.parseDouble(fields[6])); break;
		         case 7: ppty.setLongitude(Double.parseDouble(fields[7])); break;
		         case 8: ppty.setFurnishedStatus(fields[8]); break;
		         case 9: ppty.setType(fields[9]); break;
		         case 10: ppty.setGarden(fields[10]); break;
		         }
		      }
		      pList.addProperty(ppty);
	      }
	   }
	
	public void importAllProperties() throws IOException {
		//REFERENCED CODE START
		while (readNextLine()) {
			createProperty(lineInFile);
			}
	      
	      fileToImport.close();
	      System.out.println("No more properties to import");
	}

	public PropertyList getAllProperties() {
		return pList;
	}
	
	public void createLandmark(String n, String p, double l1, double l2) {
		Landmark landmark = new Landmark(n, p, l1, l2);
		lList.addLandmark(landmark);
	}
	
	public void createLandmark(String lineInFile) {	       
	      if(lineCount > 1) {

	    	  Landmark landmark = new Landmark();

		      // Tokenize the last line read from the file.
		      String[] fields = lineInFile.split(",");
		      
		      //remove quotes around the latLong fields (it split in two because of the comma)
		      fields[2] = fields[2].replaceAll("\"", "");
		      fields[3] = fields[3].replaceAll("\"", "");
		      for (int i = 0; i < fields.length; i++) {
		         switch(i) {
		         case 0: landmark.setName(fields[0]); break;
		         case 1: landmark.setPostcode(fields[1]); break;
		         case 2: landmark.setLatitude(Double.parseDouble(fields[2])); break;
		         case 3: landmark.setLongitude(Double.parseDouble(fields[3])); break;
		         }
		      }
		      lList.addLandmark(landmark);
	      }
	   }
	
	public void importAllLandmarks() throws IOException {
		//REFERENCED CODE START
		while (readNextLine()) {
			createLandmark(lineInFile);
			}
	      
	      fileToImport.close();
	      System.out.println("No more landmark to import");
	}

	public LandmarkList getAllLandmarks() {
		return lList;
	}
	
	public void createCustomer(String n, String e, String p, String d) {
		Customer customer = new Customer(n, e, p, d);
		cList.addCustomer(customer);
	}
	
	//can extend and create from file too
	

	public CustomerList getAllCustomers() {
		return cList;
	}
	
	public void createRental(Property p, Customer c, LocalDate r, LocalDate d) {
		Rental rentalPpty  = new Rental(p, c, r, d);
		rList.addRentals(rentalPpty);
	}
	
	//can extend and create from file too
	

	public RentalList getAllRentals() {
		return rList;
	}
	
	
	//Referenced code START	
	 public boolean readNextLine() throws IOException {
	    boolean lineRead; // Flag variable
	  
	    // Determine whether there is more to read.
	    lineRead = fileToImport.hasNext();
	
	    // If so, read the next line. 
	    if (lineRead) {
	    	lineInFile = fileToImport.nextLine();
	    	lineCount++;
	    }
	     
	    return lineRead;
	 }
	 //STOP

}


