package assignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * This class is used to generate an invoice for a rental property at the end of
 * the tenancy
 * 
 * @author Airat Yusuff 22831467
 *
 */
public class TenancyEndInvoice implements Invoice, DataFormatter {
	private double deductions;
	private Rental rentalDetails;
	private String invoiceId;

	/**
	 * constructor to save the referenced rental object and calculate values for the
	 * attributes
	 * 
	 * @param r Rental object
	 * @param d damage deductions
	 */
	public TenancyEndInvoice(Rental r, double d) {
		rentalDetails = r;
		deductions = d;
		invoiceId = "EOT-" + r.getRentalId();
	}

	public double getDeductions() {
		return deductions;
	}

	public void setDeductions(double d) {
		deductions = d;
	}

	public String getInvoiceId() {
		return invoiceId;
	}

	/**
	 * interface method
	 */
	public String generateInvoice() {
		Property rentalPpty = rentalDetails.getRentalPpty();
		String str = "";

		str += "Invoice Id: " + invoiceId + "\n";

		str += "Rental Id: " + rentalDetails.getRentalId() + "\n";

		str += "Rent Date: " + rentalDetails.getRentDate().format(dateFormatter) + "\n";
		str += "Due Date: " + rentalDetails.getDueDate().format(dateFormatter) + "\n";

		str += "Deposit: £" + rentalPpty.getDeposit() + "\n";
		str += "Damage Deductions: £" + deductions + "\n";
		if (deductions > rentalPpty.getDeposit()) {
			str += "Outstanding Amount Owed by Customer: £" + getRemainingAmount() + "\n";
		} else {
			str += "Deposit Amount to Return: £" + getRemainingAmount() + "\n";
		}

		return str;
	}
	
	public double getRemainingAmount() {
		return Math.abs(deductions - rentalDetails.getRentalPpty().getDeposit());
	}

	/**
	 * interface method
	 */
	public void generateInvoiceAsPDF(String filename) {
		// <-***** CSYM025 Code FROM NILE - START
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream(new File(filename)));

			// open
			document.open();
			Font f = new Font();
			f.setStyle(Font.BOLD);
			f.setSize(20);

			Paragraph p = new Paragraph("End of Tenancy Invoice", f);
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
		// ->***** CSYM025 Code FROM NILE - END
	}

}
