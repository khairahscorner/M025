package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import assignment.DataFormatter;
import assignment.Property;

class PropertyTest implements DataFormatter {
	Property ppty;
	
	@BeforeEach
	void setUp() throws Exception {
		Property.setLastPropertyIndex(0);
		ppty = new Property("Flat", "Unfurnished", "NN5 7QP", "12/01/2023", "y", 1000, 2, 2, 750, 52.25244, -0.91172);
	}

	@AfterEach
	void tearDown() throws Exception {
		ppty = null;
	}

	@Test
	void testEmptyProperty() {
		Property emptyPpty = new Property();
		assertAll("Property Attributes",
	            () -> assertEquals("", emptyPpty.getType()),
	            () -> assertEquals("", emptyPpty.getFurnishedStatus()),
	            () -> assertEquals("", emptyPpty.getPostcode()),
	            () -> assertEquals(0, emptyPpty.getSize()),
	            () -> assertEquals(0, emptyPpty.getBathrooms()),
	            () -> assertEquals(0, emptyPpty.getBedrooms()),
	            () -> assertEquals(0, emptyPpty.getRentPerMonth()),
	            () -> assertEquals(false, emptyPpty.getRentalStatus()),
	            () -> assertEquals(2, Property.getLastPropertyIndex())
	        );
	}

	@Test
	void testType() {
		ppty.setType("Bungalow");
		assertEquals("Bungalow", ppty.getType());
	}

	@Test
	void testFurnishedStatus() {
		ppty.setFurnishedStatus("semi-furnished");
		assertEquals("semi-furnished", ppty.getFurnishedStatus());
	}

	@Test
	void testLatitude() {
		ppty.setLatitude(52.2524);
		assertEquals(52.25246, ppty.getLatitude(), 0.0002);
	}

	@Test
	void testLongitude() {
		ppty.setLongitude(-0.8323);
		assertEquals(-0.8321, ppty.getLongitude(), 0.002);
	}

	@Test
	void testPostcode() {
		ppty.setPostcode("NN1 3RF");
		assertEquals("NN1 3RF", ppty.getPostcode());
	}

	@Test
	void testDateListed() {
		ppty.setDateListed(LocalDate.parse("13/01/2023", dateFormatter));
		assertEquals(LocalDate.parse("13/01/2023", dateFormatter), ppty.getDateListed());
	}

	@Test
	void testGarden() {
		ppty.setGarden("yes");
		assertEquals("yes", ppty.getGarden());
	}

	@Test
	void testSize() {
		ppty.setSize(1000);
		assertEquals(1000, ppty.getSize());
	}

	@Test
	void testBedrooms() {
		ppty.setBedrooms(2);
		assertEquals(2, ppty.getBedrooms());
	}
	
	@Test
	void testBathrooms() {
		ppty.setBathrooms(1);
		assertEquals(1, ppty.getBathrooms());
	}

	@Test
	void testRentPerMonth() {
		assertEquals(750, ppty.getRentPerMonth());
		ppty.setRentPerMonth(500);
		assertEquals(500, ppty.getRentPerMonth());
	}
	
	@Test
	void testSetRentalStatus() {
		assertEquals(false, ppty.getRentalStatus());
		ppty.setRentalStatus(true);
		assertEquals(true, ppty.getRentalStatus());
	}

	@Test
	void testPropertyId() {
		ppty.setPropertyId(0);
		assertEquals("p0", ppty.getPropertyId());
	}
	
	@Test
	void testPropertyIndex() {
		Property.setLastPropertyIndex(2);
		assertEquals(2, Property.getLastPropertyIndex());
	}

	@Test
	void testGetDeposit() {
		assertEquals(4500, ppty.getDeposit(), 0.02);
	}

	@Test
	void testGetAgentFee() {
		assertEquals(150, ppty.getAgentFee(), 0.02);
	}
	
	@Test
	void testGetPropertyDetails() {
		assertEquals(true, ppty.getPropertyDetails().contains("NN5 7QP"));
	}
	
	@Test
	void testToString() {
		assertEquals(true, ppty.toString().contains("NN5 7QP"));
	}
	

}
