package assignment;

import java.util.*;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

//<-***** CSYM025 Code FROM NILE - START
public class SendEmail {
	public void send(String recipientEmail, String invoiceType, String invoice) {
		System.out.println("Attempting to send email...");
		// Recipient's email needs to be mentioned.
		String to = recipientEmail;

		// Sender's email ID needs to be mentioned
		String from = "airahyusuff@gmail.com";

		// Assuming you are sending email from localhost
		String host = "smtp.gmail.com";

		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.setProperty("mail.smtp.host", host);
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.debug", "false");
		properties.setProperty("mail.smtp.host", host);
		properties.setProperty("mail.smtp.ssl.enable", "true");

		// Get the default Session object.
		// Session session = Session.getDefaultInstance(properties);
		Session session = Session.getInstance(properties, new SocialAuth());

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Set Subject: header field
			message.setSubject(
					"Here's your " + (invoiceType == "rental" ? "Rental invoice" : "End of tenancy invoice"));

			// Now set the actual message
			message.setText(invoice);

			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

	public boolean sendInvoiceAsPDF(String recipientEmail, String invoiceType, String filename) {
		System.out.println("Attempting to send email...");
		// Recipient's email needs to be mentioned.
		String to = recipientEmail;

		// Sender's email ID needs to be mentioned
		String from = "airahyusuff@gmail.com";

		// Assuming you are sending email from localhost
		String host = "smtp.gmail.com";

		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.setProperty("mail.smtp.host", host);
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.debug", "false");
		properties.setProperty("mail.smtp.host", host);
		properties.setProperty("mail.smtp.ssl.enable", "true");

		// Get the default Session object.
		// Session session = Session.getDefaultInstance(properties);
		Session session = Session.getInstance(properties, new SocialAuth());

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Set Subject: header field
			message.setSubject(
					"Here's your " + (invoiceType == "rental" ? "Rental invoice" : "End of tenancy invoice"));

			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();

			// Fill the message
			messageBodyPart.setText("Please find attached your invoice from CSYM025 Lettings");

			// Create a multipar message
			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			// Part two is attachment
			messageBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);

			// Send the complete message parts
			message.setContent(multipart);
			Transport.send(message);
			System.out.println("Sent message successfully....");
			return true;
		} catch (MessagingException mex) {
			mex.printStackTrace();
			return false;
		}
	}

	class SocialAuth extends Authenticator {

		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			// app password
			return new PasswordAuthentication("airahyusuff@gmail.com", "smxgctooiyuxzfwz");

		}
	}
}
//->***** CSYM025 Code FROM NILE - END
