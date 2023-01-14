package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import assignment.Landmark;

class LandmarkTest {
	Landmark landmark;

	@BeforeEach
	void setUp() throws Exception {
		Landmark.setLastIndex(0);
		landmark = new Landmark("Station", "NN1 3RF", 52.25244, -0.91172);
	}

	@AfterEach
	void tearDown() throws Exception {
		landmark = null;
	}

	@Test
	void testLandmark() {
		assertAll("Landmark Attributes",
	            () -> assertEquals("Station", landmark.getName()),
	            () -> assertEquals("NN1 3RF", landmark.getPostcode()),
	            () -> assertEquals(52.25244, landmark.getLatitude(), 0.002),
	            () -> assertEquals(-0.91172, landmark.getLongitude(), 0.002),
	            () -> assertEquals(1, Landmark.getLastIndex())
	        );
	}

	@Test
	void testName() {
		landmark.setName("Bustop");
		assertEquals("Bustop", landmark.getName());
	}
	
	@Test
	void testLatitude() {
		landmark.setLatitude(52.2524);
		assertEquals(52.25246, landmark.getLatitude(), 0.0002);
	}

	@Test
	void testLongitude() {
		landmark.setLongitude(-0.8323);
		assertEquals(-0.8321, landmark.getLongitude(), 0.002);
	}

	@Test
	void testPostcode() {
		landmark.setPostcode("NN1 3RF");
		assertEquals("NN1 3RF", landmark.getPostcode());
	}
	
	@Test
	void testLandmarkId() {
		landmark.setLandmarkId(0);
		assertEquals("l0", landmark.getLandmarkId());
	}
	
	@Test
	void testLastIndex() {
		Landmark.setLastIndex(2);
		assertEquals(2, Landmark.getLastIndex());
	}
	
	@Test
	void testToString() {
		assertEquals(true, landmark.toString().contains("Station"));
	}

}
