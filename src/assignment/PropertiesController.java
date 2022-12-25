package assignment;

import java.io.IOException;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

public class PropertiesController extends DashboardController implements DateFormatter {
	
	@FXML
	private Text pptyViewPlaceholder;
	
	@FXML
	private ImageView pptyImg;
	
	@FXML
	private Text pptyName;
	@FXML
	private Text pptyCode;
	@FXML
	private Text pptyRent;
	@FXML
	private Text pptyDateListed;

	@FXML
	private Label availableStatus;
	@FXML
	private Label rentedStatus;
	@FXML
	private Button viewPptyDetails;
	
	
	@FXML
	private Button viewPptyDetails1;
	@FXML
	private Label emptyListLabel;
	@FXML
	private Pane emptyPptyList;
	@FXML
	private Pane emptyDetailsPane;
	@FXML
	private Pane pptyDetailsPane;
	@FXML
	private TilePane pptiesWrapper;
	
	
	@FXML
	private Label pptyTitle;
	@FXML
	private TextArea pptyDetailsArea;
	
	
	private LandmarkList lList;
	private PropertyList pList;
	private Property currPpty;
	    
	
	@FXML
	private ImageView pptyImg1;
	
	@FXML
	private Text pptyName1;
	@FXML
	private Text pptyCode1;
	@FXML
	private Text pptyRent1;
	@FXML
	private Text pptyDateListed1;

	@FXML
	private Label availableStatus1;
	@FXML
	private Label rentedStatus1;
	    

	
	public void initialize() throws ClassNotFoundException, IOException {
		pList = DataHandler.readPropertyList();
		Property.setLastPropertyIndex(pList.getProperties().size());
	      
	      lList = DataHandler.readLandmarkList();
	      Landmark.setLastIndex(lList.getLandmarks().size());
	      
	      
	      Image propertyImage = new Image("file:images/a.jpg");
	      Image propertyImage1 = new Image("file:images/b.jpg");
    	  pptyImg.setImage(propertyImage);
    	  pptyImg1.setImage(propertyImage1);
    	  
    	  if(pList.getProperties().size() == 0) {
    		  emptyPptyList.setVisible(true);
    		  pptiesWrapper.setVisible(false);
    	  }
    	  else {
    		  pptiesWrapper.setVisible(true);
    		  emptyPptyList.setVisible(false);
    		  
    		  
    		  
    		  //need to know how to write for multiple properties, and set fx:id for the button to the pptyId
	    	  String key = pList.getKeys().get(0);
	    	  Property p0 = pList.getProperties().get(key);
	    	  pptyName.setText("Type: " + p0.getType());
	    	  pptyCode.setText("Property Code: " + p0.getPropertyId());
	    	  pptyRent.setText("Rent: " + dpFormatter.format(p0.getRentPerMonth()) + "pcm");
	    	  pptyDateListed.setText("Date Listed: " + p0.getDateListed().format(dateFormatter));
	    	  
	    	  if(p0.getRentalStatus()) {
	    		  availableStatus.setVisible(false);
	    		  rentedStatus.setVisible(true);
	    	  } else {
	    		  availableStatus.setVisible(true);
	    		  rentedStatus.setVisible(false);
	    	  }
	    	  viewPptyDetails.setId(key);
	    	  
	    	  String key1 = pList.getKeys().get(1);
	    	  Property p1 = pList.getProperties().get(key1);
	    	  pptyName1.setText("Type: " + p1.getType());
	    	  pptyCode1.setText("Property Code: " + p1.getPropertyId());
	    	  pptyRent1.setText("Rent: £" + dpFormatter.format(p1.getRentPerMonth()) + "pcm");
	    	  pptyDateListed1.setText("Date Listed: " + p1.getDateListed().format(dateFormatter));
	    	  
	    	  if(p1.getRentalStatus()) {
	    		  availableStatus1.setVisible(false);
	    		  rentedStatus1.setVisible(true);
	    	  } else {
	    		  availableStatus1.setVisible(true);
	    		  rentedStatus1.setVisible(false);
	    	  }
	    	  viewPptyDetails1.setId(key1);
    	  }
    	  
    	  //ensures the other side showing the details only shows empty on load
    	  emptyDetailsPane.setVisible(true);
    	  pptyDetailsPane.setVisible(false);

    	  
//    	  for(int i = 0; i < pList.getProperties().size(); i++) {
//	    	  Image propertyImage = new Image("file:images/a.jpg");
//	    	  ImageView pptyImgView = new ImageView(propertyImage);
//	    	  Pane pptyCard = new Pane();
//	    	  
//	    	  String key = pList.getKeys().get(i);
//	    	  Property ppty = pList.getProperties().get(key);
////	    	  ppty.setRentalStatus(true);
//	    	  Text pptyName = new Text();
//	    	  Text pptyRent = new Text();
//	    	  Text pptyDateListed = new Text();
//	    	  Label availableStatus = new Label();
//	    	  Label rentedStatus = new Label();
//	    	  Button viewPptyDetails = new Button();
//	    	  
//	    	  pptyName.setText("Type: " + ppty.getType());
//	    	  pptyRent.setText("Rent: " + dpFormatter.format(ppty.getRentPerMonth()) + "pcm");
//	    	  pptyDateListed.setText("Date Listed: " + ppty.getDateListed().format(dateFormatter));
//	    	  
//	    	  if(ppty.getRentalStatus()) {
//	    		  availableStatus.setVisible(false);
//	    		  rentedStatus.setVisible(true);
//	    	  } else {
//	    		  availableStatus.setVisible(true);
//	    		  rentedStatus.setVisible(false);
//	    	  }
//	    	  viewPptyDetails.setId(key);
//	    	  
//	    	  pptyCard.getChildren().add(pptyImgView);
//	    	  pptyCard.getChildren().add(pptyName);
//	    	  pptyCard.getChildren().add(pptyRent);
//	    	  pptyCard.getChildren().add(pptyDateListed);
//	    	  pptyCard.getChildren().add(availableStatus);
//	    	  pptyCard.getChildren().add(rentedStatus);
//	    	  pptyCard.getChildren().add(viewPptyDetails);
//	    	   
//	    	  pptiesWrapper.getChildren().add(pptyCard);
//	      }    	  

    	  
    	  
  }
	
