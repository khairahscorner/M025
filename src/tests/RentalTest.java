package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import assignment.Customer;
import assignment.DataFormatter;
import assignment.Property;
import assignment.Rental;

class RentalTest {
	private Property ppty;
	private Customer cust;
	private Rental r;

	@BeforeEach
	void setUp() throws Exception {
		Rental.setLastRentalIndex(0);
		ppty = new Property("Flat", "Unfurnished", "NN5 7QP", "12/01/2023", "y", 1000, 2, 2, 750, 52.25244, -0.91172);
		cust = new Customer("Airah", "airah@email.com", "07934570962", "27/01/2000");
		r = new Rental(ppty, cust, "12/01/2022", "13/02/2022");
	}

	@AfterEach
	void tearDown() throws Exception {
		ppty = null;
		cust = null;
		r = null;
	}

	@Test
	void testRentalCustomer() {
		Customer c =  new Customer("Yusuff", "airah@email.com", "07934570962", "27/01/2000");
		r.setRentalCustomer(c);
		assertEquals(c, r.getRentalCustomer());
	}

	@Test
	void testRentalProperty() {
		Property p = new Property("Detached", "Furnished", "NN3 7QP", "12/01/2023", "y", 1000, 2, 2, 750, 52.25244, -0.91172);
		r.setRentalPpty(p);
		assertEquals(p, r.getRentalPpty());
	}
	
	@Test
	void testRentDate() {
		r.setRentDate(LocalDate.parse("18/05/2021", DataFormatter.dateFormatter));
		assertEquals("18/05/2021", r.getRentDate().format( DataFormatter.dateFormatter));
	}

	@Test
	void testDueDate() {
		r.setDueDate(LocalDate.parse("13/03/2021", DataFormatter.dateFormatter));
		assertEquals("13/03/2021", r.getDueDate().format( DataFormatter.dateFormatter));
	}
	
	@Test
	void testIndexRentalId() {
		Rental.setLastRentalIndex(0);
		assertEquals(0, Rental.getLastRentalIndex());
		assertEquals("CSYM025R0", r.getRentalId());
	}

	@Test
	void testToString() {
		assertEquals(true, r.toString().contains(ppty.getPropertyId()));
	}

}
