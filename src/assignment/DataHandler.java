package assignment;

import java.io.*;
import java.util.*;


public class DataHandler {
	private static String propertyListFileName;
//	private static String pptyKeysFile;
	private static String customerListFileName;
	private static String rentalListFileName;
	private static String landmarksFileName;
	
	public static void readProperties(Properties config) {
		propertyListFileName = config.getProperty("properties.file");
//		pptyKeysFile = config.getProperty("propertyKeys.file");
		customerListFileName = config.getProperty("customers.file");
		rentalListFileName = config.getProperty("rentals.file");
		landmarksFileName = config.getProperty("landmarks.file");
		
//		System.out.println(propertyListFileName);
//		System.out.println(customerListFileName);
//		System.out.println(rentalListFileName);
//		System.out.println(landmarksFileName);
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
    	   } else {
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
	      System.out.println("Customers " +"were written to "+ customerListFileName);	
	}
    
	public static CustomerList readCustomerList() throws IOException, ClassNotFoundException {
		CustomerList cList = new CustomerList();
		Object obj;
		obj = doDeserialize(customerListFileName);
		if (obj instanceof CustomerList) {
			cList = (CustomerList) obj;
		}
		System.out.println("list size: " + cList.getCustomers().size());
		return cList;
	}
	
	
	public static void writeToFile(PropertyList pList) throws IOException {
	      doSerialize(pList, propertyListFileName);
	      System.out.println("Properties " +"were written to "+ propertyListFileName);	
	}
	
	public static PropertyList readPropertyList() throws IOException, ClassNotFoundException {
		PropertyList pList = new PropertyList();
		Object obj = doDeserialize(propertyListFileName);
		if (obj instanceof PropertyList) {
			pList = (PropertyList) obj;
		}
		System.out.println("list size: " + pList.getProperties().size());
	
//		if (pList.getProperties().size() > 0) {
//			System.out.println("Properties in the list are: ");
//			for (int i = 0; i < pList.getKeys().size(); i++) {
//				String k = pList.getKeys().get(i);
//				System.out.println("Property key: " + k + " Ppty: " + pList.getProperties().get(k).getRentalStatus());
//			}
//		}
		
		return pList;
	}
	
	public static void writeToFile(LandmarkList l) throws IOException {
	      doSerialize(l, landmarksFileName);
	      System.out.println("Landmark " +"were written to "+ landmarksFileName);	
	}
	
	public static LandmarkList readLandmarkList() throws IOException, ClassNotFoundException {
		LandmarkList list = new LandmarkList();
		Object obj = doDeserialize(landmarksFileName);
		if (obj instanceof LandmarkList) {
			list = (LandmarkList) obj;
		}
		System.out.println("list size: " + list.getLandmarks().size());
		
//		if (list.getLandmarks().size() > 0) {
//			System.out.println("Landmarks in the list are: ");
//			for (int i = 0; i < list.getLandmarks().size(); i++) {
//				System.out.println("Landmark: " + list.getLandmarks().get(i).getName());
//			}
//		}
		
		return list;
	}
	
	public static void writeToFile(RentalList rList) throws IOException {
	      doSerialize(rList, rentalListFileName);
	      System.out.println("Rentals " +"were written to "+ rentalListFileName);	
	}
	
	public static RentalList readRentalList() throws IOException, ClassNotFoundException {
		RentalList rList = new RentalList();
		Object obj;
		obj =  doDeserialize(rentalListFileName);
		if (obj instanceof RentalList) {
			rList = (RentalList) obj;
		}
		System.out.println("list size: "+ rList.getRentals().size());
		
		// display list
//		if (rList.getHires().size() > 0) {
//			System.out.println("Book Hires in the list are: ");
//			for (int i = 0; i < list.getHires().size(); i++) {
//				System.out.println("Book Hire: " + list.getHires().get(i));
//			}
//		}
		return rList;
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
