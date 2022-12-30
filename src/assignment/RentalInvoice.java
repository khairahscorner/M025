package assignment;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class RentalInvoice implements Invoice, DataFormatter {
	
	private long rentMonths;
	private double totalRent;
	private Rental rentalDetails;
	
	public RentalInvoice(Rental r) {
		rentalDetails = r;
		
		Property rentalPpty = r.getRentalPpty();
		
		setNoOfMonths(r.getRentDate(), r.getDueDate());
		setTotalRent(r.getRentDate(), r.getDueDate(), rentalPpty.getRentPerMonth());
	}
	
	
	public String generateInvoice() {
		Property rentalPpty = rentalDetails.getRentalPpty();
		String str = "";
		
		str += (" ----- Rental Invoice ------ \n");
		
		str += "Rental Id: "+ rentalDetails.getRentalId() + "\n";
		
		str += "Rent Date: " + rentalDetails.getRentDate().format(dateFormatter) +"\n";
		str += "Due Date: " + rentalDetails.getDueDate().format(dateFormatter) +"\n";
		
	    str += "Rent Duration: "+ rentMonths + " months\n";
	    
	    str += "Amount Paid for Rent: £"+ totalRent + "\n";
	    str += "Deposit: £"+ rentalPpty.getDeposit() + "\n";
	    str += "Agent Fee: £"+ rentalPpty.getAgentFee() + "\n";
	    str += "Total Amount Paid for Rent: £"+ (totalRent + rentalPpty.getDeposit()) + "\n";
			
		return str;
	}
	
	private void setNoOfMonths(LocalDate startDate, LocalDate endDate) {
		//convert the days to months and round up the months - 28 days per month;
		double noOfDays = ChronoUnit.DAYS.between(startDate, endDate);
		rentMonths = Math.round(noOfDays/28);
	}
	
	private void setTotalRent(LocalDate startDate, LocalDate endDate, double rentPerMonth) {		
		double rentDue = rentMonths * rentPerMonth;		
		totalRent = Double.parseDouble(dpFormatter.format(rentDue));	
	}

}
