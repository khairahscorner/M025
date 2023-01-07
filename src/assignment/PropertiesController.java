package assignment;

import java.io.IOException;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;

/**
 * the class is used to display a list of all properties, filter & sort options, view and edit the details of a specifc property
 * @author Airat YUsuff 22831467
 *
 */
public class PropertiesController extends DashboardController implements DataFormatter {
	
	@FXML
	private Pane optionsWrapper;
	@FXML
	private GridPane headerWrapper;	

	
	@FXML
	private ImageView pptyImg;
	@FXML
	private Label availableStatus;
	@FXML
	private Label rentedStatus;

	@FXML
	private Label emptyListLabel;
	@FXML
	private Pane emptyPptyList;
	@FXML
	private Pane emptyDetailsPane;
	@FXML
	private Pane pptyDetailsPane;
	@FXML
	private Pane mainPptyDetails;
	@FXML
	private GridPane pptiesWrapper;
		
	@FXML
	private Label pptyTitle;
	@FXML
	private TextArea pptyDetailsArea;
	
	@FXML
	private Label landmarkTitle;
	@FXML
	private Text distance;
	@FXML
	private ComboBox<String> landmarkOptions;
	
	@FXML
	private ComboBox<String> pptyAvailability;
	@FXML
	private ComboBox<String> pptyPrice; 
	@FXML
	private ComboBox<String> pptyDate; 
	@FXML
	private ComboBox<String> pptyBedrooms;
	@FXML
	private ComboBox<String> pptyBathrooms;
	@FXML
	private ComboBox<String> pptyPostcode;
	@FXML
	private Text tableCount;
	
	@FXML
	private Button searchBtn;
	@FXML
	private TextField searchField;
	
	@FXML
	private Pane editPptyDetails;
	@FXML
	private Button editBtn;
	@FXML
	private TextField editType;
	@FXML
	private TextField editPostcode;
	@FXML
	private TextField editSize;
	@FXML
	private TextField editRent;
	@FXML
	private ComboBox<String> editFurnishing;
	@FXML
	private ComboBox<String> editGarden;
	@FXML
	private TextField editBedrooms;
	@FXML
	private TextField editBathrooms;
	@FXML
	private TextField editLongitude;
	@FXML
	private TextField editLatitude;
		
	
	private LandmarkList lList;
	private PropertyList pList;
	private Property currPpty;
	private String selectedPptyAvailability;
		
//	private String defaultDateSort = "Most Recent";
	private String sortOption = null;
	private final String POSTCODE_VALIDATE = "^[A-Z0-9]{2,4}+ [A-Z0-9]{3}$";
	  

