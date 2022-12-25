package assignment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RentalList implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<Rental> rList;

	public  RentalList() {
		rList = new ArrayList<Rental>();
	}

	public List<Rental> getRentals() {
		return rList;
	}

	public void addRentals(Rental r) {
		rList.add(r);
	}

	public void setRentalsList(List<Rental> r) {
		rList = r;
	}
	
	@Override
	public String toString() {
		String s = "";
		for(int i = 0; i < rList.size(); i++) {
			s += ("rental is: " + rList.get(i) + "\n");
		}
		return s;
	}
}
