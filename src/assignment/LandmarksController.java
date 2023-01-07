package assignment;

import java.io.IOException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

/**
 * the class handles all actions in the landmarksView scenes - view, add and edit details
 * @author Airat YUsuff 22831467
 */
public class LandmarksController extends DashboardController implements DataFormatter {

	@FXML
	private Label formHeading;

	@FXML
	private TextField name;
	@FXML
	private TextField postcode;
	@FXML
	private TextField latitude;
	@FXML
	private TextField longitude;
	@FXML
	private Button addNew;
	
	@FXML
	private Label emptyListLabel;
	@FXML
	private GridPane allLandmarks;
	@FXML
	private Pane emptyList;
	
	@FXML
	private Button cancelEdit;
	@FXML
	private Button updateDetails;
 
	
	private LandmarkList lList;
	private int gridCount;
	private int currLandmarkIndex;
	
	private final String DEFAULT_FORM_HEADING = "Add New Landmark";
	private final String POSTCODE_VALIDATE = "^[A-Z0-9]{2,4}+ [A-Z0-9]{3}$";

	
	/**
	 * reads the existing customers to a list + default hides edit buttons
	 */
	public void initialize() {
		try {
			lList = DataHandler.readLandmarkList();
			Customer.setLastIndex(lList.getLandmarks().size());	
			populateList();
			
			cancelEdit.setVisible(false);
	    	updateDetails.setVisible(false);
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
	 * hides the empty list view, and populates the specified grid pane with all landmark details read from the file
	 * called by initialize() method
	 */
	private void populateList() {
		gridCount = 1;
		if(Customer.getLastIndex() < 1) {
			emptyList.setVisible(true);
			emptyListLabel.setVisible(true);
			allLandmarks.setVisible(false);
		} else {
			allLandmarks.setVisible(true);
			emptyList.setVisible(false);
			emptyListLabel.setVisible(false);
			
			lList.getLandmarks().forEach(landmark -> {
				Text name = new Text();
				Text postcode = new Text();

				name.setText(landmark.getName());
				postcode.setText(landmark.getPostcode());
				
				Button editBtn = new Button("Edit");
				editBtn.setId(landmark.getLandmarkId());
				editBtn.setOnAction(new EventHandler<ActionEvent>() {
				    @Override
				    public void handle(ActionEvent event) {
						currLandmarkIndex = Integer.parseInt(editBtn.getId().split("l")[1]);		
				    	editLandmarkDetails(currLandmarkIndex);			    	
				    }
				});	
								
				allLandmarks.getRowConstraints().add(new RowConstraints(40));
				allLandmarks.add(name, 0, gridCount);
				allLandmarks.add(postcode, 1, gridCount);
				allLandmarks.add(editBtn, 2, gridCount);
				
				//add padding to each cell
				GridPane.setMargin(name, new Insets(5));
				GridPane.setMargin(postcode, new Insets(5));
				GridPane.setMargin(editBtn, new Insets(5));
				gridCount++;
			});
			
		}
	}
	
	/**
	 * formats/validates inputs for creating new landmark, adds it to the list, and rewrites the list to its corresponding file
	 * @param e
	 * @throws IOException
	 */
	public void addNewLandmarkBtnListener(ActionEvent e) throws IOException {
		Alert alert = new Alert(AlertType.NONE);

		try {
			Double.parseDouble(latitude.getText());
			Double.parseDouble(longitude.getText());
		}
		catch(Exception ex) {
			alert.setAlertType(AlertType.ERROR);
            alert.setTitle("Error creating new landmark");
            alert.setContentText("Please enter correct longitide and latitiude values");
            alert.show();
            return;
		}
		
		if(name.getText() == "" || postcode.getText() == "") {
			alert.setAlertType(AlertType.ERROR);
            alert.setTitle("Error creating new landmark");
            alert.setContentText("Please fill in all details correctly");
            alert.show();
		} 
		else if (!postcode.getText().matches(POSTCODE_VALIDATE)) {
			alert.setAlertType(AlertType.ERROR);
            alert.setTitle("Error creating new landmark");
            alert.setContentText("Please enter a valid postcode");
            alert.show();
		}
		else {
			try {
				CreateAndImportData da = new CreateAndImportData();
				
				da.createLandmark(name.getText(), postcode.getText(), Double.parseDouble(latitude.getText()), Double.parseDouble(longitude.getText()));
				DataHandler.writeToFile(da.getAllLandmarks());
				
				alert.setAlertType(AlertType.INFORMATION);
	            alert.setTitle("Successful");
	            alert.setContentText("New landmark has been added successfully");

	            //show alert, wait for user to close and then refresh
	            Optional<ButtonType> result = alert.showAndWait();
	            
	            if(result.get() != null) {
	            	goToLandmarksListener(e);
	            }            
			}
			catch(CustomException cex) {
				alert.setAlertType(AlertType.ERROR);
                alert.setTitle("Error creating new landmark");
                alert.setContentText(cex.getMessage());
                alert.show();
			}
			catch(Exception exception) {
				alert.setAlertType(AlertType.ERROR);
                alert.setTitle("Error creating new landmark");
                alert.setContentText("An error occured");
                alert.show();
			}
		}
    }
	
	/**
	 * prefills the form with the details on click of the edit button 
	 * @param index
	 */
	private void editLandmarkDetails(int index) {
		Landmark currLandmark = lList.getLandmarks().get(index);
		
		formHeading.setText("Edit " + currLandmark.getName());
		name.setText(currLandmark.getName());
    	postcode.setText(currLandmark.getPostcode());
    	latitude.setText(Double.toString(currLandmark.getLatitude()));
    	longitude.setText(Double.toString(currLandmark.getLongitude()));
    	
    	addNew.setVisible(false);
    	cancelEdit.setVisible(true);
    	updateDetails.setVisible(true);
	}
	
	/**
	 * resets the form fields when cancelling an edit
	 */
	public void cancelEditListener() {	
    	formHeading.setText(DEFAULT_FORM_HEADING);
    	name.setText(null);
    	postcode.setText(null);
    	latitude.setText(null);
    	longitude.setText(null);
    	
    	addNew.setVisible(true);
    	cancelEdit.setVisible(false);
    	updateDetails.setVisible(false);
	}
	
	/**
	 * validates user input, updates the landmark details with the new values and writes the updated list to file, 
	 * overwriting the previously stored list
	 * @param e
	 * @throws IOException
	 */
	public void updateLandmarkBtnListener(ActionEvent e) throws IOException {
		Alert alert = new Alert(AlertType.NONE);
		Landmark currLandmark = lList.getLandmarks().get(currLandmarkIndex);
		
		double lat = 0;
		double longi = 0;
		
		try {
			lat = Double.parseDouble(latitude.getText());
			longi = Double.parseDouble(longitude.getText());
		}
		catch(Exception ex) {
			alert.setAlertType(AlertType.ERROR);
            alert.setTitle("Error updating landmark");
            alert.setContentText("Please enter correct longitide and latitiude values");
            alert.show();
            return;
		}

		if(name.getText() == "" || postcode.getText() == "" || latitude.getText() == "" || longitude.getText() == "") {
			alert.setAlertType(AlertType.ERROR);
            alert.setTitle("Error updating landmark");
            alert.setContentText("Please fill in all details correctly");
            alert.show();
		} else if (!postcode.getText().matches(POSTCODE_VALIDATE)) {
			alert.setAlertType(AlertType.ERROR);
            alert.setTitle("Error updating landmark");
            alert.setContentText("Please enter a valid postcode");
            alert.show();
		}
		else {
			try {
				// check if updates match existing landmark
				boolean landmarkExists = false;
				for (Landmark l : lList.getLandmarks()) {
					if (l.getName().equals(name.getText()) && l.getPostcode().equals(postcode.getText())
							&& l.getLatitude() == lat && l.getLongitude() == longi) {
						landmarkExists = true;
						break;
					}
				}
				if (!landmarkExists) {				
					currLandmark.setName(name.getText());
					currLandmark.setPostcode(postcode.getText());
					currLandmark.setLatitude(lat);
					currLandmark.setLongitude(longi);
					
					DataHandler.writeToFile(lList);
					
					alert.setAlertType(AlertType.INFORMATION);
		            alert.setTitle("Successful");
		            alert.setContentText("Landmark details have been updated successfully");

		            //show alert, wait for user to close and then refresh
		            Optional<ButtonType> result = alert.showAndWait();
		            
		            if(result.get() != null) {
		            	goToLandmarksListener(e);
		            } 
				} else {
					alert.setAlertType(AlertType.ERROR);
					alert.setTitle("Error updating landmark");
					alert.setContentText("Cannot update landmark as another landmark already exists with these details");
					alert.show();
				}         
			}
			catch(Exception exception) {
				alert.setAlertType(AlertType.ERROR);
                alert.setTitle("Error updating landmark");
                alert.setContentText("An error occured");
                alert.show();
			}
		}
	}
}
