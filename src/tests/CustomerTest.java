package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import assignment.Customer;

class CustomerTest {
	private Customer cust;

	@BeforeEach
	void setUp() throws Exception {
		Customer.setLastIndex(0);
		cust = new Customer("Airah", "airah@email.com", "07934570962", "27/01/2000");
	}

	@AfterEach
	void tearDown() throws Exception {
		cust = null;
	}

	@Test
	void testCustomer() {
		assertAll("Customer Attributes", () -> assertEquals("Airah", cust.getName()),
				() -> assertEquals("airah@email.com", cust.getEmail()),
				() -> assertEquals("07934570962", cust.getPhone()), () -> assertEquals("27/01/2000", cust.getDOB()),
				() -> assertEquals(1, Customer.getLastIndex()));

		Customer emptyCust = new Customer();
		assertAll("Customer Attributes", () -> assertEquals("", emptyCust.getName()),
				() -> assertEquals("", emptyCust.getEmail()), () -> assertEquals("", emptyCust.getPhone()));
	}

	@Test
	void testSetters() {
		cust.setName("Yusuff");
		cust.setEmail("airah@test.com");
		cust.setPhone("012345678");
		cust.setDOB("12/01/2004");
		cust.setCustId(0);

		assertAll("Customer Attributes", () -> assertEquals("Yusuff", cust.getName()),
				() -> assertEquals("airah@test.com", cust.getEmail()), () -> assertEquals("012345678", cust.getPhone()),
				() -> assertEquals("12/01/2004", cust.getDOB()), () -> assertEquals("c0", cust.getCustId()));
	}

	@Test
	void testLastIndex() {
		Customer.setLastIndex(2);
		assertEquals(2, Customer.getLastIndex());
	}

	@Test
	void testToString() {
		assertEquals(true, cust.toString().contains("Airah"));
	}

}
