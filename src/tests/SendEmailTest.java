package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import assignment.SendEmail;

class SendEmailTest {
	SendEmail se;
	
	@BeforeEach
	void setUp() throws Exception {
		se = new SendEmail();
	}

	@Test
	void testSend() {
		se.send("airahyusuff@gmail.com", "rental", "This is a JUnit test");
	}

//	@Test
//	void testSendInvoiceAsPDF() {
//		boolean result = se.sendInvoiceAsPDF("airahyusuff@gmail.com", "eot", "mockInvoice.pdf");
//		assertEquals(true, result);
//		
//	}

}
