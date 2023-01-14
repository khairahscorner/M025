package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import assignment.Customer;
import assignment.CustomerList;

class CustomerListTest {
	CustomerList cList;

	@BeforeEach
	void setUp() throws Exception {
		cList = new CustomerList();
	}

	@AfterEach
	void tearDown() throws Exception {
		cList = null;
	}

	@Test
	void testCustomerList() {
		assertEquals(0, cList.getCustomers().size());
	}

	@Test
	void testAddCustomer() {
		Customer c0 = new Customer();
		cList.addCustomer(c0);
		assertEquals(1, cList.getCustomers().size());
	}

	@Test
	void testSetCustomerList() {
		Customer c0 = new Customer();
		ArrayList<Customer> testList = new ArrayList<Customer>();
		testList.add(c0);
		cList.setCustomerList(testList);
		
		assertEquals(1, cList.getCustomers().size());
	}
	
	@Test
	void testToString() {
		Customer c0 = new Customer("Airah", "airah@email.com", "+44123445556", "12/12/2004");
		cList.addCustomer(c0);
		assertEquals(true, cList.toString().contains(cList.getCustomers().get(0).getName()));
	}

}
