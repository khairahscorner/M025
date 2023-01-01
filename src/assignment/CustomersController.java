package assignment;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;


public class CustomersController extends DashboardController implements DataFormatter {
	@FXML
	private AnchorPane customerForm;
	@FXML
	private Label formHeading;

	@FXML
	private TextField name;
	@FXML
	private TextField email;
	@FXML
	private TextField phone;
	@FXML
	private DatePicker dob;
	@FXML
	private Button addNew;
	
	@FXML
	private Label emptyListLabel;
	@FXML
	private GridPane allCustomers;
	@FXML
	private Pane emptyCustomerList;
	
	@FXML
	private Button cancelEdit;
	@FXML
	private Button updateDetails;
 
	
	private CustomerList cList;
	private int gridCount;
	private int currCustIndex;
	
	private String defaultFormHeading = "Add New Customer";
	private String emailValidate = "^[a-zA-Z0-9_\\-\\.]{2,}+@[A-Za-z0-9\\-]+\\.[a-zA-Z]{2,10}$";
	private String phoneValidate = "^\\+[0-9]{8,15}$";

	
	
	public void initialize() throws ClassNotFoundException, IOException {
		cList = DataHandler.readCustomerList();
		Customer.setLastIndex(cList.getCustomers().size());	
		populateList();
		
		cancelEdit.setVisible(false);
    	updateDetails.setVisible(false);
	}
	
	private void populateList() {
		gridCount = 1;
		if(Customer.getLastIndex() < 1) {
			emptyCustomerList.setVisible(true);
			emptyListLabel.setVisible(true);
			allCustomers.setVisible(false);
		} else {
			allCustomers.setVisible(true);
			emptyCustomerList.setVisible(false);
			emptyListLabel.setVisible(false);
			
			cList.getCustomers().forEach(cust -> {
				Text name = new Text();
				name.setText(cust.getName());
				Text email = new Text();
				Text phone = new Text();
				Text dob = new Text();
				
				email.setText(cust.getEmail());
				phone.setText(cust.getPhone());
				dob.setText(cust.getDOB().format(dateFormatter));
				Button editBtn = new Button("Edit");
				editBtn.setId(cust.getCustId());
				editBtn.setOnAction(new EventHandler<ActionEvent>() {
				    @Override
				    public void handle(ActionEvent event) {
						currCustIndex = Integer.parseInt(editBtn.getId().split("c")[1]);		
				    	editCustomerDetails(currCustIndex);			    	
				    }
				});	
				
				
				allCustomers.getRowConstraints().add(new RowConstraints(40));
				allCustomers.add(name, 0, gridCount);
				allCustomers.add(email, 1, gridCount);
				allCustomers.add(phone, 2, gridCount);
				allCustomers.add(dob, 3, gridCount);
				allCustomers.add(editBtn, 4, gridCount);
				
				//add padding to each cell
				GridPane.setMargin(name, new Insets(5));
				GridPane.setMargin(email, new Insets(5));
				GridPane.setMargin(phone, new Insets(5));
				GridPane.setMargin(dob, new Insets(5));
				GridPane.setMargin(editBtn, new Insets(5));
				gridCount++;
			});
			
		}
	}
	
