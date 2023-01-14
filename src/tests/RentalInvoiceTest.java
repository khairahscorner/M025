package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import assignment.Customer;
import assignment.Property;
import assignment.Rental;
import assignment.RentalInvoice;

class RentalInvoiceTest {
	private Property ppty;
	private Customer cust;
	private Rental r;
	private RentalInvoice t;
	
	@BeforeEach
	void setUp() throws Exception {
		ppty = new Property("Flat", "Unfurnished", "NN5 7QP", "12/01/2023", "y", 1000, 2, 2, 750, 52.25244, -0.91172);
		cust = new Customer("Airah", "airah@email.com", "07934570962", "27/01/2000");	
		r = new Rental(ppty, cust, "12/01/2022", "13/02/2022");
		t = new RentalInvoice(r);
	}

	@AfterEach
	void tearDown() throws Exception {
		ppty = null;
		cust = null;
		r = null;
		t = null;
	}

	@Test
	void testRentalInvoice() {
		assertAll("Rental Invoice Attributes",
	            () -> assertEquals(1, t.getNoOfMonths())
	        );
	}
	
	@Test
	void testMonths() {
		assertEquals(1, t.getNoOfMonths());
	}
	
	@Test
	void testTotalRent() {
		assertEquals(750, t.getTotalRent());
	}

	@Test
	void testGenerateInvoice() {
		assertEquals(5400, t.getTotalAmountPaid());
		
		String str = "";
		str += "Rental Id: " + r.getRentalId() + "\n";
		str += "Rent Date: 12/01/2022\n";
		str += "Due Date: 13/02/2022\n";
		str += "Amount Paid for Rent: £750.0\n";
		str += "Deposit: £4500.0\n";
		str += "Agent Fee: £150.0\n";
		str += "Total Amount Paid: £5400.0\n";
		
		assertEquals(str, t.generateInvoice());
	}

}
