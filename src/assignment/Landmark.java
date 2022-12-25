package assignment;

import java.io.Serializable;

public class Landmark implements Serializable {
	private static final long serialVersionUID = 1L; //default fix from compiler
	
	private String landmarkId;
	private String name;
	private double latitude;
	private double longitude;
	private String postcode;
	
	private static int lastIndex = 0;
		
	
	
	public Landmark() {	
		landmarkId = "l" + lastIndex;
		name = "";
		latitude = 0;
		longitude = 0;
		postcode = "";
		lastIndex++;
	}

	public Landmark(String n, String p, double lat, double l) {	
		landmarkId = "l" + lastIndex;
		name = n;
		latitude = lat;
		longitude = l;
		postcode = p;
		lastIndex++;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String s) {
		name = s;
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
		
	public String getLandmarkId() {
		return landmarkId;
	}
	
	public void setLandmarkId(int index) {
		landmarkId = "l" + index;
	}
	
	public static void setLastIndex(int i) {
		lastIndex = i;
	}
	public static int getLastIndex() {
		return lastIndex;
	}

	
	@Override
	public String toString() {
		return getClass() + " - name=" + name + ", latitude =" + latitude + ", longitude= " + longitude + ", postCode=" + postcode + ".";
	}

}