	public void addNewCustomerBtnListener(ActionEvent e) throws IOException {
		Alert alert = new Alert(AlertType.NONE);
		
		if(name.getText() == "" || email.getText() == "" || phone.getText() == "" || dob.getValue() == null) {
			alert.setAlertType(AlertType.ERROR);
            alert.setTitle("Error adding Customer");
            alert.setContentText("Please fill in all details correctly");
            alert.show();
		} 
		else if (!email.getText().matches(emailValidate)) {
			alert.setAlertType(AlertType.ERROR);
            alert.setTitle("Error adding Customer");
            alert.setContentText("Please enter a valid email address");
            alert.show();
		}
		else if (!phone.getText().matches(phoneValidate)) {
			alert.setAlertType(AlertType.ERROR);
            alert.setTitle("Error adding Customer");
            alert.setContentText("Please enter a correct phone number:\\n- country code starting with + and,\\nBetween 8 to 14 digits");
            alert.show();
		}
		else if (ChronoUnit.YEARS.between(dob.getValue(), LocalDate.now()) < 18) {
			alert.setAlertType(AlertType.ERROR);
            alert.setTitle("Error adding Customer");
            alert.setContentText("Customer must be at least 18 years old");
            alert.show();
		}
		else {
			try {
				ImportData da = new ImportData();
				
				da.createCustomer(name.getText(), email.getText(), phone.getText(), dob.getValue().format(dateFormatter));
				DataHandler.writeToFile(da.getAllCustomers());
				
				//DataHandler.readCustomerList();
				alert.setAlertType(AlertType.INFORMATION);
	            alert.setTitle("Successful");
	            alert.setContentText("New Customer has been added successfully");

	            //show alert, wait for user to close and then refresh
	            Optional<ButtonType> result = alert.showAndWait();
	            
	            if(result.get() == ButtonType.OK) {
	            	goToCustomersListener(e);
	            } else {
	            	//still refresh
	            	goToCustomersListener(e);
	            }
	            	            

	            
			} catch(Exception exception) {
				alert.setAlertType(AlertType.ERROR);
                alert.setTitle("Error adding Customer");
                alert.setContentText("An error occured");
                alert.show();
			}
		}
    }
	
	private void editCustomerDetails(int custIndex) {
		Customer currCustomer = cList.getCustomers().get(custIndex);
		
		formHeading.setText("Edit " + currCustomer.getName());
		name.setText(currCustomer.getName());
    	email.setText(currCustomer.getEmail());
    	phone.setText(currCustomer.getPhone());
    	dob.setValue(currCustomer.getDOB());
    	
    	addNew.setVisible(false);
    	cancelEdit.setVisible(true);
    	updateDetails.setVisible(true);
	}
	
	
	public void cancelEditListener(ActionEvent e) throws IOException {
		addNew.setVisible(true);
    	cancelEdit.setVisible(false);
    	updateDetails.setVisible(false);
    	
    	formHeading.setText(defaultFormHeading);
    	name.setText(null);
    	email.setText(null);
    	phone.setText(null);
    	dob.setValue(null);
	}
	
	public void updateCustomerBtnListener(ActionEvent e) throws IOException {
		Alert alert = new Alert(AlertType.NONE);
		Customer currCustomer = cList.getCustomers().get(currCustIndex);


		if(name.getText() == "" || email.getText() == "" || phone.getText() == "" || dob == null) {
			alert.setAlertType(AlertType.ERROR);
            alert.setTitle("Error updating customer details");
            alert.setContentText("Please fill in all details correctly");
            alert.show();
		} else if (!email.getText().matches(emailValidate)) {
			alert.setAlertType(AlertType.ERROR);
            alert.setTitle("Error updating customer details");
            alert.setContentText("Please enter a valid email address");
            alert.show();
		} else if (!phone.getText().matches(phoneValidate)) {
			alert.setAlertType(AlertType.ERROR);
            alert.setTitle("Error updating customer details");
            alert.setContentText("Please enter a correct phone number:\n- country code starting with + and,\nBetween 8 to 14 digits");
            alert.show();
		} else if (ChronoUnit.YEARS.between(dob.getValue(), LocalDate.now()) < 18) {
			alert.setAlertType(AlertType.ERROR);
            alert.setTitle("Error updating customer details");
            alert.setContentText("Customer must be at least 18 years old");
            alert.show();
		}
		else {
			try {
				currCustomer.setName(name.getText());
				currCustomer.setEmail(email.getText());
				currCustomer.setPhone(phone.getText());
				currCustomer.setDOB(dob.getValue().format(dateFormatter));
				
				DataHandler.writeToFile(cList);
				
				alert.setAlertType(AlertType.INFORMATION);
	            alert.setTitle("Successful");
	            alert.setContentText("Customer details have been updated successfully");

	            //show alert, wait for user to close and then refresh
	            Optional<ButtonType> result = alert.showAndWait();
	            
	            if(result.get() == ButtonType.OK) {
	            	goToCustomersListener(e);
	            } else {
	            	//still refresh
	            	goToCustomersListener(e);
	            }
	            	            

	            
			} catch(Exception exception) {
				alert.setAlertType(AlertType.ERROR);
                alert.setTitle("Error adding Customer");
                alert.setContentText("An error occured");
                alert.show();
			}
		}
	}
}
