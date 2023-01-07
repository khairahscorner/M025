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

/**
 * This class controls the property creation scene for manually creating a new
 * property in the application
 * 
 * @author Airat YUsuff 22831467
 */
public class PropertyCreateController extends DashboardController implements DataFormatter {

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

	private final String POSTCODE_VALIDATE = "^[A-Z0-9]{2,4}+ [A-Z0-9]{3}$";

	/**
	 * reads the existing properties to a list + sets values for combo boxes
	 */
	public void initialize() {
		try {
			pList = DataHandler.readPropertyList();
			Property.setLastPropertyIndex(pList.getProperties().size());

			furnishing.getItems().addAll("Unfurnished", "Semi-Furnished", "Furnished");
			hasGarden.getItems().addAll("Yes", "No");
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("An error has occured in the app");
			alert.show();
			System.out.println(e.toString());
		}

	}

	/**
	 * validates user input, creates & appends new property to the list and writes
	 * the updated list to file, overwriting the previously stored list
	 * 
	 * @param e
	 * @throws IOException
	 */
	public void addNewPropertyListener(ActionEvent e) throws IOException {
		Alert alert = new Alert(AlertType.NONE);
		double rentVal = 0;
		int noOfBeds = 0;
		int noOfBaths = 0;
		double pptySize = 0;
		double longi = 0;
		double lat = 0;

		try {
			rentVal = Double.parseDouble(rent.getText());
			pptySize = Double.parseDouble(size.getText());
			lat = Double.parseDouble(latitude.getText());
			longi = Double.parseDouble(longitude.getText());
			noOfBeds = Integer.parseInt(bedrooms.getText());
			noOfBaths = Integer.parseInt(bathrooms.getText());
		} catch (Exception ex) {
			alert.setAlertType(AlertType.ERROR);
			alert.setTitle("Error creating new property");
			alert.setContentText(
					"Please enter valid values for rent, size, longitude, latitude, bedrooms and bathrooms");
			alert.show();
			return;
		}

		if (type.getText() == "" || postcode.getText() == "" || furnishing.getValue() == null
				|| hasGarden.getValue() == null) {
			alert.setAlertType(AlertType.ERROR);
			alert.setTitle("Error creating new property");
			alert.setContentText("Please fill in all details correctly");
			alert.show();
		} else if (!postcode.getText().matches(POSTCODE_VALIDATE)) {
			alert.setAlertType(AlertType.ERROR);
			alert.setTitle("Error creating new property");
			alert.setContentText("Please enter a valid postcode");
			alert.show();
		} else if (rentVal < 200 || pptySize < 0 || noOfBeds < 1 || noOfBaths < 1 || noOfBeds > 5 || noOfBaths > 5) {
			alert.setAlertType(AlertType.ERROR);
			alert.setTitle("Error creating new property");
			alert.setContentText(
					"Please enter values within the valid range: 0 - 5 for bedrooms and bathrooms and at least £200 for monthly rent");
			alert.show();
		} else {
			try {
				CreateAndImportData da = new CreateAndImportData();

				da.createProperty(type.getText(), furnishing.getValue(), postcode.getText(),
						LocalDate.now().format(dateFormatter), hasGarden.getValue(), pptySize, noOfBeds, noOfBaths,
						rentVal, lat, longi);

				DataHandler.writeToFile(da.getAllProperties());

				alert.setAlertType(AlertType.INFORMATION);
				alert.setTitle("Successful");
				alert.setContentText("New Property has been added successfully");

				Optional<ButtonType> result = alert.showAndWait();

				if (result.get() != null) {
					goToPropertiesListener(e);
				}

			} catch (CustomException cex) {
				alert.setAlertType(AlertType.ERROR);
				alert.setTitle("Error creating new Property");
				alert.setContentText(cex.getMessage());
				alert.show();
			} catch (Exception exception) {
				alert.setAlertType(AlertType.ERROR);
				alert.setTitle("Error creating new Property");
				alert.setContentText("An error occured");
				alert.show();
			}
		}
	}

}
