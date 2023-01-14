package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import assignment.Landmark;
import assignment.LandmarkList;

class LandmarkListTest {
	LandmarkList lList;

	@BeforeEach
	void setUp() throws Exception {
		lList = new LandmarkList();
	}

	@AfterEach
	void tearDown() throws Exception {
		lList = null;
	}

	@Test
	void testLandmarkList() {
		assertEquals(0, lList.getLandmarks().size());
	}

	@Test
	void testAddLandmark() {
		Landmark l0 = new Landmark();
		lList.addLandmark(l0);
		assertEquals(1, lList.getLandmarks().size());
	}

	@Test
	void testSetLandmarkList() {
		Landmark l0 = new Landmark();
		ArrayList<Landmark> testList = new ArrayList<Landmark>();
		testList.add(l0);
		lList.setLandmarkList(testList);
		
		assertEquals(1, lList.getLandmarks().size());
	}
	
	@Test
	void testToString() {
		Landmark l0 = new Landmark("Station", "NN1 3RF", 52.25244, -0.91172);;
		lList.addLandmark(l0);
		assertEquals(true, lList.toString().contains(lList.getLandmarks().get(0).getName()));
	}

}
