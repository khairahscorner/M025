package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import assignment.FileDataHandler;
import assignment.PropertyList;
import assignment.RentalList;

class FileDataHandlerTest {
	private static final Properties properties = new Properties();

	@BeforeEach
	void setUp() throws Exception {
		properties.load(new FileInputStream("allFiles.properties"));
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testInitialise() {
		FileDataHandler.readProperties(properties);
		assertEquals("PropertyList.dat", properties.getProperty("properties.file"));
		assertEquals("CustomerList.dat", properties.getProperty("customers.file"));
		assertEquals("RentalList.dat", properties.getProperty("rentals.file"));
		assertEquals("LandmarkList.dat", properties.getProperty("landmarks.file"));
		assertEquals("admins.dat", properties.getProperty("users.file"));		
	}

}
