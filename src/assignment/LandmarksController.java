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
	
	private String defaultFormHeading = "Add New Landmark";
	private String postcodeValidate = "^[A-Z0-9]{2,4}+ [A-Z0-9]{3}$";

	
	
	public void initialize() throws ClassNotFoundException, IOException {
		lList = DataHandler.readLandmarkList();
		Customer.setLastIndex(lList.getLandmarks().size());	
		populateList();
		
		cancelEdit.setVisible(false);
    	updateDetails.setVisible(false);
	}
	
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
	
	public void addNewLandmarkBtnListener(ActionEvent e) throws IOException {
		Alert alert = new Alert(AlertType.NONE);
		String l1 = "";
		String l2 = "";
		
		
		try {
			l1 = locationFormatter.format(Double.parseDouble(latitude.getText()));
			l2 = locationFormatter.format(Double.parseDouble(longitude.getText()));
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
		else if (!postcode.getText().matches(postcodeValidate)) {
			alert.setAlertType(AlertType.ERROR);
            alert.setTitle("Error adding new landmark");
            alert.setContentText("Please enter a valid postcode");
            alert.show();
		}
		else {
			try {
				ImportData da = new ImportData();
				
				da.createLandmark(name.getText(), postcode.getText(), Double.parseDouble(l1), Double.parseDouble(l2));
				DataHandler.writeToFile(da.getAllLandmarks());
				
				alert.setAlertType(AlertType.INFORMATION);
	            alert.setTitle("Successful");
	            alert.setContentText("New landmark has been added successfully");

	            //show alert, wait for user to close and then refresh
	            Optional<ButtonType> result = alert.showAndWait();
	            
	            if(result.get() == ButtonType.OK) {
	            	goToLandmarksListener(e);
	            } else {
	            	//still refresh
	            	goToLandmarksListener(e);
	            }
	         
	            
			} catch(Exception exception) {
				alert.setAlertType(AlertType.ERROR);
                alert.setTitle("Error adding new landmark");
                alert.setContentText("An error occured");
                alert.show();
			}
		}
    }
	
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
	
	
	public void cancelEditListener(ActionEvent e) throws IOException {	
    	formHeading.setText(defaultFormHeading);
    	name.setText(null);
    	postcode.setText(null);
    	latitude.setText(null);
    	longitude.setText(null);
    	
    	addNew.setVisible(true);
    	cancelEdit.setVisible(false);
    	updateDetails.setVisible(false);
	}
	
	public void updateLandmarkBtnListener(ActionEvent e) throws IOException {
		Alert alert = new Alert(AlertType.NONE);
		Landmark currLandmark = lList.getLandmarks().get(currLandmarkIndex);
		
		String l1 = "";
		String l2 = "";
		
		try {
			l1 = locationFormatter.format(Double.parseDouble(latitude.getText()));
			l2 = locationFormatter.format(Double.parseDouble(longitude.getText()));
		}
		catch(Exception ex) {
			alert.setAlertType(AlertType.ERROR);
            alert.setTitle("Error updating landmark details");
            alert.setContentText("Please enter correct longitide and latitiude values");
            alert.show();
            return;
		}

		if(name.getText() == "" || postcode.getText() == "" || latitude.getText() == "" || longitude.getText() == "") {
			alert.setAlertType(AlertType.ERROR);
            alert.setTitle("Error updating landmark details");
            alert.setContentText("Please fill in all details correctly");
            alert.show();
		} else if (!postcode.getText().matches(postcodeValidate)) {
			alert.setAlertType(AlertType.ERROR);
            alert.setTitle("Error updating landmark details");
            alert.setContentText("Please enter a valid postcode");
            alert.show();
		}
		else {
			try {
				currLandmark.setName(name.getText());
				currLandmark.setPostcode(postcode.getText());
				currLandmark.setLatitude(Double.parseDouble(l1));
				currLandmark.setLongitude(Double.parseDouble(l2));
				
				DataHandler.writeToFile(lList);
				
				alert.setAlertType(AlertType.INFORMATION);
	            alert.setTitle("Successful");
	            alert.setContentText("Landmark details have been updated successfully");

	            //show alert, wait for user to close and then refresh
	            Optional<ButtonType> result = alert.showAndWait();
	            
	            if(result.get() == ButtonType.OK) {
	            	goToLandmarksListener(e);
	            } else {
	            	//still refresh
	            	goToLandmarksListener(e);
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
