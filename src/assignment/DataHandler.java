package assignment;

import java.io.*;
import java.util.*;


public class DataHandler {
	private static String propertyListFileName;
	private static String customerListFileName;
	private static String rentalListFileName;
	private static String landmarksFileName;
	private static String usersFileName;
	
	
	public static void readProperties(Properties config) {
		propertyListFileName = config.getProperty("properties.file");
		customerListFileName = config.getProperty("customers.file");
		rentalListFileName = config.getProperty("rentals.file");
		landmarksFileName = config.getProperty("landmarks.file");
		usersFileName = config.getProperty("users.file");	
	}
	
	
	
	//referenced code - START
	// Serialise the object to a file
	public static void doSerialize(Object obj, String outputFile) throws IOException {
        FileOutputStream fileTowrite = new FileOutputStream(outputFile);
        ObjectOutputStream objTowrite = new ObjectOutputStream(fileTowrite);
        objTowrite.writeObject(obj);
        fileTowrite.close();
    }

    // Deserialise the Java object from a given file
    public static Object doDeserialize(String inputFile) throws IOException, ClassNotFoundException {
    	Object obj = new Object();
    	File f = new File (inputFile); 
    	if (f.exists()) {
    	FileInputStream fileToread = new FileInputStream(inputFile);
        ObjectInputStream objToread = new ObjectInputStream(fileToread);
    	  if (f.length() > 0) {
            obj = objToread.readObject();
            objToread.close();
    	  }
    	  else {
    		  System.out.println("File " + inputFile + " is empty");
    	  }
    	}
    	else {
    		System.out.println("File " + inputFile + " does not exist");
    	}
    	
        return obj;
    }
    
    //referenced code - STOP
    
    
	public static void writeToFile(CustomerList cList) throws IOException {
	      doSerialize(cList, customerListFileName);
	}
    
	public static CustomerList readCustomerList() throws IOException, ClassNotFoundException {
		CustomerList cList = new CustomerList();
		Object obj;
		obj = doDeserialize(customerListFileName);
		if (obj instanceof CustomerList) {
			cList = (CustomerList) obj;
		}

		return cList;
	}
	
	
	public static void writeToFile(PropertyList pList) throws IOException {
	      doSerialize(pList, propertyListFileName);
	}
	
	public static PropertyList readPropertyList() throws IOException, ClassNotFoundException {
		PropertyList pList = new PropertyList();
		Object obj = doDeserialize(propertyListFileName);
		if (obj instanceof PropertyList) {
			pList = (PropertyList) obj;
		}
		
		return pList;
	}
	
	
	public static void writeToFile(LandmarkList l) throws IOException {
	      doSerialize(l, landmarksFileName);
	}
	
	public static LandmarkList readLandmarkList() throws IOException, ClassNotFoundException {
		LandmarkList list = new LandmarkList();
		Object obj = doDeserialize(landmarksFileName);
		if (obj instanceof LandmarkList) {
			list = (LandmarkList) obj;
		}
		
		return list;
	}
	
	
	public static void writeToFile(RentalList rList) throws IOException {
	      doSerialize(rList, rentalListFileName);
	}
	
	public static RentalList readRentalList() throws IOException, ClassNotFoundException {
		RentalList rList = new RentalList();
		Object obj;
		obj =  doDeserialize(rentalListFileName);
		if (obj instanceof RentalList) {
			rList = (RentalList) obj;
		}
		
		return rList;
	}
	
	
	public static void writeToFile(Admin user) throws IOException {
	      doSerialize(user, usersFileName);	
	}
	
	public static Admin readAdmins() throws IOException, ClassNotFoundException {
		Admin user = new Admin();
		Object obj;
		obj =  doDeserialize(usersFileName);
		if (obj instanceof Admin) {
			user = (Admin) obj;
		}
		
		return user;
	}
	

	
//	//handling strings for a file	
//	public static void saveKeyToKeyFile(String key, String fileName) throws IOException {
//	
//	File file = new File(fileName); 
//	FileWriter fWriter;
//	PrintWriter pWriter;
//	    
//	if (file.exists()) {
//		System.out.println("file exists"); 
//		fWriter = new FileWriter(file, true);
//		fWriter.append(key + "\n");
//		fWriter.close();
//	}
//	else {
//		System.out.println("file does not");
//		pWriter = new PrintWriter(file);
//		pWriter.println(key);
//		pWriter.close();
//	}	
//}
//
//public static void writeToFile(String s) throws IOException {
//      saveKeyToKeyFile(s, pptyKeysFile);
//      System.out.println("key " +"were written to "+ pptyKeysFile);	
//}

//public static void readKeysFromFile(String fileName) throws IOException {  
//      File file = new File(fileName);
//      Scanner sc = new Scanner(file);
//    
//      while(sc.hasNext()) {
//        System.out.println(sc.next());
//        System.out.println();
//      }
//      sc.close();
//}

	
}
