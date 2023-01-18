package assignment;

import java.io.*;
import java.util.Scanner;

/**
 * this class is used to create new objects from parameters or field values from
 * csv files, and adds to their respective lists
 * 
 * @author Airat YUsuff 22831467
 *
 */
public class CreateAndImportData implements DataFormatter {
	private PropertyList pList;
	private CustomerList cList;
	private LandmarkList lList;

	private String lineInFile;
	private Scanner fileToImport;
	private int lineCount = 0;

	/**
	 * reads all data from the existing files and set appropriate last index
	 * 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public CreateAndImportData() throws ClassNotFoundException, IOException {
		/**
		 * properties can be deleted so the correct last index has to be gotten from the
		 * last property in the file, to avoid creating duplicate ids and overwriting
		 * existing data
		 */
		pList = FileDataHandler.readPropertyList();
		Property.setLastPropertyIndex(FileDataHandler.getLastPropertyIndexFromFile());

		lList = FileDataHandler.readLandmarkList();
		Landmark.setLastIndex(lList.getLandmarks().size());

		cList = FileDataHandler.readCustomerList();
		Customer.setLastIndex(cList.getCustomers().size());
	}

	/**
	 * constructor for reading data from csv files
	 * 
	 * @param filename file name to import from
	 * @param type     the type of data that's to be read from the file i.e.
	 *                 properties, customers, or landmarks
	 */
	public CreateAndImportData(String filename, String type) throws ClassNotFoundException, IOException {
		if (type == "landmark") {
			lList = FileDataHandler.readLandmarkList();
			Landmark.setLastIndex(lList.getLandmarks().size());
		} else if (type == "property") {
			pList = FileDataHandler.readPropertyList();
			Property.setLastPropertyIndex(FileDataHandler.getLastPropertyIndexFromFile());
		} else if (type == "customer") {
			cList = FileDataHandler.readCustomerList();
			Customer.setLastIndex(cList.getCustomers().size());
		}

		File file = new File(filename);
		fileToImport = new Scanner(file);
	}

	/**
	 * reads each line from the file and save to string
	 * 
	 * @return boolean whether there is a next line available to read
	 * @throws IOException
	 */
	public boolean readNextLine() throws IOException {
		boolean lineRead;
		lineRead = fileToImport.hasNext();

		if (lineRead) {
			lineInFile = fileToImport.nextLine();
			lineCount++;
		}
		return lineRead;
	}

	/**
	 * reads every line of the csv file and creates properties with each line
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	public void importAllProperties() throws IOException, Exception {
		while (readNextLine()) {
			createProperty(lineInFile);
		}
		fileToImport.close();
	}

	/**
	 * create a new property using field values from a line read from the csv file,
	 * and add to property list
	 * 
	 * @param lineInFile comma_separated fields in the current line
	 * @throws Exception
	 */
	public void createProperty(String lineInFile) throws Exception {
		if (lineCount > 1) {
			String[] fields = lineInFile.split(",");

			// attempt to validate the fields in the csv file
			if (fields.length != 11) {
				throw new Exception(
						"The fields do not match the required number of fields. Please confirm that the file is correct");
			} else {
				// remove quotes around the latLong fields (it split in two because of the comma)
				fields[6] = fields[6].replaceAll("\"", "");
				fields[7] = fields[7].replaceAll("\"", "");

				/**
				 * check if the field values exists in the current propertyList, return a
				 * boolean and use the boolean to determine whether to create new property or
				 * not
				 **/
				boolean pptyExists = false;
				for (Property p : pList.getProperties().values()) {
					if (p.getBedrooms() == Integer.parseInt(fields[1])
							&& p.getBathrooms() == Integer.parseInt(fields[2])
							&& p.getRentPerMonth() == Double.parseDouble(fields[3])
							&& p.getSize() == Double.parseDouble(fields[4]) && p.getPostcode().equals(fields[5])
							&& p.getLatitude() == Double.parseDouble(fields[6])
							&& p.getLongitude() == Double.parseDouble(fields[7])
							&& p.getFurnishedStatus().equals(fields[8]) && p.getType().equals(fields[9])) {
						pptyExists = true;
						break;
					}
				}
				if (!pptyExists) {
					Property ppty = new Property(fields[9], fields[8], fields[5], fields[0], fields[10],
							Double.parseDouble(fields[4]), Integer.parseInt(fields[1]), Integer.parseInt(fields[2]),
							Double.parseDouble(fields[3]), Double.parseDouble(fields[6]),
							Double.parseDouble(fields[7]));
					pList.addProperty(ppty);
				}

			}
		}
	}

	/**
	 * create a new property and add to the list of existing properties
	 * 
	 * @param t  type
	 * @param f  furnished status
	 * @param p  postcode
	 * @param d  date listed
	 * @param g  garden
	 * @param s  size
	 * @param b  bedrooms
	 * @param c  bathrooms
	 * @param r  rent PCM
	 * @param l1 latitude
	 * @param l2 longitude
	 * @throws CustomException
	 */
	public void createProperty(String t, String f, String postcode, String d, String g, double s, int b, int c,
			double r, double l1, double l2) throws CustomException {

		// check if property already exists
		boolean propertyExists = false;
		for (Property p : pList.getProperties().values()) {
			if (p.getType().equals(t) && p.getPostcode().equals(postcode) && p.getFurnishedStatus().equals(f)
					&& p.getLatitude() == l1 && p.getLongitude() == l2 && p.getSize() == s && p.getBedrooms() == b
					&& p.getBathrooms() == c) {
				propertyExists = true;
				break;
			}
		}
		if (!propertyExists) {
			Property ppty = new Property(t, f, postcode, d, g, s, b, c, r, l1, l2);
			pList.addProperty(ppty);
		} else {
			throw new CustomException("Property already exists");
		}
	}

	public PropertyList getAllProperties() {
		return pList;
	}

	/**
	 * reads every line of the csv file and creates place of interest with each line
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	public void importAllLandmarks() throws IOException, Exception {
		while (readNextLine()) {
			createLandmark(lineInFile);
		}
		fileToImport.close();
	}

	/**
	 * create a new place of interest using field values from a line read from the
	 * csv file, and add to the list
	 * 
	 * @param lineInFile comma_separated fields in the current line
	 * @throws Exception
	 */
	public void createLandmark(String lineInFile) throws Exception {
		if (lineCount > 1) {
			String[] fields = lineInFile.split(",");

			// attempt to validate the fields in the csv file
			if (fields.length != 4) {
				throw new Exception(
						"The fields do not match the required number of fields. Please confirm that the file is correct");
			} else {
				// remove quotes around the latLong fields (it split in two because of the comma)
				fields[2] = fields[2].replaceAll("\"", "");
				fields[3] = fields[3].replaceAll("\"", "");

				/**
				 * check if the field values exist in the current list, return a boolean and use
				 * the boolean to determine whether to create new landmark or not
				 **/
				boolean landmarkExists = false;

				for (Landmark l : lList.getLandmarks()) {
					if (l.getName().equals(fields[0]) && l.getPostcode().equals(fields[1])
							&& l.getLatitude() == Double.parseDouble(fields[2])
							&& l.getLongitude() == Double.parseDouble(fields[3])) {
						landmarkExists = true;
						break;
					}
				}
				if (!landmarkExists) {
					Landmark landmark = new Landmark(fields[0], fields[1], Double.parseDouble(fields[2]),
							Double.parseDouble(fields[3]));
					lList.addLandmark(landmark);
				}

			}
		}
	}

	/**
	 * creates a new place of interest and add to the list of existing ones
	 * 
	 * @param n  name
	 * @param p  postcode
	 * @param l1 latitude
	 * @param l2 longitude
	 * @throws CustomException
	 */
	public void createLandmark(String n, String p, double l1, double l2) throws CustomException {
		// check if landmark already exists
		boolean landmarkExists = false;
		for (Landmark l : lList.getLandmarks()) {
			if (l.getName().equals(n) && l.getPostcode().equals(p) && l.getLatitude() == l1 && l.getLongitude() == l2) {
				landmarkExists = true;
				break;
			}
		}
		if (!landmarkExists) {
			Landmark landmark = new Landmark(n, p, l1, l2);
			lList.addLandmark(landmark);
		} else {
			throw new CustomException("Landmark already exists");
		}
	}

	public LandmarkList getAllLandmarks() {
		return lList;
	}

	/**
	 * reads every line of the csv file and creates customers with each line
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	public void importAllCustomers() throws IOException, Exception {
		while (readNextLine()) {
			createCustomer(lineInFile);
		}
		fileToImport.close();
	}

	/**
	 * create a new customer using field values from a line read from the csv file,
	 * and add to the list
	 * 
	 * @param lineInFile comma_separated fields in the current line
	 * @throws Exception
	 */
	public void createCustomer(String lineInFile) throws Exception {
		if (lineCount > 1) {
			String[] fields = lineInFile.split(",");

			// attempt to validate the fields in the csv file
			if (fields.length != 4) {
				throw new Exception(
						"The fields do not match the required number of fields and/or type. Please confirm that the file is correct");
			} else {
				/**
				 * check if customer exists in the current list, return a boolean and use the
				 * boolean to determine whether to add it or not
				 **/
				boolean customerExists = false;
				for (Customer c : cList.getCustomers()) {
					if (c.getName().equals(fields[0]) && c.getEmail().equals(fields[1])
							&& c.getPhone().equals(fields[2])
							&& c.getDOB().equals(fields[3])) {
						customerExists = true;
						break;
					}
				}
				if (!customerExists) {
					Customer cust = new Customer(fields[0], fields[1], fields[2], fields[3]);
					cList.addCustomer(cust);
				}
			}
		}
	}

	/**
	 * creates new customer and add to the list of existing ones
	 * 
	 * @param n name
	 * @param e email
	 * @param p phone
	 * @param d date of birth
	 * @throws Exception
	 */
	public void createCustomer(String n, String e, String p, String d) throws Exception {
		// check if customer already exists
		boolean customerExists = false;
		for (Customer c : cList.getCustomers()) {
			if (c.getName().equals(n) && c.getEmail().equals(e) && c.getPhone().equals(p)
					&& c.getDOB().equals(d)) {
				customerExists = true;
				break;
			}
		}
		if (!customerExists) {
			Customer customer = new Customer(n, e, p, d);
			cList.addCustomer(customer);
		} else {
			throw new CustomException("Customer already exists");
		}
	}

	public CustomerList getAllCustomers() {
		return cList;
	}

}
