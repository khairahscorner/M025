package assignment;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;


public class TenancyInvoiceController extends DashboardController implements DateFormatter {

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
//	private CustomerList cList;
//	
//	private HashMap<String, Property> filteredProperties;
//	private HashMap<String, Customer> hashedCustomers;
//	private HashMap<String, Rental> hashedRentals;
//	
//	
//	
//	
	public void initialize() throws ClassNotFoundException, IOException {
		rList = DataHandler.readRentalList();
		Rental.setLastRentalIndex(rList.getRentals().size());
		
		pList = DataHandler.readPropertyList();

		for (String key: rList.getKeys()) {
			rentalProperties.getItems().add(key);
		}



//		System.out.println(pList.getProperties().size());
//		System.out.println(filteredProperties.size());
	}
	

	public void selectRentalPptyListener() {
		selectedRentalId = rentalProperties.valueProperty().getValue();
		System.out.println(selectedRentalId);
	}
	
	
	public void generateInvoiceListener(ActionEvent e) throws IOException {
		double convertedDeduction = 0;
		Alert alert = new Alert(AlertType.NONE);
		Rental currRental = rList.getRentals().get(selectedRentalId);
				
		
		try {
			convertedDeduction = Double.parseDouble(deductions.getText());
		} catch(Exception error) {
			alert.setAlertType(AlertType.ERROR);
            alert.setTitle("Error generating invoice");
            alert.setContentText("Please enter a correct deduction value");
            alert.show();
            return;
		}
		
		if(selectedRentalId == null) {
			alert.setAlertType(AlertType.ERROR);
            alert.setTitle("Error generating invoice");
            alert.setContentText("Please fill in all details correctly");
            alert.show();
		} else if (convertedDeduction < 0) {
			alert.setAlertType(AlertType.ERROR);
            alert.setTitle("Error generating invoice");
            alert.setContentText("Please enter a positive deduction value");
            alert.show();
		}
		else if(LocalDate.now().isBefore(currRental.getDueDate())) {
			alert.setAlertType(AlertType.ERROR);
            alert.setTitle("Error generating invoice");
            alert.setContentText("Invoice can only be generated after tenancy has ended");
            alert.show();
		}
		else {
			try {
				//ensure the property object in the property list itself has the rental status set back to false
				Property currPpty = currRental.getRentalPpty();
				pList.getProperties().get(currPpty.getPropertyId()).setRentalStatus(false);
				DataHandler.writeToFile(pList);
			
				
				rList.removeRental(selectedRentalId);
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
