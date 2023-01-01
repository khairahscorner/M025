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
	private Pane editPptyDetails;
	@FXML
	private Button editBtn;
	@FXML
	private TextField editRent;
	@FXML
	private ComboBox<String> editFurnishing;
	@FXML
	private TextField editBedrooms;
	@FXML
	private TextField editBathrooms;
	
	
	
	private LandmarkList lList;
	private PropertyList pList;
	private Property currPpty;
	
	private String selectedPptyAvailability;
	
	private String defaultDateSort = "Most Recent";
	private String sortOption = null;
	  

	
	public void initialize() throws ClassNotFoundException, IOException {
		pList = DataHandler.readPropertyList();
		Property.setLastPropertyIndex(pList.getProperties().size());
	      
	    lList = DataHandler.readLandmarkList();
	    Landmark.setLastIndex(lList.getLandmarks().size());
	    
		pptyAvailability.getItems().addAll("Rented", "Available", "All Properties");
		editFurnishing.getItems().addAll("Unfurnished", "Semi-furnished", "Furnished");
		
		pptyPrice.getItems().addAll("Low to High", "High to Low");
		pptyDate.getItems().addAll("Most Recent", "Earliest");
		pptyBedrooms.getItems().addAll("1", "2", "3+");
		pptyBathrooms.getItems().addAll("1", "2", "3+");
		
		HashMap<String,String> postcodeMap = new HashMap<String,String>();  
		for (String key: pList.getKeys()) {
			String pptyPostcode = pList.getProperties().get(key).getPostcode();
			postcodeMap.put(pptyPostcode.split(" ")[0], pptyPostcode.split(" ")[0]);
		}
		pptyPostcode.getItems().addAll(postcodeMap.keySet());
		
	           
    	  if(pList.getProperties().size() == 0) {
    		  emptyPptyList.setVisible(true);
    		  pptiesWrapper.setVisible(false);
    		  optionsWrapper.setVisible(false);
    		  headerWrapper.setVisible(false);
    	  }
    	  else {
    		  pptiesWrapper.setVisible(true);
    		  emptyPptyList.setVisible(false); 
    		  optionsWrapper.setVisible(true);
    		  headerWrapper.setVisible(true); 		  
    		  
    		  //Default to most recent
    		  pptyDate.setValue(defaultDateSort);
    		  populateList(PropertyActions.sortPropertiesByDate(pList, "DESC"));
//    		  populateList(pList);
    	  }
    	  
    	  //ensures the other side showing the details only shows empty on load
    	  emptyDetailsPane.setVisible(true);
    	  pptyDetailsPane.setVisible(false);
  	    
  }
	
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
	
	public void goToAddPropertyListener(ActionEvent e) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource("PropertyCreate.fxml")); 
	      
	    Scene scene = new Scene(parent); 
	    scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
	
	    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
	    stage.setTitle("Add Property - CSYM025 Lettings"); 
	    stage.setScene(scene);
	    stage.show(); 
	}
	
	
	private void viewPropertyDetailsListener(String currPptyId) {
		emptyDetailsPane.setVisible(false);
		editPptyDetails.setVisible(false);
		pptyDetailsPane.setVisible(true);
		mainPptyDetails.setVisible(true);
		
		editBtn.setId(currPptyId);
		
		Image propertyImage = new Image("file:images/b.jpg");
		pptyImg = new ImageView(propertyImage);
				
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
		
		List<Landmark> allLandmarks = lList.getLandmarks();
		pptyDetailsArea.appendText(Property.getLandmarksProximity(currPpty, allLandmarks));
		
		pptyDetailsArea.setEditable(false);		
    }
	
	
	public void selectAvailabilityListener() {
		selectedPptyAvailability = pptyAvailability.getValue();
		
		clearTable();
		
		populateList(PropertyActions.filterPropertiesByRentalStatus(pList, selectedPptyAvailability)); 
		
		
		//reset other options
//		pptyAvailability.setValue(null);
//		pptyAvailability.setPromptText("All Properties");
//		pptyBedrooms.setValue("0");
//		pptyBedrooms.setPromptText("Bedrooms");
//		pptyBathrooms.setValue("0");
//		pptyBathrooms.setPromptText("Bathrooms");
//		pptyPrice.setValue(null);
//		pptyDate.setPromptText("");
//		pptyPostcode.setPromptText("");
	}
	
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
	
	public void selectPostcodeListener() {
		String postcode = pptyPostcode.getValue();

		clearTable();
		
		populateList(PropertyActions.filterPropertiesByPostcode(pList, postcode));
		
	}
	
	
	private void clearTable() {
		//how to still retain the grid lines after replacing with new ppty list????

		// REFERENCED CODE -START
		pptiesWrapper.getChildren().clear();
		while(pptiesWrapper.getRowConstraints().size() > 0){
			pptiesWrapper.getRowConstraints().remove(0);
		}
		// STOP
	}
	
	
	
	public void openEditViewListener() throws ClassNotFoundException, IOException {
		editPptyDetails.setVisible(true);
		mainPptyDetails.setVisible(false);
		emptyDetailsPane.setVisible(false);

		editRent.setText(Integer.toString((int) currPpty.getRentPerMonth()));
		editFurnishing.setValue(currPpty.getFurnishedStatus());
		editBedrooms.setText(Integer.toString(currPpty.getBedrooms()));
		editBathrooms.setText(Integer.toString(currPpty.getBathrooms()));
	}
	
	
	public void goBackToDetailsViewListener() throws ClassNotFoundException, IOException {
		editPptyDetails.setVisible(false);
		mainPptyDetails.setVisible(true);
	}
	
	public void updatePptyDetailsListener() throws ClassNotFoundException, IOException {
		Alert alert = new Alert(AlertType.NONE);
		double rentVal = 0;
		int bedrooms = 0;
		int bathrooms = 0;
		
		try {
			rentVal = Double.parseDouble(editRent.getText());
			bedrooms = Integer.parseInt(editBedrooms.getText());
			bathrooms = Integer.parseInt(editBathrooms.getText());
		}
		catch(Exception e) {
			alert.setAlertType(AlertType.ERROR);
            alert.setTitle("Error updating property");
            alert.setContentText("Please enter valid values for rent, bedrooms and bathrooms");
            alert.show();
            return;
		}
		if(rentVal < 0 || bedrooms < 1 || bathrooms < 1) {
			alert.setAlertType(AlertType.ERROR);
            alert.setTitle("Error updating property");
            alert.setContentText("Please enter values greater than 0 for rent, bedrooms and bathrooms");
            alert.show();
		}
		else if(editFurnishing.getValue() == null) {
			alert.setAlertType(AlertType.ERROR);
            alert.setTitle("Error updating property");
            alert.setContentText("Please enter a furnishing type");
            alert.show();
		}
		else {
			try {				
				currPpty.setFurnishedStatus(editFurnishing.getValue());
				currPpty.setRentPerMonth(rentVal);
				currPpty.setBedrooms(bedrooms);
				currPpty.setBathrooms(bathrooms);
				
				
				DataHandler.writeToFile(pList);

				alert.setAlertType(AlertType.INFORMATION);
	            alert.setTitle("Successful");
	            alert.setContentText("Property has been updated successfully");

	            //show alert, wait for user to close and then refresh
	            Optional<ButtonType> result = alert.showAndWait();
	            
	            if(result.get() == ButtonType.OK) {
	            	viewPropertyDetailsListener(currPpty.getPropertyId());
	            } else {
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




