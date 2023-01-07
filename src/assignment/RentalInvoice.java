package assignment;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * this class is used to generate an invoice for a rental property just rented
 * @author Airat Yusuff 22831467
 *
 */
public class RentalInvoice implements Invoice, DataFormatter {
	private long rentMonths;
	private double totalRent;
	private Rental rentalDetails;
	
	/**
	 * constructor to save the referenced rental object and calculate values for the attributes
	 * @param r	Rental object
	 */
	public RentalInvoice(Rental r) {
		rentalDetails = r;
		
		Property rentalPpty = r.getRentalPpty();
		
		setNoOfMonths(r.getRentDate(), r.getDueDate());
		setTotalRent(r.getRentDate(), r.getDueDate(), rentalPpty.getRentPerMonth());
	}
	
	/**
	 * interface method
	 */
	public String generateInvoice() {
		Property rentalPpty = rentalDetails.getRentalPpty();
		String str = "";
		
		str += (" ----- Rental Invoice ------ \n");
		
		str += "Rental Id: "+ rentalDetails.getRentalId() + "\n";
		
		str += "Rent Date: " + rentalDetails.getRentDate().format(dateFormatter) +"\n";
		str += "Due Date: " + rentalDetails.getDueDate().format(dateFormatter) +"\n";		
//	    str += "Rent Duration: "+ rentMonths + " months\n";    
	    str += "Amount Paid for Rent: £"+ totalRent + "\n";
	    str += "Deposit: £"+ rentalPpty.getDeposit() + "\n";
	    str += "Agent Fee: £"+ rentalPpty.getAgentFee() + "\n";
	    str += "Total Amount Paid for Rent: £"+ (totalRent + rentalPpty.getDeposit()) + "\n";
			
		return str;
	}
	
	/**
	 * calculate no of months of rent
	 * @param startDate	rentDate of the property
	 * @param endDate dueDate of the rental
	 */
	private void setNoOfMonths(LocalDate startDate, LocalDate endDate) {
		//convert the days to months and round up the months - 28 days per month;
		double noOfDays = ChronoUnit.DAYS.between(startDate, endDate);
		rentMonths = Math.round(noOfDays/28);
	}
	
	/**
	 * calculate total rent for the calculated months
	 * @param startDate	rentDate of property
	 * @param endDate	dueDate of property
	 * @param rentPerMonth	amount per month
	 */
	private void setTotalRent(LocalDate startDate, LocalDate endDate, double rentPerMonth) {		
		double rentDue = rentMonths * rentPerMonth;		
		totalRent = Double.parseDouble(dpFormatter.format(rentDue));	
	}

}
