package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import assignment.DistanceCalculator;
import assignment.Landmark;
import assignment.LandmarkList;
import assignment.Property;
import assignment.PropertyActions;
import assignment.PropertyList;

class PropertyActionsTest {
	PropertyList pList;
	Property p0;
	Property p1;

	LandmarkList lList;
	Landmark l0;
	Landmark l1;

	@BeforeEach
	void setUp() throws Exception {
		Property.setLastPropertyIndex(0);
		pList = new PropertyList();
		p0 = new Property("Flat", "Unfurnished", "NN5 7QP", "12/01/2023", "y", 1000, 2, 2, 750, 52.23383, -0.94446);
		p1 = new Property("Bungalow", "Furnished", "NN3 7QP", "15/09/2022", "n", 1000, 1, 1, 500, 52.2546, -0.85976);
		pList.addProperty(p0);
		pList.addProperty(p1);

		Landmark.setLastIndex(0);
		lList = new LandmarkList();
		l0 = new Landmark("Station", "NN3", 52.2371, -0.90631);
		l1 = new Landmark("Uni", "NN5", 52.23994, -0.93415);
		lList.addLandmark(l0);
		lList.addLandmark(l1);
	}

	@AfterEach
	void tearDown() throws Exception {
		p0 = null;
		p1 = null;
		pList = null;

		l0 = null;
		l1 = null;
		lList = null;
	}

	@Test
	void testGetDistanceToLandmark() {
		String result = PropertyActions.getDistanceToLandmark(p0, l0);
		assertEquals(0.99, DistanceCalculator.getDistance(p0.getLatitude(), p0.getLongitude(), l1.getLatitude(),
				l1.getLongitude(), "K"), 0.03);
		assertEquals(true, result.startsWith("Distance to " + l0.getName() + ": "));
	}

	@Test
	void testGetClosestLandmarksDistance() {
		ArrayList<Landmark> l = new ArrayList<Landmark>();
		l.add(l0);
		l.add(l1);

		assertEquals(3.74, DistanceCalculator.getDistance(p1.getLatitude(), p1.getLongitude(), l0.getLatitude(),
				l0.getLongitude(), "K"), 0.03);
		String result = PropertyActions.getClosestLandmarksDistance(p1, l);

		assertEquals(true, result.contains(l0.getName() + ": "));
	}

	@Test
	void testFilterPropertiesByRentalStatus() {
		p0.setRentalStatus(true);
		
		assertAll("Filtered Properties",
	            () -> assertEquals(p0, PropertyActions.filterPropertiesByRentalStatus(pList, "Rented").getProperties().get("p0")),
	            () -> assertEquals(p1, PropertyActions.filterPropertiesByRentalStatus(pList, "Available").getProperties().get("p1"))
	        );
	}

	@Test
	void testFilterPropertiesByBedrooms() {
		assertEquals(p1, PropertyActions.filterPropertiesByBedrooms(pList, 1).getProperties().get("p1"));
		p0.setBedrooms(3);
		assertEquals(p0, PropertyActions.filterPropertiesByBedrooms(pList, 3).getProperties().get("p0"));
	}

	@Test
	void testFilterPropertiesByBathrooms() {
		assertEquals(p0, PropertyActions.filterPropertiesByBathrooms(pList, 2).getProperties().get("p0"));
		p1.setBathrooms(3);
		assertEquals(p1, PropertyActions.filterPropertiesByBathrooms(pList, 3).getProperties().get("p1"));
	}

	@Test
	void testFilterPropertiesBySearch() {
		assertEquals(p1, PropertyActions.filterPropertiesBySearch(pList, "bunga").getProperties().get("p1"));
	}

	@Test
	void testFilterPropertiesByPostcode() {
		assertEquals(p0, PropertyActions.filterPropertiesByPostcode(pList, "NN5").getProperties().get("p0"));
	}

	@Test
	void testSortPropertiesByPrice() {
		assertEquals(p1, PropertyActions.sortPropertiesByPrice(pList, "ASC").getProperties().get("p1"));
		assertEquals(p0, PropertyActions.sortPropertiesByPrice(pList, "DESC").getProperties().get("p0"));
	}

	@Test
	void testSortPropertiesByDate() {
		assertEquals(p0, PropertyActions.sortPropertiesByDate(pList, "DESC").getProperties().get("p0"));
		assertEquals(p1, PropertyActions.sortPropertiesByDate(pList, "ASC").getProperties().get("p1"));
	}

}
