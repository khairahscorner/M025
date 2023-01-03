package assignment;

/**
 * This class is used to generate an invoice for a rental property at the end of the tenancy
 * @author Airat Yusuff 22831467
 *
 */
public class TenancyEndInvoice implements Invoice, DataFormatter {
	
	private double deductions;
	private Rental rentalDetails;
	private String invoiceId;
	

	/**
	 * constructor to save the referenced rental object and calculate values for the attributes
	 * @param r	Rental object
	 * @param d	damage deductions
	 */
	public TenancyEndInvoice(Rental r, double d) {
		rentalDetails = r;
		deductions = d;
		invoiceId = "EOT-" + r.getRentalId();
	}
	
	public double getDeductions() {
		return deductions;
	}
	
	public void setDeductions(double d) {
		deductions = d;
	}
	
	public String getInvoiceId() {
		return invoiceId;
	}
	
	/**
	 * interface method
	 */
	public String generateInvoice() {
		Property rentalPpty = rentalDetails.getRentalPpty();
		String str = "";
		
		str += (" ----- End of Tenancy Invoice ------ \n");
		
		str += "Invoice Id: "+ invoiceId + "\n";
		
		str += "Rental Id: "+ rentalDetails.getRentalId() + "\n";
		
		str += "Rent Date: " + rentalDetails.getRentDate().format(dateFormatter) +"\n";
		str += "Due Date: " + rentalDetails.getDueDate().format(dateFormatter) +"\n";
		
	    
	    str += "Deposit: £"+ rentalPpty.getDeposit() + "\n";
	    str += "Damage Deductions: £"+ deductions + "\n";
	    if(deductions > rentalPpty.getDeposit()) {
	    	str += "Outstanding Amount Owed by Customer: £"+ (deductions - rentalPpty.getDeposit()) + "\n";
	    } 
	    else {
	    	str += "Deposit Amount to Return: £"+ (rentalPpty.getDeposit() - deductions) + "\n";
	    }
			
		return str;
	}
}


