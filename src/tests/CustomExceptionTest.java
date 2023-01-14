package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import assignment.CustomException;

class CustomExceptionTest {

	@Test
	void testCustomException() {
		try {
			throw new CustomException("Test");
		}
		catch(Exception e) {
			assertEquals("Test", e.getMessage());			
		}
	}

}
