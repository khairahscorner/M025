package assignment;

/**
 * interface for the RentalInvoice and TenancyEndInvoice classes
 * @author Airat YUsuff 22831467
 *
 */
public interface Invoice {
	
	/**
	 * generate a string containing all the invoice details
	 * @return string with the invoice summary
	 */
	String generateInvoice();
	
}
