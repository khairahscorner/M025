package assignment;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;

public class PropertyCreateController  extends DashboardController implements DataFormatter {
	
	@FXML
	private Button submitBtn;
	@FXML
	private Button cancelBtn;
	
	@FXML
	private TextField type;
	@FXML
	private ComboBox<String> furnishing;
	@FXML
	private TextField postcode;
	@FXML
	private TextField size;
	@FXML
	private TextField rent;
	@FXML
	private ComboBox<String> hasGarden;
	@FXML
	private TextField bedrooms;
	@FXML
	private TextField bathrooms;
	@FXML
	private TextField longitude;
	@FXML
	private TextField latitude;
	
	
	private PropertyList pList;
	
	public void initialize() throws ClassNotFoundException, IOException {
		pList = DataHandler.readPropertyList();
		Property.setLastPropertyIndex(pList.getProperties().size());
    
		furnishing.getItems().addAll("Unfurnished", "Semi-furnished", "Furnished");
		hasGarden.getItems().addAll("Yes", "No");
	}
	
	public void addNewPropertyListener(ActionEvent e) throws IOException {
		Alert alert = new Alert(AlertType.NONE);
		double rentVal = 0;
		int noOfBeds = 0;
		int noOfBaths = 0;
		int pptySize = 0;
		
		//strings so i can first format the location values to 5 d.p.
		String l1 = "";
		String l2 = "";
		
		try {
			rentVal = Double.parseDouble(rent.getText());
			pptySize = Integer.parseInt(size.getText());
			noOfBeds = Integer.parseInt(bedrooms.getText());
			noOfBaths = Integer.parseInt(bathrooms.getText());
		}
		catch(Exception ex) {
			alert.setAlertType(AlertType.ERROR);
            alert.setTitle("Error creating new property");
            alert.setContentText("Please enter valid values for rent, size, bedrooms and bathrooms");
            alert.show();
            return;
		}
		try {
			l1 = locationFormatter.format(Double.parseDouble(longitude.getText()));
			l2 = locationFormatter.format(Double.parseDouble(latitude.getText()));
		}
		catch(Exception ex) {
			alert.setAlertType(AlertType.ERROR);
            alert.setTitle("Error creating new property");
            alert.setContentText("Please enter correct longitide and latitiude values");
            alert.show();
            return;
		}

		if(type.getText() == "" || postcode.getText() == "" || furnishing.getValue() == null || hasGarden.getValue() == null) {
			alert.setAlertType(AlertType.ERROR);
            alert.setTitle("Error creating new property");
            alert.setContentText("Please fill in all details correctly");
            alert.show();
		} else if(rentVal < 0 || pptySize < 0 || noOfBeds < 1 || noOfBaths < 1) {
			alert.setAlertType(AlertType.ERROR);
            alert.setTitle("Error creating new property");
            alert.setContentText("Please enter values greater than 0 for rent, size, bedrooms and bathrooms");
            alert.show();
		}
		else {
			try {
				
				ImportData da = new ImportData();
				
				da.createProperty(type.getText(), furnishing.getValue(), postcode.getText(), LocalDate.now().format(dateFormatter), 
						hasGarden.getValue(), pptySize, noOfBeds, noOfBaths, rentVal, Double.parseDouble(l1), Double.parseDouble(l2));
						
				DataHandler.writeToFile(da.getAllProperties());
				
				//DataHandler.readCustomerList();
				alert.setAlertType(AlertType.INFORMATION);
	            alert.setTitle("Successful");
	            alert.setContentText("New Property has been added successfully");

	            //show alert, wait for user to close and then refresh
	            Optional<ButtonType> result = alert.showAndWait();
	            
	            if(result.get() == ButtonType.OK) {
	            	goToPropertiesListener(e);
	            } else {
	            	//still refresh
	            	goToPropertiesListener(e);
	            }
	            
	            
			} catch(Exception exception) {
				alert.setAlertType(AlertType.ERROR);
                alert.setTitle("Error adding new Property");
                alert.setContentText("An error occured");
                alert.show();
			}
		}
	}
	
	
}
