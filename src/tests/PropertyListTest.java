package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import assignment.Property;
import assignment.PropertyList;

class PropertyListTest {
	private PropertyList plist;

	@BeforeEach
	void setUp() throws Exception {
		plist = new PropertyList();
	}

	@AfterEach
	void tearDown() throws Exception {
		plist = null;
	}

	@Test
	void testPropertyList() {
		assertAll("Property List", () -> assertEquals(0, plist.getKeys().size()),
				() -> assertEquals(0, plist.getProperties().size()));
	}

	@Test
	void testAddProperty() {
		Property p0 = new Property();
		plist.addProperty(p0);
		assertEquals(1, plist.getProperties().size());
		assertEquals(1, plist.getKeys().size());
	}

	@Test
	void testRemoveProperty() {
		plist.removeProperty("p0");
		assertEquals(0, plist.getKeys().size());
		assertEquals(0, plist.getProperties().size());
	}
	
	@Test
	void testToString() {
		Property p0 = new Property("Flat", "Unfurnished", "NN5 7QP", "12/01/2023", "y", 1000, 2, 2, 750, 52.25244, -0.91172);
		plist.addProperty(p0);
		assertEquals(true, plist.toString().contains(plist.getKeys().get(0)));
	}

}
