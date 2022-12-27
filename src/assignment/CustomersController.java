package assignment;

import java.io.IOException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;


public class CustomersController extends DashboardController implements DateFormatter {

	@FXML
	private TextField name;
	@FXML
	private TextField email;
	@FXML
	private TextField phone;
	@FXML
	private DatePicker dob;
	@FXML
	private Button addNewCustomer;
	
	@FXML
	private Label emptyListLabel;
	@FXML
	private GridPane allCustomers;
	@FXML
	private Pane emptyCustomerList;
	@FXML
	private AnchorPane customersWrapper;
	
	
	private CustomerList cList;
	
	
	public void initialize() throws ClassNotFoundException, IOException {
		cList = DataHandler.readCustomerList();
		Customer.setLastIndex(cList.getCustomers().size());	
		populateList();
	}
	
	private void populateList() {
		
		if(Customer.getLastIndex() < 1) {
			emptyListLabel.setVisible(true);
			allCustomers.setVisible(false);
		} else {
			allCustomers.setVisible(true);
			emptyListLabel.setVisible(false);
			
			for(int i = 0; i < cList.getCustomers().size(); i++) {
				Text name = new Text();
				name.setText(cList.getCustomers().get(i).getName());
				Text email = new Text();
				Text phone = new Text();
				Text dob = new Text();
				
				email.setText(cList.getCustomers().get(i).getEmail());
				phone.setText(cList.getCustomers().get(i).getPhone());
				dob.setText(cList.getCustomers().get(i).getDOB().format(dateFormatter));
				
				
				allCustomers.getRowConstraints().add(new RowConstraints(40));
				allCustomers.add(name, 0, i+1);
				allCustomers.add(email, 1, i+1);
				allCustomers.add(phone, 2, i+1);
				allCustomers.add(dob, 3, i+1);
				
				//add padding to each cell
				GridPane.setMargin(name, new Insets(5));
				GridPane.setMargin(email, new Insets(5));
				GridPane.setMargin(phone, new Insets(5));
				GridPane.setMargin(dob, new Insets(5));
			}
		}
	}
	
	public void AddNewCustomerBtnListener(ActionEvent e) throws IOException {
		Alert alert = new Alert(AlertType.NONE);
		
		if(name.getText() == "" || email.getText() == "" || phone.getText() == "" || dob == null) {
			System.out.println("No text");
			alert.setAlertType(AlertType.ERROR);
            alert.setTitle("Error adding Customer");
            alert.setContentText("Please fill in all details correctly");
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
	
}
