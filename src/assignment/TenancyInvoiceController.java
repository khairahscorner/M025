package assignment;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;

/**
 * class handles all actions in the invoiceView scenes - select rental property
 * to release
 * 
 * @author Airat Yusuff 22831467
 *
 */
public class TenancyInvoiceController extends DashboardController implements DataFormatter {

	@FXML
	private ComboBox<String> rentalProperties;
	@FXML
	private TextField deductions;
	@FXML
	private Button generateInvoice;
	@FXML
	private Label loadingStatus;

	@FXML
	private TextArea displayInvoice;

	private RentalList rList;
	private PropertyList pList;
	private String selectedRentalId;
	
	private final String DOWNLOAD_FILENAME = "tenancyEndInvoice.pdf";

	/**
	 * read specified existing files into lists + adds options to comboBoxes
	 */
	public void initialize() {
		try {
			rList = FileDataHandler.readRentalList();
			pList = FileDataHandler.readPropertyList();

			for (String key : rList.getKeys()) {
				rentalProperties.getItems().add(key);
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("An error has occured in the app");
			alert.show();
		}
	}

	/**
	 * select rental property to release
	 */
	public void selectRentalPptyListener() {
		selectedRentalId = rentalProperties.getValue();
	}

	/**
	 * validates inputs, sets rental status of the property in the properties list
	 * back to true, remove the rental property objects, and writes the updates to
	 * corresponding files. Also, it shows generated invoice
	 * 
	 * @param e
	 * @throws IOException
	 */
	public void generateInvoiceListener(ActionEvent e) throws IOException {
		double convertedDeduction = 0;
		Alert alert = new Alert(AlertType.NONE);
		Rental currRental = rList.getRentals().get(selectedRentalId);

		if (selectedRentalId == null) {
			alert.setAlertType(AlertType.ERROR);
			alert.setTitle("Error generating invoice");
			alert.setContentText("Please select a rental property");
			alert.show();
			return;
		}

		try {
			convertedDeduction = Double.parseDouble(deductions.getText());
		} catch (Exception error) {
			alert.setAlertType(AlertType.ERROR);
			alert.setTitle("Error generating invoice");
			alert.setContentText("Please enter a valid value to deduct");
			alert.show();
			return;
		}

		if (convertedDeduction < 0) {
			alert.setAlertType(AlertType.ERROR);
			alert.setTitle("Error generating invoice");
			alert.setContentText("Please enter a positive value to deduct");
			alert.show();
		} else if (LocalDate.now().isBefore(currRental.getDueDate())) {
			alert.setAlertType(AlertType.ERROR);
			alert.setTitle("Error generating invoice");
			alert.setContentText("Invoice can only be generated after tenancy has ended");
			alert.show();
		} else {
			try {
				Property currPpty = currRental.getRentalPpty();
				// ensure the property object in the property list has the rental status set back to false
				pList.getProperties().get(currPpty.getPropertyId()).setRentalStatus(false);

				rList.removeRental(selectedRentalId);

				FileDataHandler.writeToFile(pList);
				FileDataHandler.writeToFile(rList);
				
				TenancyEndInvoice eotInvoice = new TenancyEndInvoice(currRental, convertedDeduction);

				// generate invoice as pdf
				eotInvoice.generateInvoiceAsPDF(DOWNLOAD_FILENAME);

				// send email with invoice as pdf
				new SendEmail().sendInvoiceAsPDF(currRental.getRentalCustomer().getEmail(), "eot", DOWNLOAD_FILENAME);

				alert.setAlertType(AlertType.INFORMATION);
				alert.setTitle("Successful");
				alert.setContentText("Property has been released successfully and the invoice has been sent to " + currRental.getRentalCustomer().getEmail()
						+ ".");

				// show alert, wait for user to close and then refresh
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() != null) {
					showFinalInvoice(currRental, convertedDeduction);
					resetForm();
				}

			} catch (Exception exception) {
				alert.setAlertType(AlertType.ERROR);
				alert.setTitle("Error renting property");
				alert.setContentText("An error occured");
				alert.show();
			}
		}
	}

	/**
	 * generate the invoice, display invoice and the property and customer details
	 * 
	 * @param r         Rental object
	 * @param deduction amount deduction
	 */
	private void showFinalInvoice(Rental r, double deduction) {
		TenancyEndInvoice eotInvoice = new TenancyEndInvoice(r, deduction);

		displayInvoice.setText(" ----- Generated Invoice ------ \n");
		displayInvoice.appendText(eotInvoice.generateInvoice() + "\n");

		displayInvoice.appendText("----- Property Details ------\n");
		displayInvoice.appendText(r.getRentalPpty().getPropertyDetails() + "\n");

		displayInvoice.appendText("\n----- Customer Details ------\n" + r.getRentalCustomer().toString());
	}

	/**
	 * clear selection option and fields in the form called by
	 * selectRentalPptyListener() method
	 */
	private void resetForm() {
		selectedRentalId = null;
		rentalProperties.getItems().clear();
		deductions.clear();
		try {
			initialize();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