	/**
	 * read specified existing files into lists + adds options to comboBoxes
	 */
	public void initialize() {
		try {
			pList = DataHandler.readPropertyList();
			Property.setLastPropertyIndex(pList.getProperties().size());
		    		    
			pptyAvailability.getItems().addAll("Rented", "Available", "All Properties");
			editFurnishing.getItems().addAll("Unfurnished", "Semi-Furnished", "Furnished");
			editGarden.getItems().addAll("Yes", "No");
			
			pptyPrice.getItems().addAll("Low to High", "High to Low");
			pptyDate.getItems().addAll("Most Recent", "Earliest");
			pptyBedrooms.getItems().addAll("1", "2", "3+");
			pptyBathrooms.getItems().addAll("1", "2", "3+");
			
			/**
			 * get the first half of all postcodes and save the unique keys as values for the "filter by postcode" option; 
			 * this will return more matched results as opposed to specific postcodes
			 */
			HashMap<String,String> postcodeMap = new HashMap<String,String>();  
			for (String key: pList.getKeys()) {
				String pptyPostcode = pList.getProperties().get(key).getPostcode();
				postcodeMap.put(pptyPostcode.split(" ")[0], pptyPostcode.split(" ")[0]);
			}
			pptyPostcode.getItems().addAll(postcodeMap.keySet());
			

			//populate the dropdown with landmark names
		    lList = DataHandler.readLandmarkList();
		    
			for (Landmark l: lList.getLandmarks()) {
				landmarkOptions.getItems().add(l.getName());
			}
		           
	    	if(pList.getProperties().size() == 0) {
	    		emptyPptyList.setVisible(true);
	    		pptiesWrapper.setVisible(false);
	    		optionsWrapper.setVisible(false);
	    		headerWrapper.setVisible(false);
	    	}
	    	 else {
	    		 emptyPptyList.setVisible(false); 
	    		 pptiesWrapper.setVisible(true);
	    		 optionsWrapper.setVisible(true);
	    		 headerWrapper.setVisible(true); 		  

	    		 populateList(pList);
	    	  }
	    	  
	    	  //ensures the other side showing the details only shows empty on load
	    	  emptyDetailsPane.setVisible(true);
	    	  pptyDetailsPane.setVisible(false);
	    	  editPptyDetails.setVisible(false);

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
	 * populates the specified grid pane with all property details read from the file
	 * called by initialize() method
	 */
	private void populateList(PropertyList pList) {
		tableCount.setText("List count: " + pList.getProperties().size());
		
		for(int i = 0; i < pList.getProperties().size(); i++) {
			Text pptyCode = new Text();
			Text pptyDateListed = new Text();
			Text rentalStatus = new Text();
				
			String key = pList.getKeys().get(i);
	    	Property currPpty = pList.getProperties().get(key);
  	    	pptyCode.setText(currPpty.getPropertyId());
  	    	pptyDateListed.setText(currPpty.getDateListed().format(dateFormatter));
  	    	  
  	    	if(currPpty.getRentalStatus()) {
  	    		rentalStatus.setText("Rented");
  	    	} else {
  	    		  rentalStatus.setText("Available");
  	    	}
  	    	
  	    	Button viewBtn = new Button("View Details");
			viewBtn.setId(key);
			viewBtn.setOnAction(new EventHandler<ActionEvent>() {
			    @Override
			    public void handle(ActionEvent event) {
			    	viewPropertyDetailsListener(key);			    	
			    }
			});
  	    	 
  	    	pptiesWrapper.getRowConstraints().add(new RowConstraints(40));
  	    	pptiesWrapper.add(pptyCode, 0, i);
  	    	pptiesWrapper.add(pptyDateListed, 1, i);
  	    	pptiesWrapper.add(rentalStatus, 2, i);
  	    	pptiesWrapper.add(viewBtn, 3, i);
				
			//add padding to each cell
			GridPane.setMargin(pptyCode, new Insets(5));
			GridPane.setMargin(pptyDateListed, new Insets(5));
			GridPane.setMargin(rentalStatus, new Insets(5));		
			GridPane.setMargin(viewBtn, new Insets(5));
		}
	}
	
	/**
	 * action handler for the button to add new property
	 * @param e
	 * @throws IOException
	 */
	public void goToAddPropertyListener(ActionEvent e) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource("PropertyCreate.fxml")); 
	      
	    Scene scene = new Scene(parent); 
	    scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
	
	    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
	    stage.setTitle("Add Property - CSYM025 Lettings"); 
	    stage.setScene(scene);
	    stage.show(); 
	}
	
	/**
	 * show all details of the clicked property
	 * @param currPptyId id of the property to display
	 */
	private void viewPropertyDetailsListener(String currPptyId) {
		emptyDetailsPane.setVisible(false);
		editPptyDetails.setVisible(false);
		pptyDetailsPane.setVisible(true);
		mainPptyDetails.setVisible(true);

		
		editBtn.setId(currPptyId);
		Image propertyImage = new Image("file:images/b.jpg");
		pptyImg.setImage(propertyImage);
				
		currPpty = pList.getProperties().get(currPptyId);
		
		if(currPpty.getRentalStatus()) {
	  		  availableStatus.setVisible(false);
	  		  rentedStatus.setVisible(true);
	  	  } else {
	  		  availableStatus.setVisible(true);
	  		  rentedStatus.setVisible(false);
	  	  }
		
		pptyTitle.setText(currPpty.getFurnishedStatus() + " " + currPpty.getType());
		pptyDetailsArea.setText(Property.getPropertyDetails(currPpty));
		
		//display closest places of interest 
		List<Landmark> allLandmarks = lList.getLandmarks();
		pptyDetailsArea.appendText(Property.getClosestLandmarksDistance(currPpty, allLandmarks));
		
		pptyDetailsArea.setEditable(false);
		
		//hide the distance field for places of interest selected from dropdown
		distance.setVisible(false);
    }
	
	
	public void getDistanceListener() {
		String selectedLandmarkName = landmarkOptions.getValue();
		Landmark currLandmark = new Landmark();
		for(Landmark l : lList.getLandmarks()) {
			if(l.getName().equals(selectedLandmarkName)) {
				currLandmark = l;
				distance.setText(PropertyActions.getDistanceToLandmark(currPpty, currLandmark));
				distance.setVisible(true);
			}
		};
	}
	/**
	 * filter properties list based on search value
	 */
	public void searchPropertiesListener() {
		clearTable();
		
		populateList(PropertyActions.filterPropertiesBySearch(pList, searchField.getText()));
	}
	
