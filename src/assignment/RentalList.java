package assignment;

import java.io.Serializable;
import java.util.*;

/**
 * This class is used to create a new list object consisting of multiple rental objects from the Rental class
 * @author Airat Yusuff 22831467
 *
 */
public class RentalList implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//hash the list and create a rental keys list for ease of use in other classes
	private Map<String, Rental> rList;
	private List<String> rentalKeys;

	public  RentalList() {
		rList = new HashMap<String, Rental>();
		rentalKeys = new ArrayList<String>();
	}
	

	public List<String> getKeys() {
		return rentalKeys; 
	}

	public Map<String, Rental> getRentals() {
		return rList;
	}

	public void addRentals(Rental r) {
		rList.put(r.getRentalId(), r);
		rentalKeys.add(r.getRentalId());
	}
	
	public void removeRental(String rentalId) {
		rList.remove(rentalId);	
		rentalKeys.remove(rentalId);
	}

	public void setRentalsList(RentalList r) {
		rList = r.getRentals();
		rentalKeys = r.getKeys();
	}
	
	@Override
	public String toString() {
		String s = "";
		for(String key: rentalKeys) {
			s += ("rental is: " + rList.get(key) + "\n");
		}
		return s;
	}
}
