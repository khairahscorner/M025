package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import assignment.Customer;
import assignment.Property;
import assignment.Rental;
import assignment.RentalList;

class RentalListTest {
	RentalList rList;
	private Property ppty;
	private Customer cust;
	private Rental r;

	@BeforeEach
	void setUp() throws Exception {
		ppty = new Property("Flat", "Unfurnished", "NN5 7QP", "12/01/2023", "y", 1000, 2, 2, 750, 52.25244, -0.91172);
		cust = new Customer("Airah", "airah@email.com", "07934570962", "27/01/2000");
		r = new Rental(ppty, cust, "12/01/2022", "13/02/2022");

		rList = new RentalList();
	}

	@AfterEach
	void tearDown() throws Exception {
		ppty = null;
		cust = null;
		r = null;
		rList = null;
	}

	@Test
	void testPropertyList() {
		assertAll("Rental List", () -> assertEquals(0, rList.getKeys().size()),
				() -> assertEquals(0, rList.getRentals().size()));
	}

	@Test
	void testAddRentals() {
		rList.addRentals(r);

		assertAll("added Rental", () -> assertEquals(1, rList.getKeys().size()),
				() -> assertEquals(1, rList.getRentals().size()));
	}

	@Test
	void testRemoveRental() {
		rList.addRentals(r);

		String str = r.getRentalId();
		rList.removeRental(str);
		assertEquals(0, rList.getRentals().size());
	}

	@Test
	void testSetRentalsList() {
		RentalList testList = new RentalList();
		rList.setRentalsList(testList);
		assertEquals(0, rList.getRentals().size());
	}
	
	@Test
	void testToString() {
		rList.addRentals(r);
		assertEquals(true, rList.toString().contains(rList.getKeys().get(0)));
	}
}
