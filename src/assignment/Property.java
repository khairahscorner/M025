package assignment;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * the class is used to create a property object that can be serialised and stored in a file
 * @author Airat Yusuff 22831467
 */
public class Property implements Serializable, DataFormatter {

	private static final long serialVersionUID = 1L;
	private static int lastPropertyIndex = 0;
	
	private String propertyId;
	private String type;
	private String furnishedStatus;
	private double latitude;
	private double longitude;
	private String postcode;
	private LocalDate dateListed;
	private String garden;
	
	private double size;
	private int bedrooms;
	private int bathrooms;
	private double rentPerMonth;
	
	private boolean rentalStatus;
		

	public Property() {
		rentalStatus = false;
		propertyId = "p" + lastPropertyIndex;
		lastPropertyIndex++;

		type = "";
		furnishedStatus = "";
		latitude = 0;
		longitude = 0;
		postcode = "";
		dateListed = LocalDate.now();
		garden = "n";
		size = 0;
		bedrooms = 0;
		bathrooms = 0;
		rentPerMonth = 0;
	}
	
	/**
	 * create a new property
	 * @param t	type
	 * @param f	furnished status
	 * @param p	postcode
	 * @param d	listed date
	 * @param g	has garden?
	 * @param s	property size
	 * @param b	number of bedrooms
	 * @param c	number of bathrooms
	 * @param r	rent PCM	
	 * @param lat	latitude
	 * @param l		longitude
	 */
	public Property(String t, String f, String p, String d, String g, double s, int b, int c, double r, double lat, double l) {	
		rentalStatus = false;
		propertyId = "p" + lastPropertyIndex;
		lastPropertyIndex++;

		type = t;
		furnishedStatus = f;
		latitude = lat;
		longitude = l;
		postcode = p;
		dateListed = LocalDate.parse(d, dateFormatter);
		garden = g;
		size = s;
		bedrooms = b;
		bathrooms = c;
		rentPerMonth = r;
		
	}
	
	public String getType() {
		return type;
	}
	public void setType(String s) {
		type = s;
	}
	
	public String getFurnishedStatus() {
		return furnishedStatus;
	}
	public void setFurnishedStatus(String s) {
		furnishedStatus = s;
	}
	
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double d) {
		latitude = d;
	}
	
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double d) {
		longitude = d;
	}
	
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String s) {
		postcode = s;
	}
	
	public LocalDate getDateListed() {
		return dateListed;
	}
	public void setDateListed(LocalDate s) {
		dateListed = s;
	}
	
	public String getGarden() {
		return garden;
	}
	public void setGarden(String s) {
		garden = s;
	}
	
	public double getSize() {
		return size;
	}
	public void setSize(double s) {
		size = s;
	}
	
	public int getBedrooms() {
		return bedrooms;
	}
	public void setBedrooms(int s) {
		bedrooms = s;
	}
	
	public int getBathrooms() {
		return bathrooms;
	}
	public void setBathrooms(int s) {
		bathrooms = s;
	}
	
	public double getRentPerMonth() {
		return rentPerMonth;
	}
	public void setRentPerMonth(double s) {
		rentPerMonth = s;
	}
	
	public boolean getRentalStatus() {
		return rentalStatus;
	}
	public void setRentalStatus(boolean b) {
		rentalStatus = b;
	}
	
	public String getPropertyId() {
		return propertyId;
	}
	public void setPropertyId(int index) {
		propertyId = "p" + index;
	}
	
	public static void setLastPropertyIndex(int i) {
		lastPropertyIndex = i;
	}
	public static int getLastPropertyIndex() {
		return lastPropertyIndex;
	}
	
	/**
	 * calculate deposit - 6 times the rent
	 * @return deposit amount
	 */
	public double getDeposit() {
		return Double.parseDouble(dpFormatter.format(rentPerMonth * 6));	
	}
	
	/**
	 * calculate agent fee: 20% rent
	 * @return agent fee
	 */
	public double getAgentFee() {
		return Double.parseDouble(dpFormatter.format(0.2 * rentPerMonth));	
	}
	
	/**
	 * format all the attributes of a class object to a string
	 * @param currPpty	current property to print details
	 * @return string containing all property details
	 */
	public static String getPropertyDetails(Property currPpty) {
		return "Type: " + currPpty.getType() + "\nProperty Code: " + currPpty.getPropertyId() + "\nDate Listed: " + currPpty.getDateListed().format(dateFormatter) + "\nRental Status: " + currPpty.getRentalStatus()
		+ "\nFurnishing type: " + currPpty.getFurnishedStatus() + "\nPostcode: " + currPpty.getPostcode() + "\nNo. of Bedrooms:" +
		currPpty.getBedrooms() + "\nNo. of Bathrooms: " + currPpty.getBathrooms() + "\nGarden?: " + currPpty.getGarden().toUpperCase()
		+ "\nMonthly Rent: £" + dpFormatter.format(currPpty.getRentPerMonth()) + "\nDeposit: £" + currPpty.getDeposit()
		+ "\nAgent fee: £" + currPpty.getAgentFee();
		
	}
	
	/**
	 * gets landmarks with postcode corresponding to property postcode, and formats to a string
	 * @param currPpty	current property
	 * @param allLandmarks	landmarks 
	 * @return string with details of all matched 
	 */
	public static String getClosestLandmarksDistance(Property currPpty, List<Landmark> allLandmarks) {
		String pptyPostCode = currPpty.getPostcode().split(" ")[0];
		String str = "\n\nClosest Landmarks (" + pptyPostCode + ") \n\n";
		
		for(int i = 0; i < Landmark.getLastIndex(); i++) {
			String landmarkCode = allLandmarks.get(i).getPostcode().split(" ")[0];
			if(pptyPostCode.equals(landmarkCode)) {
				str += allLandmarks.get(i).getName() + ": " + dpFormatter.format(
						DistanceCalculator.getDistance(currPpty.getLatitude(), currPpty.getLongitude(), allLandmarks.get(i).getLatitude(), 
								allLandmarks.get(i).getLongitude(), "K")) + "km\n";			
			};
		}
		return str;		
	}
	
	
	@Override
	public String toString() {
		return getClass() + " - type=" + type + ", dateListed=" + dateListed + ", latitude =" + latitude + ", longitude= " + longitude +
				", postCode=" + postcode + ", rent =" + rentPerMonth + ", rentalStatus =" + rentalStatus + ", id =" + propertyId + ".";
	}
	

}

