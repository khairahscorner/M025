package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import assignment.AdminList;

class AdminListTest {
	AdminList users;
	
	@BeforeEach
	void setUp() throws Exception {
		users = new AdminList();
	}

	@AfterEach
	void tearDown() throws Exception {
		users = null;
	}

	@Test
	void testAdminList() {
		assertEquals(0, users.getAdmins().size());
	}

	@Test
	void testAddAdmin() {
		users.addAdmin("admin", "admin");
		assertEquals(1, users.getAdmins().size());
	}
	
	@Test
	void testToString() {
		users.addAdmin("admin", "admin");
		assertEquals(true, users.toString().contains(users.getAdmins().get("admin")));
	}

}
