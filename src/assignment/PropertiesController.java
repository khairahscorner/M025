package assignment;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

public class PropertiesController extends DashboardController implements DateFormatter {
	
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
	
	private String selectedPptyAvailability = "All Properties";
	private String selectedFurnishing = null;
	    
	

	
	public void initialize() throws ClassNotFoundException, IOException {
		pptyAvailability.getItems().addAll("All Properties", "Rented", "Available");
		editFurnishing.getItems().addAll("Unfurnished", "Semi-furnished", "Furnished");
		
		pList = DataHandler.readPropertyList();
		Property.setLastPropertyIndex(pList.getProperties().size());
	      
	      lList = DataHandler.readLandmarkList();
	      Landmark.setLastIndex(lList.getLandmarks().size());
	         
    	  
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
    		  
    		  populateList(pList); 
    		    		  
    	  }
    	  
    	  //ensures the other side showing the details only shows empty on load
    	  emptyDetailsPane.setVisible(true);
    	  pptyDetailsPane.setVisible(false);
  	   	  
    	  
  }
	
	private void populateList(PropertyList pList) {
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
		
		String pptyPostCode = currPpty.getPostcode().split(" ")[0];
		List<Landmark> allLandmarks = lList.getLandmarks();
		
		pptyDetailsArea.appendText("\n\n ---- Proximity to Landmarks in " + pptyPostCode + " ----\n");

		for(int i = 0; i < Landmark.getLastIndex(); i++) {
			String landmarkCode = allLandmarks.get(i).getPostcode().split(" ")[0];
			if(pptyPostCode.equals(landmarkCode)) {
				pptyDetailsArea.appendText(allLandmarks.get(i).getName() + ": " + dpFormatter.format(
						DistanceCalculator.getDistance(currPpty.getLatitude(), currPpty.getLongitude(), allLandmarks.get(i).getLatitude(), 
								allLandmarks.get(i).getLongitude(), "K")) + "km\n"
				);
				
			};
		}
		pptyDetailsArea.setEditable(false);		
    }
	
	
	public void selectAvailabilityListener() {
		selectedPptyAvailability = pptyAvailability.getValue();
		
		//how to clear the gridPane replace with the new values and still retain the grid lines????
		
		// REFERENCED CODE -START
		pptiesWrapper.getChildren().clear();
		while(pptiesWrapper.getRowConstraints().size() > 0){
			pptiesWrapper.getRowConstraints().remove(0);
		}
		// STOP
		
		populateList(filterProperties()); 
		
		System.out.println(pList.getProperties().size());
	}
	
	private PropertyList filterProperties() {
		PropertyList pListToShow = new PropertyList();
		System.out.println(selectedPptyAvailability);
		
		if(selectedPptyAvailability.equals("Rented")) {
			pList.getKeys().forEach(key -> {
				if(pList.getProperties().get(key).getRentalStatus()) {
			    	Property availablePpty = pList.getProperties().get(key);		    	
			    	pListToShow.addProperty(availablePpty);
			    }
			});
			System.out.println(pListToShow.getProperties().size());
		} 
		else if (selectedPptyAvailability.equals("Available")) {
			pList.getKeys().forEach(key -> {
				if(!pList.getProperties().get(key).getRentalStatus()) {
			    	Property availablePpty = pList.getProperties().get(key);		    	
			    	pListToShow.addProperty(availablePpty);
			    }
			});
			System.out.println(pListToShow.getProperties().size());
		}
		else {
			pListToShow.setPropertyList(pList);
			System.out.println(pListToShow.getProperties().size());
		}
		  
		return pListToShow;
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
	
	public void selectFurnishingStatusListener() {
//		selectedFurnishing = editFurnishing.valueProperty().getValue();
		selectedFurnishing = editFurnishing.getValue();
		System.out.println(selectedFurnishing);
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




