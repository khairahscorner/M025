package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import assignment.DistanceCalculator;

class DistanceCalculatorTest {

	@Test
	void testZeroDistance() {
		double result = DistanceCalculator.getDistance(0, 0, 0, 0, "K");
		assertEquals(0, result);	
	}
	
	@Test
	void testDistance() {
		double result = DistanceCalculator.getDistance(52.23548, -0.89199, 52.2371, -0.90631, "K");
		double result1 = DistanceCalculator.getDistance(52.23548, -0.89199, 52.2371, -0.90631, "N");
		assertEquals(0.99, result, 0.05);
		assertEquals(0.53, result1, 0.05);
	}

}
