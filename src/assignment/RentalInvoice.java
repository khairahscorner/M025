package assignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * this class is used to generate an invoice for a rental property just rented
 * 
 * @author Airat Yusuff 22831467
 *
 */
public class RentalInvoice implements Invoice, DataFormatter {
	private double totalRent;
	private Rental rentalDetails;

	/**
	 * constructor to save the referenced rental object and calculate values for the
	 * attributes
	 * 
	 * @param r Rental object
	 */
	public RentalInvoice(Rental r) {
		rentalDetails = r;

		Property rentalPpty = r.getRentalPpty();

		setTotalRent(r.getRentDate(), r.getDueDate(), rentalPpty.getRentPerMonth());
	}

	/**
	 * interface method
	 */
	public String generateInvoice() {
		Property rentalPpty = rentalDetails.getRentalPpty();
		String str = "";

		str += "Rental Id: " + rentalDetails.getRentalId() + "\n";

		str += "Rent Date: " + rentalDetails.getRentDate().format(dateFormatter) + "\n";
		str += "Due Date: " + rentalDetails.getDueDate().format(dateFormatter) + "\n";
		str += "Amount Paid for Rent: £" + totalRent + "\n";
		str += "Deposit: £" + rentalPpty.getDeposit() + "\n";
		str += "Agent Fee: £" + rentalPpty.getAgentFee() + "\n";
		str += "Total Amount Paid for Rent: £" + (totalRent + rentalPpty.getDeposit()) + "\n";

		return str;
	}

	// <-***** CSYM025 Code FROM NILE - START
	public void generateInvoiceAsPDF(String filename) {
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream(new File(filename)));

			// open
			document.open();
			Font f = new Font();
			f.setStyle(Font.BOLD);
			f.setSize(20);

			Paragraph p = new Paragraph("Rental Invoice", f);
			p.setAlignment(Element.ALIGN_CENTER);
			document.add(p);

			Paragraph p2 = new Paragraph();
			p2.add(generateInvoice());

			document.add(p2);

			// close
			document.close();

			System.out.println("Done generating pdf");

		} catch (FileNotFoundException | DocumentException e) {
			System.out.println(e.toString());
		}
	}
	// ->***** CSYM025 Code FROM NILE - END

	/**
	 * calculate total rent for the calculated months
	 * 
	 * @param startDate    rentDate of property
	 * @param endDate      dueDate of property
	 * @param rentPerMonth amount per month
	 */
	private void setTotalRent(LocalDate startDate, LocalDate endDate, double rentPerMonth) {
		// convert the days to months and round up the months - 28 days per month;
		double noOfDays = ChronoUnit.DAYS.between(startDate, endDate);
		double rentMonths = Math.round(noOfDays / 28);
		double rentDue = rentMonths * rentPerMonth;
		totalRent = Double.parseDouble(dpFormatter.format(rentDue));
	}

}
