package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import assignment.Customer;
import assignment.Property;
import assignment.Rental;
import assignment.TenancyEndInvoice;

class TenancyEndInvoiceTest {
	private Property ppty;
	private Customer cust;
	private Rental r;
	private TenancyEndInvoice t;
	
	@BeforeEach
	void setUp() throws Exception {
		ppty = new Property("Flat", "Unfurnished", "NN5 7QP", "12/01/2023", "y", 1000, 2, 2, 750, 52.25244, -0.91172);
		cust = new Customer("Airah", "airah@email.com", "07934570962", "27/01/2000");	
		r = new Rental(ppty, cust, "12/01/2022", "13/01/2022");
		t = new TenancyEndInvoice(r, 1000);
	}

	@AfterEach
	void tearDown() throws Exception {
		ppty = null;
		cust = null;
		r = null;
		t = null;
	}

	@Test
	void testGetDeductions() {
		t.setDeductions(700.55);
		assertEquals(700.54, t.getDeductions(), 0.02);
	}


	@Test
	void testGetInvoiceId() {
		String str = r.getRentalId();
		assertEquals("EOT-" + str, t.getInvoiceId());
	}

	@Test
	void testGenerateInvoice() {
		assertEquals(3500, t.getRemainingAmount());
		
		String str = "";
		str += "Invoice Id: " + t.getInvoiceId() + "\n";
		str += "Rental Id: " + r.getRentalId() + "\nRent Date: 12/01/2022\nDue Date: 13/01/2022\n";
		str += "Deposit: £4500.0\n";
		str += "Damage Deductions: £1000.0\n";
		str += "Deposit Amount to Return: £3500.0\n";
		assertEquals(str, t.generateInvoice());
		
		t.setDeductions(5000);
		assertEquals(true, t.generateInvoice().contains("Outstanding Amount Owed "));
		assertEquals(500, t.getRemainingAmount());
	}


}
