package assignment;

import java.io.Serializable;
import java.util.*;

/**
 * This class is used to create a new list object consisting of multiple customer objects from the Customer class
 * @author Airat YUsuff 22831467
 *
 */
public class CustomerList implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Customer> cList;

	public  CustomerList() {
		cList = new ArrayList<Customer>();
	}

	public List<Customer> getCustomers() {
		return cList;
	}

	public void addCustomer(Customer c) {
		cList.add(c);
	}

	public void setCustomerList(List<Customer> c) {
		cList = c;
	}
	
	@Override
	public String toString() {
		String s = "";
		for(int i = 0; i < cList.size(); i++) {
			s += ("customers on this list are: " + cList.get(i) + "\n");
		}
		return s;
	}
}

