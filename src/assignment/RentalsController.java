package assignment;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

/**
 * The class handles all actions in the rentalsView scenes - rent property, view rentals
 * @author Airat Yusuff 22831467
 *
 */
public class RentalsController extends DashboardController implements DataFormatter {

	@FXML
	private ComboBox<String> availableProperties;
	@FXML
	private ComboBox<String> availableCustomers;
	@FXML
	private DatePicker rentDueDate;
	
	@FXML
	private Property selectedProperty;
	@FXML
	private Customer selectedCustomer;
	
	@FXML
	private Label emptyListLabel;
	@FXML
	private GridPane allRentals;
	@FXML
	private Pane emptyRentalsList;
	@FXML
	private ScrollPane rentalsWrapper; 
	@FXML
	private TextArea rentalInvoice;
	@FXML
	private TextArea rentalPptyDetails;
	@FXML
	private TextArea rentalCustomerDetails;
	
	
	private RentalList rList;
	private PropertyList pList;
	private CustomerList cList;
	private Rental currRental;
	
	private HashMap<String, Property> filteredProperties;
	private HashMap<String, Customer> hashedCustomers;	
	
	
	/**
	 * read specified existing files into lists + adds options to comboBoxes
	 */
	public void initialize() {
		try {
			rList = DataHandler.readRentalList();
					
			pList = DataHandler.readPropertyList();
			filteredProperties = new HashMap<String, Property>();
			
			cList = DataHandler.readCustomerList();
			hashedCustomers = new HashMap<String, Customer>();
	
			//get all properties that are not rented i.e rentalStatus is false
			for (String key : pList.getKeys()) {
			    if(!pList.getProperties().get(key).getRentalStatus()) {
			    	Property availablePpty = pList.getProperties().get(key);		    	
			    	filteredProperties.put(key, availablePpty);
			    	availableProperties.getItems().add(key);
			    }
			}
			for (Customer cust : cList.getCustomers()) {
				hashedCustomers.put(cust.getCustId(), cust);
			    availableCustomers.getItems().add(cust.getCustId());
			}
			
			if(rList.getKeys().size() == 0) {
				emptyRentalsList.setVisible(true);
				allRentals.setVisible(false);
				rentalsWrapper.setVisible(false);
			} else {
				allRentals.setVisible(true);
				rentalsWrapper.setVisible(true);
				emptyListLabel.setVisible(false);
				populateList();
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
	
	/**
	 * populates the specified grid pane with the rental details read from the file
	 * called by initialize() method
	 */
	private void populateList() {
		for(int i = 0; i < rList.getRentals().size(); i++) {
			String key = rList.getKeys().get(i);
			currRental = rList.getRentals().get(key);
			
			Property currPpty = currRental.getRentalPpty();
			Customer currCust = currRental.getRentalCustomer();
			
			Text rentalCode = new Text();
			Text pptyCode = new Text();
			rentalCode.setText(currRental.getRentalId());
			pptyCode.setText(currPpty.getPropertyId());
			
			Text custName = new Text();
			custName.setText(currCust.getName());				
			Button viewBtn = new Button("View Invoice");
			viewBtn.setId(key);

			viewBtn.setOnAction(new EventHandler<ActionEvent>() {
			    @Override
			    public void handle(ActionEvent event) {
			    	System.out.println(viewBtn.getId());
			    	showRentalPropertyDetails(currPpty, currCust, viewBtn.getId());			    	
			    }
			});			
			
			
			allRentals.getRowConstraints().add(new RowConstraints(40));
			allRentals.add(rentalCode, 0, i+1);
			allRentals.add(pptyCode, 1, i+1);
			allRentals.add(custName, 2, i+1);
			allRentals.add(viewBtn, 3, i+1);
			
			
			//add padding to each cell
			GridPane.setMargin(rentalCode, new Insets(5));
			GridPane.setMargin(pptyCode, new Insets(5));
			GridPane.setMargin(custName, new Insets(5));
			GridPane.setMargin(viewBtn, new Insets(5));
		}
	}
	
	/**
	 * select property to rent
	 */
	public void selectPropertyToRentListener() {
		String selectedPptyId = availableProperties.getValue();
		System.out.println(selectedPptyId);
		selectedProperty = filteredProperties.get(selectedPptyId);
	    System.out.println(selectedProperty);
	}
	
	/**
	 * select customer to rent to
	 */
	public void selectCustomerListener() {
		String selectedCustId = availableCustomers.getValue();
		System.out.println(selectedCustId);
		selectedCustomer = hashedCustomers.get(selectedCustId);
	    System.out.println(selectedCustomer);
	}
	
	
	/**
	 * validate user input, create a rental property object, append to existing list and rewrite the list to the corresponding file
	 * also, change the availability status of the property and 
	 * @param e
	 * @throws IOException
	 */
	public void rentPropertyListener(ActionEvent e) throws IOException {
		
		Alert alert = new Alert(AlertType.NONE);
		
		if(selectedProperty == null || selectedCustomer == null ||  rentDueDate.getValue() == null) {
			alert.setAlertType(AlertType.ERROR);
            alert.setTitle("Error renting property");
            alert.setContentText("Please fill in all details correctly");
            alert.show();
		}
//		else if (!rentDueDate.getValue().isAfter(LocalDate.now())) {
//			alert.setAlertType(AlertType.ERROR);
//            alert.setTitle("Error renting property");
//            alert.setContentText("Please select a date after " + LocalDate.now().format(dateFormatter));
//            alert.show();
//		}
		else if (ChronoUnit.MONTHS.between(LocalDate.now(), rentDueDate.getValue()) < 1) {
			alert.setAlertType(AlertType.ERROR);
            alert.setTitle("Error renting property");
            alert.setContentText("Please select a date at least 1 month after " + LocalDate.now().format(dateFormatter));
            alert.show();
		}
		else {
			try {
				ImportData da = new ImportData();
				
				da.createRental(selectedProperty, selectedCustomer, LocalDate.now(), rentDueDate.getValue());
				
				selectedProperty.setRentalStatus(true);
				
				DataHandler.writeToFile(da.getAllRentals());
				DataHandler.writeToFile(pList);
							
				
				alert.setAlertType(AlertType.INFORMATION);
	            alert.setTitle("Successful");
	            alert.setContentText("Property has been rented successfully");

	            //show alert, wait for user to close and then refresh
	            Optional<ButtonType> result = alert.showAndWait();
	            
	            if(result.get() != null) {
	            	goToRentalsListener(e);
	            }        	            
        
			} catch(Exception exception) {
				alert.setAlertType(AlertType.ERROR);
                alert.setTitle("Error renting property");
                alert.setContentText("An error occured");
                alert.show();
			}
		}
}

	/**
	 * display details of the rental property including the invoice, property and customer details
	 * @param p	Property object
	 * @param c	Customer object
	 * @param key	key of the current Rental object
	 */
	private void showRentalPropertyDetails(Property p, Customer c, String key) {
		Rental r = rList.getRentals().get(key);
		RentalInvoice newInvoice = new RentalInvoice(r);
			
    	rentalInvoice.setText(newInvoice.generateInvoice() + "\n");
    	
    	rentalPptyDetails.setText("-- Property Details at Rental --\n");
    	rentalPptyDetails.appendText(Property.getPropertyDetails(p) + "\n");
    	
    	rentalCustomerDetails.setText("--- Customer Details at Rental ---\n");
    	rentalCustomerDetails.appendText(c.toString());
    	
	}
}

