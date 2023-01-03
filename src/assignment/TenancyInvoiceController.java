package assignment;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;


public class TenancyInvoiceController extends DashboardController implements DataFormatter {

	@FXML
	private ComboBox<String> rentalProperties;
	@FXML
	private TextField deductions;
	@FXML
	private Button generateInvoice;
		
	
	@FXML
	private TextArea displayInvoice;
	
	
	private RentalList rList;
	private String selectedRentalId;

	private PropertyList pList;

	

	
	public void initialize() {
		try {
			rList = DataHandler.readRentalList();		
		
			pList = DataHandler.readPropertyList();
	
			for (String key: rList.getKeys()) {
				rentalProperties.getItems().add(key);
			}
		}
		catch(Exception e) {
    		Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("An error has occured in the app");
            alert.show();
            System.out.println(e.toString());
    	}
	}
	

	public void selectRentalPptyListener() {
		selectedRentalId = rentalProperties.getValue();
		System.out.println(selectedRentalId);
	}
	
	
	public void generateInvoiceListener(ActionEvent e) throws IOException {
		double convertedDeduction = 0;
		Alert alert = new Alert(AlertType.NONE);
		Rental currRental = rList.getRentals().get(selectedRentalId);				
		
		if(selectedRentalId == null) {
			alert.setAlertType(AlertType.ERROR);
            alert.setTitle("Error generating invoice");
            alert.setContentText("Please select a rental property");
            alert.show();
            return;
		}
		
		try {
			convertedDeduction = Double.parseDouble(deductions.getText());
		} catch(Exception error) {
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
		} else if(LocalDate.now().isBefore(currRental.getDueDate())) {
			alert.setAlertType(AlertType.ERROR);
            alert.setTitle("Error generating invoice");
            alert.setContentText("Invoice can only be generated after tenancy has ended");
            alert.show();
		}
		else {
			try {
				Property currPpty = currRental.getRentalPpty();
				//ensure the property object in the property list itself has the rental status set back to false
				pList.getProperties().get(currPpty.getPropertyId()).setRentalStatus(false);			
				
				rList.removeRental(selectedRentalId);

				DataHandler.writeToFile(pList);
				DataHandler.writeToFile(rList);				
				
				alert.setAlertType(AlertType.INFORMATION);
	            alert.setTitle("Successful");
	            alert.setContentText("Property has been released successfully");

	            //show alert, wait for user to close and then refresh
	            Optional<ButtonType> result = alert.showAndWait();
	            
	            if(result.get() == ButtonType.OK) {
	            	showFinalInvoice(currRental, convertedDeduction);
	            	resetForm();
	            } else {
	            	showFinalInvoice(currRental, convertedDeduction);
	            	resetForm();
	            }
	            
	            
			} catch(Exception exception) {
				alert.setAlertType(AlertType.ERROR);
                alert.setTitle("Error renting property");
                alert.setContentText("An error occured");
                alert.show();
			}
		}
}
	
	private void showFinalInvoice(Rental r, double deduction) {
		TenancyEndInvoice eotInvoice = new TenancyEndInvoice(r, deduction);
		
		displayInvoice.setText(eotInvoice.generateInvoice() + "\n");
		
		displayInvoice.appendText("----- Property Details ------\n");
		displayInvoice.appendText(Property.getPropertyDetails(r.getRentalPpty()) + "\n");
    	
		displayInvoice.appendText("\n----- Customer Details ------\n" + r.getRentalCustomer().toString());	
	}
	
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
