package assignment;

import java.io.*;
import java.util.*;

/**
 * the class processses object serialisation and deserialisation for class
 * objects of different types
 * 
 * @author Airat YUsuff 22831467
 *
 */
public class FileDataHandler {
	private static String propertyListFileName;
	private static String customerListFileName;
	private static String rentalListFileName;
	private static String landmarksFileName;
	private static String usersFileName;

	/**
	 * reads values of each property stored in the specified Properties object
	 * 
	 * @param ppties Java Properties object
	 */
	public static void readProperties(Properties ppties) {
		propertyListFileName = ppties.getProperty("properties.file");
		customerListFileName = ppties.getProperty("customers.file");
		rentalListFileName = ppties.getProperty("rentals.file");
		landmarksFileName = ppties.getProperty("landmarks.file");
		usersFileName = ppties.getProperty("users.file");
	}

	/**
	 * serialise object to a file
	 * 
	 * @param obj        object to serialise
	 * @param outputFile file to serailise object to
	 * @throws IOException
	 */
	//<-***** CSYM025 Code FROM NILE - START
	public static void doSerialize(Object obj, String outputFile) throws IOException {
		FileOutputStream fileTowrite = new FileOutputStream(outputFile);
		ObjectOutputStream objTowrite = new ObjectOutputStream(fileTowrite);
		objTowrite.writeObject(obj);
		fileTowrite.close();
	}

	/**
	 * Deserialise the Java object from a given file
	 * 
	 * @param inputFile file to read objects from
	 * @return the deserialised object
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Object doDeserialize(String inputFile) throws IOException, ClassNotFoundException {
		Object obj = new Object();
		File f = new File(inputFile);
		if (f.exists()) {
			FileInputStream fileToread = new FileInputStream(inputFile);
			ObjectInputStream objToread = new ObjectInputStream(fileToread);
			if (f.length() > 0) {
				obj = objToread.readObject();
				objToread.close();
			} else {
				System.out.println("File " + inputFile + " is empty");
			}
		} else {
			System.out.println("File " + inputFile + " does not exist");
		}

		return obj;
	}
	//->***** CSYM025 Code FROM NILE - END

	/**
	 * overwrites the existing object in the file with new object
	 * 
	 * @param cList customerList object
	 * @throws IOException
	 */
	public static void writeToFile(CustomerList cList) throws IOException {
		doSerialize(cList, customerListFileName);
	}

	/**
	 * reads the customersList object stored in a file
	 * 
	 * @return the deserialised object
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
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

	/**
	 * retrieves the last property index added to the file
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static int getLastPropertyIndexFromFile() throws ClassNotFoundException, IOException {
		PropertyList pList = FileDataHandler.readPropertyList();

		if (pList.getProperties().size() > 0) {
			String lastPptyKeyInFile = pList.getKeys().get(pList.getKeys().size() - 1);
			int lastPptyIdFromFile = Integer.parseInt(lastPptyKeyInFile.split("p")[1]);
			return lastPptyIdFromFile + 1;
		} else {
			return pList.getProperties().size();
		}
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
		obj = doDeserialize(rentalListFileName);
		if (obj instanceof RentalList) {
			rList = (RentalList) obj;
		}

		return rList;
	}

	/**
	 * retrieves the last rental index added to the file
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static int getLastRentalIndexFromFile() throws ClassNotFoundException, IOException {
		RentalList rList = FileDataHandler.readRentalList();
		if (rList.getRentals().size() > 0) {
			String lastRentalKeyInFile = rList.getKeys().get(rList.getKeys().size() - 1);
			int lastRentalIdFromFile = Integer.parseInt(lastRentalKeyInFile.split("R")[1]);

			return (lastRentalIdFromFile + 1);
		} else {
			return rList.getRentals().size();
		}
	}

	public static void writeToFile(AdminList usersList) throws IOException {
		doSerialize(usersList, usersFileName);
	}

	public static AdminList readAdmins() throws IOException, ClassNotFoundException {
		AdminList usersList = new AdminList();
		Object obj;
		obj = doDeserialize(usersFileName);
		if (obj instanceof AdminList) {
			usersList = (AdminList) obj;
		}
		return usersList;
	}

}
