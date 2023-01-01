package assignment;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class DashboardController {
    
    @FXML
    private Button  loginButton;

    @FXML
    private Label propertyCount; 
    @FXML
    private Label rentalCount; 
    @FXML
    private Label customerCount;
    @FXML
    private Label landmarkCount;
      
    @FXML
    private Button selectFile;
	@FXML
	private ComboBox<String> selectImportType;
	@FXML
    private Button importBtn;
    
    
    private String[] importOptions = {"property", "customer", "landmark"};
    private PropertyList pList;
    private CustomerList cList;
    private RentalList rList;
    private LandmarkList lList;
    
    private String selectedFilePathToImport;
    private String selectedFileTypeToImport;
    
 
    
    public void initialize() throws ClassNotFoundException, IOException {	      
	      pList = DataHandler.readPropertyList();
	      Property.setLastPropertyIndex(pList.getProperties().size());
	      
	      cList = DataHandler.readCustomerList();
	      Customer.setLastIndex(cList.getCustomers().size());
	      
	      rList = DataHandler.readRentalList();
	      Rental.setLastRentalIndex(rList.getRentals().size());
	      
	      lList = DataHandler.readLandmarkList();
	      Landmark.setLastIndex(lList.getLandmarks().size());
	      
	      	      
	      //initial things to run here
	      propertyCount.setText(Integer.toString(pList.getProperties().size()));
	      customerCount.setText(Integer.toString(cList.getCustomers().size()));
	      rentalCount.setText(Integer.toString(rList.getRentals().size()));
	      landmarkCount.setText(Integer.toString(lList.getLandmarks().size()));
	      
	      //set values for dropbox
	      for (String option: importOptions) {
	    	  selectImportType.getItems().add(option);
	      }
    }
    
    public void goToAppStartListener(ActionEvent e) throws IOException {
    	Parent parent = FXMLLoader.load(
	               getClass().getResource("AppStart.fxml")); 
	      
	      // Build the scene graph.
	      Scene scene = new Scene(parent); 
	      scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
	
	      Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
	      // Display our window, using the scene graph.
	      stage.setTitle("Properties - CSYM025 Lettings"); 
	      stage.setScene(scene);
	      stage.show(); 
    }
    
    public void goToDashboardListener(ActionEvent e) throws IOException {
    	Parent parent = FXMLLoader.load(
	               getClass().getResource("Dashboard.fxml")); 
	      
	      // Build the scene graph.
	      Scene scene = new Scene(parent); 
	      scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
	
	      Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
	      // Display our window, using the scene graph.
	      stage.setTitle("Dashboard - CSYM025 Lettings"); 
	      stage.setScene(scene);
	      stage.show(); 
    }
     
    public void goToPropertiesListener(ActionEvent e) throws IOException {
    	Parent parent = FXMLLoader.load(
	               getClass().getResource("Properties.fxml")); 
	      
	      // Build the scene graph.
	      Scene scene = new Scene(parent); 
	      scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
	
	      Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
	      // Display our window, using the scene graph.
	      stage.setTitle("All Properties - CSYM025 Lettings"); 
	      stage.setScene(scene);
	      stage.show(); 
    }
    
    public void goToCustomersListener(ActionEvent e) throws IOException {
    	Parent parent = FXMLLoader.load(
	               getClass().getResource("Customers.fxml")); 
	      
	      // Build the scene graph.
	      Scene scene = new Scene(parent); 
	      scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
	
	      Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
	      // Display our window, using the scene graph.
	      stage.setTitle("All Customers - CSYM025 Lettings"); 
	      stage.setScene(scene);
	      stage.show(); 
    }
    
    public void goToRentalsListener(ActionEvent e) throws IOException {
    	Parent parent = FXMLLoader.load(
	               getClass().getResource("Rentals.fxml")); 
	      
	      // Build the scene graph.
	      Scene scene = new Scene(parent); 
	      scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
	
	      Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
	      // Display our window, using the scene graph.
	      stage.setTitle("Rent Properties - CSYM025 Lettings"); 
	      stage.setScene(scene);
	      stage.show(); 
    }
    
    public void goToInvoicesListener(ActionEvent e) throws IOException {
    	Parent parent = FXMLLoader.load(
	               getClass().getResource("TenancyInvoice.fxml")); 
	      
	      // Build the scene graph.
	      Scene scene = new Scene(parent); 
	      scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
	
	      Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
	      // Display our window, using the scene graph.
	      stage.setTitle("Invoices - CSYM025 Lettings"); 
	      stage.setScene(scene);
	      stage.show(); 
    }
    
    public void goToLandmarksListener(ActionEvent e) throws IOException {
    	Parent parent = FXMLLoader.load(getClass().getResource("Landmarks.fxml")); 
	      
	      // Build the scene graph.
	      Scene scene = new Scene(parent); 
	      scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
	
	      Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
	      // Display our window, using the scene graph.
	      stage.setTitle("Landmarks - CSYM025 Lettings"); 
	      stage.setScene(scene);
	      stage.show(); 
    }
    
    public void selectImportTypeListener() {
    	selectedFileTypeToImport = selectImportType.getValue();
	}
    
    public void filePickerListener(ActionEvent e) throws IOException {	
	      Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
	      FileChooser fileChooser = new FileChooser();
	      File selectedFile = fileChooser.showOpenDialog(stage);
	      
	      selectedFilePathToImport = selectedFile.getPath();
	}
    
    public void importDataListener(ActionEvent e) throws IOException {
		
		Alert alert = new Alert(AlertType.NONE);
		
		if(selectedFilePathToImport == null || selectedFileTypeToImport == null) {
			alert.setAlertType(AlertType.ERROR);
            alert.setTitle("Error importing data");
            alert.setContentText("Please fill in all details correctly");
            alert.show();
		}
		else {
			try {				
				ImportData da = new ImportData(selectedFilePathToImport, selectedFileTypeToImport);
				switch(selectedFileTypeToImport) {
				case "property":
					da.importAllProperties();
					DataHandler.writeToFile(da.getAllProperties());
					break;
				case "customer":
					da.importAllCustomers();
					DataHandler.writeToFile(da.getAllCustomers());
					break;
				case "landmark":
					da.importAllLandmarks();
					DataHandler.writeToFile(da.getAllLandmarks());
					break;
				}
				
				alert.setAlertType(AlertType.INFORMATION);
	            alert.setTitle("Successful");
	            alert.setContentText(selectedFileTypeToImport.toUpperCase() +" has been imported successfully");

	            //show alert, wait for user to close and then refresh
	            Optional<ButtonType> result = alert.showAndWait();
	            
	            if(result.get() == ButtonType.OK) {
	            	goToDashboardListener(e);
	            } else {
	            	//still refresh
	            	goToDashboardListener(e);
	            }     
			}
			catch(Exception exception) {
				alert.setAlertType(AlertType.ERROR);
                alert.setTitle("ERROR IMPORTING DATA");
                alert.setContentText("An error occured");
                alert.show();
			}
		}
}
	
}
