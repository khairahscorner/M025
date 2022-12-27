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


public class RentalsController extends DashboardController implements DateFormatter {

	@FXML
	private ComboBox<String> availableProperties;
	@FXML
	private ComboBox<String> availableCustomers;
	@FXML
	private DatePicker rentDueDate;
	@FXML
	private Button addNewRental;
	
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
	
	
	
	public void initialize() throws ClassNotFoundException, IOException {
		rList = DataHandler.readRentalList();
		Rental.setLastRentalIndex(rList.getRentals().size());

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
		
		if(Rental.getLastRentalIndex() == 0) {
			emptyRentalsList.setVisible(true);
			allRentals.setVisible(false);
		} else {
			allRentals.setVisible(true);
			emptyRentalsList.setVisible(false);
			populateList();
		}
		
	}
	
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
	
	
	public void selectPropertyToRentListener() {
		String selectedPptyId = availableProperties.valueProperty().getValue();
		System.out.println(selectedPptyId);
		selectedProperty = filteredProperties.get(selectedPptyId);
	    System.out.println(selectedProperty);
	}
	
	public void selectCustomerListener() {
		String selectedCustId = availableCustomers.valueProperty().getValue();
		System.out.println(selectedCustId);
		selectedCustomer = hashedCustomers.get(selectedCustId);
	    System.out.println(selectedCustomer);
	}
	
	
	public void RentPropertyListener(ActionEvent e) throws IOException {
		
		Alert alert = new Alert(AlertType.NONE);
		
		if(selectedProperty == null || selectedCustomer == null ||  rentDueDate.getValue() == null) {
			alert.setAlertType(AlertType.ERROR);
            alert.setTitle("Error renting property");
            alert.setContentText("Please fill in all details correctly");
            alert.show();
		} else if (!rentDueDate.getValue().isAfter(LocalDate.now())) {
			alert.setAlertType(AlertType.ERROR);
            alert.setTitle("Error renting property");
            alert.setContentText("Please select a date after " + LocalDate.now().format(dateFormatter));
            alert.show();
		}
		else {
			try {
				ImportData da = new ImportData();
				da.createRental(selectedProperty, selectedCustomer, LocalDate.now(), rentDueDate.getValue());
				
				//ensure the property object in the rental(which references the ppty object in the list) has the rental status set to true
				selectedProperty.setRentalStatus(true);
				
				
				DataHandler.writeToFile(da.getAllRentals());
//				
//				//set original property to true??
//				//pList.getProperties().get(selectedProperty.getPropertyId()).setRentalStatus(true);
//								
				DataHandler.writeToFile(pList);
				
				alert.setAlertType(AlertType.INFORMATION);
	            alert.setTitle("Successful");
	            alert.setContentText("Property has been rented successfully");

	            //show alert, wait for user to close and then refresh
	            Optional<ButtonType> result = alert.showAndWait();
	            
	            if(result.get() == ButtonType.OK) {
	            	goToRentalsListener(e);
	            } else {
	            	//still refresh
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
	
	private void showRentalPropertyDetails(Property p, Customer c, String key) {
		System.out.println("inside: " + key);
		Rental r = rList.getRentals().get(key);
		RentalInvoice newInvoice = new RentalInvoice(r);
		
		
    	rentalInvoice.setText(newInvoice.generateInvoice() + "\n");
    	
    	rentalPptyDetails.setText("----- Property Details ------\n");
    	rentalPptyDetails.appendText("Type: " + p.getFurnishedStatus() + " " + p.getType() + "\n" 
    	+ Property.getPropertyDetails(p) + "\n");
    	
    	rentalCustomerDetails.setText("----- Customer Details ------\n");
    	rentalCustomerDetails.appendText(c.toString());
    	
	}
}
