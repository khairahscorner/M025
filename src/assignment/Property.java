package assignment;

import java.io.Serializable;
import java.time.LocalDate;

public class Property implements Serializable, DateFormatter {
	private static final long serialVersionUID = 1L; //default fix from compiler
	
	private String propertyId;
	private String type;
	private String furnishedStatus;
	private double latitude;
	private double longitude;
	private String postcode;
	private LocalDate dateListed;
	private String garden;
	
	private int size;
	private int bedrooms;
	private int bathrooms;
	private double rentPerMonth;
	
	private boolean rentalStatus;
	
	private static int lastPropertyIndex = 0;
	
	//to use when creating bulk properties via file
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
	
	
	public Property(String t, String f, String p, String d, String g, int s, int b, int c, double r, double lat, double l) {	
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
	
	public int getSize() {
		return size;
	}
	public void setSize(int s) {
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
	
	public double getDeposit() {
		return Double.parseDouble(dpFormatter.format(rentPerMonth * 6));	
	}
	
	public double getAgentFee() {
		return Double.parseDouble(dpFormatter.format(0.2 * rentPerMonth));	
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
	
	public static String getPropertyDetails(Property currPpty) {
		return "Type: " + currPpty.getFurnishedStatus() + " " + currPpty.getType() + "\nProperty Code: " + currPpty.getPropertyId() + "\nDate Listed: " + currPpty.getDateListed().format(dateFormatter) + "\nRental Status: " + currPpty.getRentalStatus()
		+ "\nFurnishing type: " + currPpty.getFurnishedStatus() + "\nPostcode: " + currPpty.getPostcode() + "\nNo. of Bedrooms:" +
		currPpty.getBedrooms() + "\nNo. of Bathrooms: " + currPpty.getBathrooms() + "\nGarden?: " + currPpty.getGarden().toUpperCase()
		+ "\nMonthly Rent: £" + dpFormatter.format(currPpty.getRentPerMonth()) + "\nDeposit: £" + currPpty.getDeposit()
		+ "\nAgent fee: £" + currPpty.getAgentFee();
		
	}
	
	
	@Override
	public String toString() {
		return getClass() + " - type=" + type + ", dateListed=" + dateListed + ", latitude =" + latitude + ", longitude= " + longitude +
				", postCode=" + postcode + ", rent =" + rentPerMonth + ", rentalStatus =" + rentalStatus + ", id =" + propertyId + ".";
	}
	

}

