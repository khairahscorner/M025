package assignment;

import java.io.Serializable;
import java.time.LocalDate;


/**
 * the class is used to create a new customer object that can be serialised and stored in a file
 * @author Airat YUsuff 22831467
 *
 */
public class Customer implements Serializable, DataFormatter {
	private static final long serialVersionUID = 1L;
	private static int lastCustomerIndex = 0;

	private String name;
	private String email;
	private String phone;
	private LocalDate dob;
	private String custId;
	
	/**
	 * constructor to create a default/empty customer object
	 */
	public Customer() {
		name = "";
		email = "";
		phone = "";
		dob = null;
		custId = "c" + lastCustomerIndex;
		lastCustomerIndex++;
	}
	
	/**
	 * constructor to create a Customer object with the provided parameters
	 * @param n	name
	 * @param e	email
	 * @param p	phone number
	 * @param d	date of birth
	 */
	public Customer(String n, String e, String p, String d)  {
		name = n;
		email = e;
		phone = p;
		dob = LocalDate.parse(d, dateFormatter);
		custId = "c" + lastCustomerIndex;
		lastCustomerIndex++;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String n) {
		name = n;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String e) {
		email = e;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String p) {
		phone = p;
	}


	public LocalDate getDOB() {
		return dob;
	}

	public void setDOB(String d) {
		dob = LocalDate.parse(d, dateFormatter);
	}
	
	public String getCustId() {
		return custId;
	}
	
	public void setCustId(int i) {
		custId = "c" + i;
	}
	
	public static int getLastIndex() {
		return lastCustomerIndex;
	}

	public static void setLastIndex(int i) {
		lastCustomerIndex = i;
	}

	@Override
	public String toString() {
		return "Customer ID: " + custId + "\nName: " + name + "\nEmail: " + email + "\nPhone: " + phone
				+ "\nDate of Birth: " + dob.format(dateFormatter) + "\n";
	}

}