	/**
	 * filter properties list based on property availability (rentalStatus)
	 */
	public void selectAvailabilityListener() {
		selectedPptyAvailability = pptyAvailability.getValue();
		
		clearTable();
		
		populateList(PropertyActions.filterPropertiesByRentalStatus(pList, selectedPptyAvailability)); 
	}
	
	/**
	 * sort properties list based on price (low to high/high to low)
	 */
	public void selectPriceListener() {
		String option = pptyPrice.getValue();
		if(option == "Low to High") {
			sortOption = "ASC";
		} else {
			sortOption = "DESC";
		}
		clearTable();
		
		populateList(PropertyActions.sortPropertiesByPrice(pList, sortOption));
		
	}
	
	/**
	 * sort properties list based on date listed (most recent/earliest)
	 */
	public void selectDateListedListener() {
		String option = pptyDate.getValue();
		if(option == "Earliest") {
			sortOption = "ASC";
		} else {
			sortOption = "DESC";
		}
		clearTable();
		
		populateList(PropertyActions.sortPropertiesByDate(pList, sortOption));
		
	}
	
	/**
	 * filter properties list based on number of bedrooms (3+ for 3 or more bedrooms)
	 */
	public void selectBedroomsListener() {

		int num = 0;
		String option = pptyBedrooms.getValue();
		if(option == "3+") {
			num = 3;
		} else {
			num = Integer.parseInt(option);
		}
		clearTable();
		
		populateList(PropertyActions.filterPropertiesByBedrooms(pList, num));
		
	}
	
	/**
	 * filter properties list based on number of bathrooms (3+ for 3 or more bathrooms)
	 */
	public void selectBathroomsListener() {
		int num = 0;
		String option = pptyBathrooms.getValue();
		if(option == "3+") {
			num = 3;
		} else {
			num = Integer.parseInt(option);
		}
		clearTable();
		
		populateList(PropertyActions.filterPropertiesByBathrooms(pList, num));
		
	}
	
	/**
	 * filter properties list based on postcode area
	 */
	public void selectPostcodeListener() {
		String postcode = pptyPostcode.getValue();

		clearTable();
		
		populateList(PropertyActions.filterPropertiesByPostcode(pList, postcode));
		
	}
	
	/**
	 * clears existing table in grid pane
	 */
	private void clearTable() {
		pptiesWrapper.getChildren().clear();
		//<-***** Waligóra(2017) [3] - START
		while(pptiesWrapper.getRowConstraints().size() > 0){
			pptiesWrapper.getRowConstraints().remove(0);
		}
		//->***** Waligóra(2017) [3] - END
	}
	
	/**
	 * switches the details view to edit property and populates the form fields with the property values
	 */
	public void openEditViewListener() {
		editPptyDetails.setVisible(true);
		pptyDetailsPane.setVisible(false);
		mainPptyDetails.setVisible(false);
		emptyDetailsPane.setVisible(false);

		editType.setText(currPpty.getType());
		editPostcode.setText(currPpty.getPostcode());
		editSize.setText(dpFormatter.format(currPpty.getSize()));
		editRent.setText(dpFormatter.format(currPpty.getRentPerMonth()));
		if(currPpty.getGarden().equals("y")) {
			editGarden.setValue("Yes");
		}
		else {
			editGarden.setValue("No");
		}
		editFurnishing.setValue(currPpty.getFurnishedStatus());
		editBedrooms.setText(Integer.toString(currPpty.getBedrooms()));
		editBathrooms.setText(Integer.toString(currPpty.getBathrooms()));
		editLongitude.setText(Double.toString(currPpty.getLongitude()));
		editLatitude.setText(Double.toString(currPpty.getLatitude()));
	}
	