	public void ViewBtnListener(ActionEvent e) throws IOException {
		emptyDetailsPane.setVisible(false);
		pptyDetailsPane.setVisible(true);
		
		String currKey = viewPptyDetails.getId();
		currPpty = pList.getProperties().get(currKey);
		
		pptyTitle.setText(currPpty.getFurnishedStatus() + " " + currPpty.getType());
		pptyDetailsArea.setText(Property.getPropertyDetails(currPpty));
		
		String pptyCode = currPpty.getPostcode().split(" ")[0];
		List<Landmark> allLandmarks = lList.getLandmarks();
		
		pptyDetailsArea.appendText("\n\n ---- Proximity to Landmarks in " + pptyCode + " ----\n");

		for(int i = 0; i < Landmark.getLastIndex(); i++) {
			String landmarkCode = allLandmarks.get(i).getPostcode().split(" ")[0];
			if(pptyCode.equals(landmarkCode)) {
				pptyDetailsArea.appendText(allLandmarks.get(i).getName() + ": " + dpFormatter.format(
						DistanceCalculator.getDistance(currPpty.getLatitude(), currPpty.getLongitude(), allLandmarks.get(i).getLatitude(), 
								allLandmarks.get(i).getLongitude(), "K")) + "km\n"
				);
				
			};
		}
		pptyDetailsArea.setEditable(false);		
    }
	
	public void ViewBtn1Listener(ActionEvent e) throws IOException {
		emptyDetailsPane.setVisible(false);
		pptyDetailsPane.setVisible(true);
		
		String currKey = viewPptyDetails1.getId();
		currPpty = pList.getProperties().get(currKey);
		
		pptyTitle.setText(currPpty.getFurnishedStatus() + " " + currPpty.getType());
		pptyDetailsArea.setText(Property.getPropertyDetails(currPpty));
				
		String pptyCode = currPpty.getPostcode().split(" ")[0];
		List<Landmark> allLandmarks = lList.getLandmarks();
		
		pptyDetailsArea.appendText("\n\n ---- Proximity to Landmarks in " + pptyCode + " ----\n");
		
		for(int i = 0; i < Landmark.getLastIndex(); i++) {
			String landmarkCode = allLandmarks.get(i).getPostcode().split(" ")[0];
			if(pptyCode.equals(landmarkCode)) {
				pptyDetailsArea.appendText(allLandmarks.get(i).getName() + ": " + dpFormatter.format(
						DistanceCalculator.getDistance(currPpty.getLatitude(), currPpty.getLongitude(), allLandmarks.get(i).getLatitude(), 
								allLandmarks.get(i).getLongitude(), "K")) + "km\n"
				);
				
			};
		}
		pptyDetailsArea.setEditable(false);	
		
    }
}
