package assignment;

import java.io.IOException;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
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
	private GridPane pptiesWrapper;
	
	
	@FXML
	private Label pptyTitle;
	@FXML
	private TextArea pptyDetailsArea;
	
	@FXML
	private ComboBox<String> pptyAvailability;
	
	
	private LandmarkList lList;
	private PropertyList pList;
	private Property currPpty;
	
	private String selectedPptyAvailability = "All Properties";
	    
	

	
	public void initialize() throws ClassNotFoundException, IOException {
		pptyAvailability.getItems().addAll("All Properties", "Rented", "Available");
		
		
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
			    	ViewPropertyDetailsListener(key);			    	
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
	
	public void ViewPropertyDetailsListener(String currPptyId){
		emptyDetailsPane.setVisible(false);
		pptyDetailsPane.setVisible(true);
		
		Image propertyImage = new Image("file:images/a.jpg");
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
		selectedPptyAvailability = pptyAvailability.valueProperty().getValue();
		
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
	
	public PropertyList filterProperties() {
		PropertyList pListToShow = new PropertyList();
		System.out.println(selectedPptyAvailability);
		
		if(selectedPptyAvailability.equals("Rented")) {
			for (String key : pList.getKeys()) {
			    if(pList.getProperties().get(key).getRentalStatus()) {
			    	Property availablePpty = pList.getProperties().get(key);		    	
			    	pListToShow.addProperty(availablePpty);
			    }
			}
			System.out.println(pListToShow.getProperties().size());
		} else if (selectedPptyAvailability.equals("Available")) {
			for (String key : pList.getKeys()) {
			    if(!pList.getProperties().get(key).getRentalStatus()) {
			    	Property availablePpty = pList.getProperties().get(key);		    	
			    	pListToShow.addProperty(availablePpty);
			    }
			} 
			System.out.println(pListToShow.getProperties().size());
		}
		else {
			pListToShow.setPropertyList(pList);
			System.out.println(pListToShow.getProperties().size());
		}
		  
		return pListToShow;
	}
}


