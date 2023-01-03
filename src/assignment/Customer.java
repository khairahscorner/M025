package assignment;

import java.io.Serializable;
import java.time.LocalDate;


public class Customer implements Serializable, DataFormatter {
	private static final long serialVersionUID = 1L;
	private static int lastCustomerIndex = 0;

	private String name;
	private String email;
	private String phone;
	private LocalDate dob;
	private String custId;
	
	
	public Customer() {
		name = "";
		email = "";
		phone = "";
		dob = null;
		custId = "";
	}
	
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
