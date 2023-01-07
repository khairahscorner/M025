package assignment;

import java.io.*;
import java.util.Scanner;
import java.time.LocalDate;

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
	private RentalList rList;

	private String lineInFile;
	private Scanner fileToImport;
	private int lineCount = 0;

	/**
	 * reads all data from the existing files
	 * 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public CreateAndImportData() throws ClassNotFoundException, IOException {
		pList = DataHandler.readPropertyList();
		Property.setLastPropertyIndex(pList.getProperties().size());

		lList = DataHandler.readLandmarkList();
		Landmark.setLastIndex(lList.getLandmarks().size());

		cList = DataHandler.readCustomerList();
		Customer.setLastIndex(cList.getCustomers().size());

		/**
		 * rentals can be deleted so if there are existing rentals, the correct last
		 * index has to be gotten from the last rental in the file, to avoid creating
		 * duplicate keys and overwriting the last rental
		 */
		rList = DataHandler.readRentalList();
		if (rList.getRentals().size() > 0) {
			String lastRentalKeyInFile = rList.getKeys().get(rList.getKeys().size() - 1);
			int lastRentalIdFromFile = Integer.parseInt(lastRentalKeyInFile.split("R")[1]);

			Rental.setLastRentalIndex((lastRentalIdFromFile + 1));
		} else {
			Rental.setLastRentalIndex(rList.getRentals().size());
		}
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
			lList = DataHandler.readLandmarkList();
			Landmark.setLastIndex(lList.getLandmarks().size());
		} else if (type == "property") {
			pList = DataHandler.readPropertyList();
			Property.setLastPropertyIndex(pList.getProperties().size());
		} else if (type == "customer") {
			cList = DataHandler.readCustomerList();
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
			Property ppty = new Property();
			String[] fields = lineInFile.split(",");

			// attempt to validate the fields in the csv file
			if (fields.length != 11) {
				throw new Exception(
						"The fields do not match the required number of fields and/or type. Please confirm that the file is correct");
			} else {
				// remove quotes around the latLong fields (it split in two because of the comma)
				fields[6] = fields[6].replaceAll("\"", "");
				fields[7] = fields[7].replaceAll("\"", "");

				for (int i = 0; i < fields.length; i++) {
					switch (i) {
					case 0:
						ppty.setDateListed(LocalDate.parse(fields[0], dateFormatter));
						break;
					case 1:
						ppty.setBedrooms(Integer.parseInt(fields[1]));
						break;
					case 2:
						ppty.setBathrooms(Integer.parseInt(fields[2]));
						break;
					case 3:
						ppty.setRentPerMonth(Double.parseDouble(fields[3]));
						break;
					case 4:
						ppty.setSize(Double.parseDouble(fields[4]));
						break;
					case 5:
						ppty.setPostcode(fields[5]);
						break;
					case 6:
						ppty.setLatitude(Double.parseDouble(fields[6]));
						break;
					case 7:
						ppty.setLongitude(Double.parseDouble(fields[7]));
						break;
					case 8:
						ppty.setFurnishedStatus(fields[8]);
						break;
					case 9:
						ppty.setType(fields[9]);
						break;
					case 10:
						ppty.setGarden(fields[10]);
						break;
					}
				}

				/**
				 * check if property exists in the current propertyList, return a boolean and
				 * use the boolean to determine whether to add the property or not
				 **/
				boolean pptyExists = false;
				for (Property p : pList.getProperties().values()) {
					if (p.getType().equals(ppty.getType()) && p.getPostcode().equals(ppty.getPostcode())
							&& p.getFurnishedStatus().equals(ppty.getFurnishedStatus()) && p.getLatitude() == ppty.getLatitude()
							&& p.getLongitude() == ppty.getLongitude() && p.getSize() == ppty.getSize()
							&& p.getBedrooms() == ppty.getBedrooms() && p.getBathrooms() == ppty.getBathrooms()) {
						pptyExists = true;
						break;
					}
				}
				if (!pptyExists) {
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
		Property ppty = new Property(t, f, postcode, d, g, s, b, c, r, l1, l2);

		// check if property already exists
		boolean propertyExists = false;
		for (Property p : pList.getProperties().values()) {
			if (p.getType().equals(ppty.getType()) && p.getPostcode().equals(ppty.getPostcode())
					&& p.getFurnishedStatus().equals(ppty.getFurnishedStatus()) && p.getLatitude() == ppty.getLatitude()
					&& p.getLongitude() == ppty.getLongitude() && p.getSize() == ppty.getSize()
					&& p.getBedrooms() == ppty.getBedrooms() && p.getBathrooms() == ppty.getBathrooms()) {
				propertyExists = true;
				break;
			}
		}
		if (!propertyExists) {
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

			Landmark landmark = new Landmark();
			String[] fields = lineInFile.split(",");

			// attempt to validate the fields in the csv file
			if (fields.length != 4) {
				throw new Exception(
						"The fields do not match the required number of fields and/or type. Please confirm that the file is correct");
			} else {
				// remove quotes around the latLong fields (it split in two because of the comma)
				fields[2] = fields[2].replaceAll("\"", "");
				fields[3] = fields[3].replaceAll("\"", "");

				for (int i = 0; i < fields.length; i++) {
					switch (i) {
					case 0:
						landmark.setName(fields[0]);
						break;
					case 1:
						landmark.setPostcode(fields[1]);
						break;
					case 2:
						landmark.setLatitude(Double.parseDouble(fields[2]));
						break;
					case 3:
						landmark.setLongitude(Double.parseDouble(fields[3]));
						break;
					}
				}

				/**
				 * check if landmark exists in the current list, return a boolean and use the
				 * boolean to determine whether to add it or not
				 **/
				boolean landmarkExists = false;

				for (Landmark l : lList.getLandmarks()) {
					if (l.getName().equals(landmark.getName()) && l.getPostcode().equals(landmark.getPostcode())
							&& l.getLatitude() == landmark.getLatitude()
							&& l.getLongitude() == landmark.getLongitude()) {
						landmarkExists = true;
						break;
					}
				}
				if (!landmarkExists) {
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
		Landmark landmark = new Landmark(n, p, l1, l2);

		// check if landmark already exists
		boolean landmarkExists = false;
		for (Landmark l : lList.getLandmarks()) {
			if (l.getName().equals(landmark.getName()) && l.getPostcode().equals(landmark.getPostcode())
					&& l.getLatitude() == landmark.getLatitude() && l.getLongitude() == landmark.getLongitude()) {
				landmarkExists = true;
				break;
			}
		}
		if (!landmarkExists) {
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
		// REFERENCED CODE START
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
			Customer cust = new Customer();
			String[] fields = lineInFile.split(",");

			// attempt to validate the fields in the csv file
			if (fields.length != 4) {
				throw new Exception(
						"The fields do not match the required number of fields and/or type. Please confirm that the file is correct");
			}

			for (int i = 0; i < fields.length; i++) {
				switch (i) {
				case 0:
					cust.setName(fields[0]);
					break;
				case 1:
					cust.setEmail(fields[1]);
					break;
				case 2:
					cust.setPhone(fields[2]);
					break;
				case 3:
					cust.setDOB(fields[3]);
					break;
				}
			}

			/**
			 * check if customer exists in the current list, return a boolean and use the
			 * boolean to determine whether to add it or not
			 **/
			boolean customerExists = false;

			for (Customer c : cList.getCustomers()) {
				if (c.getName().equals(cust.getName()) && c.getEmail().equals(cust.getEmail())
						&& c.getPhone().equals(cust.getPhone()) && c.getDOB().equals(cust.getDOB())) {
					customerExists = true;
					break;
				}
			}
			if (!customerExists) {
				cList.addCustomer(cust);
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
		Customer customer = new Customer(n, e, p, d);

		// check if customer already exists
		boolean customerExists = false;
		for (Customer c : cList.getCustomers()) {
			if (c.getName().equals(customer.getName()) && c.getEmail().equals(customer.getEmail())
					&& c.getPhone().equals(customer.getPhone()) && c.getDOB().equals(customer.getDOB())) {
				customerExists = true;
				break;
			}
		}
		if (!customerExists) {
			cList.addCustomer(customer);
		} else {
			throw new CustomException("Customer already exists");
		}
	}

	public CustomerList getAllCustomers() {
		return cList;
	}

	/**
	 * create a new rental property with the parameters and add to the list
	 * 
	 * @param p Property object
	 * @param c Customer object
	 * @param r rental date
	 * @param d due date
	 */
	public void createRental(Property p, Customer c, LocalDate r, LocalDate d) {
		Rental rentalPpty = new Rental(p, c, r, d);
		rList.addRentals(rentalPpty);
	}

	public RentalList getAllRentals() {
		return rList;
	}
}
