package assignment;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * This class is used to create a new Rental object that can be serialised and stored in a file
 * @author Airat Yusuff 22831467
 *
 */
public class Rental implements Serializable, DataFormatter {
	private static final long serialVersionUID = 1L;
	
	private Property ppty;
	private Customer cust;
	private LocalDate rentDate;
	private LocalDate dueDate;
	private String rentalId;
	
	private static int lastRentalIndex = 0;
	
	/*
	 * constructor to create a new Rental object
	 */
	public Rental(Property p, Customer c, String r, String d) {
		ppty = p;
		cust = c;
		rentDate = LocalDate.parse(r, dateFormatter);
		dueDate = LocalDate.parse(d, dateFormatter);
		rentalId = "CSYM025R" + lastRentalIndex;
		lastRentalIndex++;		
	}
	
	public Property getRentalPpty() {
		return ppty;
	}

	public void setRentalPpty(Property p) {
		ppty = p;
	}

	public Customer getRentalCustomer() {
		return cust;
	}

	public void setRentalCustomer(Customer c) {
		cust = c;
	}

	public LocalDate getRentDate() {
		return rentDate;
	}

	public void setRentDate(LocalDate d) {
		rentDate = d;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate d) {
		dueDate = d;
	}

	public String getRentalId() {
		return rentalId;
	}

	
	public static int getLastRentalIndex() {
		return lastRentalIndex;
	}

	public static void setLastRentalIndex(int i) {
		lastRentalIndex = i;
	}
	
	public String toString() {
		return rentalId + " name: " + ppty.getPropertyId();
	}
	
}