	/**
	 * cancels property edit and resets form values
	 */
	public void goBackToDetailsViewListener() {
		editPptyDetails.setVisible(false);
		mainPptyDetails.setVisible(true);
		pptyDetailsPane.setVisible(true);
	}
	
	/**
	 * validates input, updates property details in list and rewrites the list to its corresponding file
	 * @throws IOException
	 */
	public void updatePptyDetailsListener() throws IOException {
		Alert alert = new Alert(AlertType.NONE);
		double rentVal = 0;
		double pptySize = 0;
		int bedrooms = 0;
		int bathrooms = 0;
		
		//strings to format the parsed double values first, before parsing again as double parameters for the property
		double latitude = 0;
		double longitude = 0;
		
		try {
			bedrooms = Integer.parseInt(editBedrooms.getText());
			bathrooms = Integer.parseInt(editBathrooms.getText());
			rentVal = Double.parseDouble(editRent.getText());
			pptySize = Double.parseDouble(editSize.getText());
			latitude = Double.parseDouble(editLatitude.getText());
			longitude = Double.parseDouble(editLongitude.getText());
		}
		catch(Exception e) {
			alert.setAlertType(AlertType.ERROR);
            alert.setTitle("Error updating property");
            alert.setContentText("Please enter valid values for longitude, latitude, rent, bedrooms and/or bathrooms");
            alert.show();
            return;
		}
		
		try {
			
		}
		catch(Exception ex) {
			alert.setAlertType(AlertType.ERROR);
            alert.setTitle("Error creating new property");
            alert.setContentText("Please enter correct longitide and latitiude values");
            alert.show();
            return;
		}

		if(editType.getText() == "" || editPostcode.getText() == "" || editFurnishing.getValue() == null || editGarden.getValue() == null) {
			alert.setAlertType(AlertType.ERROR);
            alert.setTitle("Error updating property");
            alert.setContentText("Please fill in all details correctly");
            alert.show();
		}
		else if (!editPostcode.getText().matches(POSTCODE_VALIDATE)) {
			alert.setAlertType(AlertType.ERROR);
            alert.setTitle("Error creating new property");
            alert.setContentText("Please enter a valid postcode");
            alert.show();
		}
		else if(rentVal < 200 || pptySize < 0 || bedrooms < 1 || bathrooms < 1 || bedrooms > 5 || bathrooms > 5) {
			alert.setAlertType(AlertType.ERROR);
            alert.setTitle("Error updating property");
            alert.setContentText("Please enter values within the valid range: 0 - 5 for bedrooms and bathrooms, greater than 0 for size and at least £200 for monthly rent");
            alert.show();
		}
		else {
			try {
				currPpty.setType(editType.getText());
				currPpty.setPostcode(editPostcode.getText());
				currPpty.setFurnishedStatus(editFurnishing.getValue());
				currPpty.setGarden(editGarden.getValue());
				currPpty.setSize(pptySize);
				currPpty.setRentPerMonth(rentVal);
				currPpty.setBedrooms(bedrooms);
				currPpty.setBathrooms(bathrooms);
				currPpty.setLatitude(latitude);
				currPpty.setLongitude(longitude);
				
				DataHandler.writeToFile(pList);

				alert.setAlertType(AlertType.INFORMATION);
	            alert.setTitle("Successful");
	            alert.setContentText("Property has been updated successfully");

	            //show alert, wait for user to close and then refresh
	            Optional<ButtonType> result = alert.showAndWait();
	            
	            if(result.get() != null) {
	            	viewPropertyDetailsListener(currPpty.getPropertyId());
	            }
	            
			} catch(Exception exception) {
				alert.setAlertType(AlertType.ERROR);
                alert.setTitle("Error renting property");
                alert.setContentText("An error occured");
                alert.show();
			}
		}
	}

}

//REFERENCES
//1. Waligóra, J. (2017) How do I delete a row or column in Gridpane. Stack Overflow [online]. Available from: https://stackoverflow.com/questions/23002532/javafx-2-how-do-i-delete-a-row-or-column-in-gridpane#:~:text=works [Accessed 28/12/22].




