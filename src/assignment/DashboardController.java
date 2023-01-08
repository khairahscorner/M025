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
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * the class populates the dashboard with count values and handles importing data from files
 * @author Airat YUsuff 22831467
 *
 */
public class DashboardController {
    @FXML
    private Label propertyCount; 
    @FXML
    private Label rentalCount; 
    @FXML
    private Label customerCount;
    @FXML
    private Label landmarkCount;
    @FXML
    private Text fileError;
  
	@FXML
	private ComboBox<String> selectImportType;
	@FXML
	private Button selectFile;
    
    
    private PropertyList pList;
    private CustomerList cList;
    private RentalList rList;
    private LandmarkList lList;
    
    private String selectedFilePathToImport;
    private String selectedFileTypeToImport;
    
    private final String[] importOptions = {"property", "customer", "landmark"};

 
    /**
     * reads the lists and sets the dashboard counts to the lists sizes
     */
    public void initialize() {	      
	      try {
	    	  pList = FileDataHandler.readPropertyList();
		      cList = FileDataHandler.readCustomerList();
		      rList = FileDataHandler.readRentalList();
		      lList = FileDataHandler.readLandmarkList();
		      		      	      
		      propertyCount.setText(Integer.toString(pList.getProperties().size()));
		      customerCount.setText(Integer.toString(cList.getCustomers().size()));
		      rentalCount.setText(Integer.toString(rList.getRentals().size()));
		      landmarkCount.setText(Integer.toString(lList.getLandmarks().size()));
		      
		      //set values for combo box for the import
		      for (String option: importOptions) {
		    	  selectImportType.getItems().add(option);
		      }
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
     * click action handler for comboBox for choosing import type 
     */
    public void selectImportTypeListener() {
    	selectedFileTypeToImport = selectImportType.getValue();
	}
    
    /**
     * action handler for selecting the file to import
     * @param e
     * @throws Exception
     */
    public void filePickerListener(ActionEvent e) throws Exception {		
    	Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
	    FileChooser fileChooser = new FileChooser();
	    File selectedFile = fileChooser.showOpenDialog(stage);
	    
	    if(selectedFile == null) {
	    	fileError.setVisible(true);
	    	fileError.setText("Please pick a file");
	    }
	    else if(!selectedFile.getName().endsWith(".csv")) {
	    	fileError.setVisible(true);
	    	fileError.setText("Please pick the correct file in .csv format");	
		}
	    else {
	    	selectedFilePathToImport = selectedFile.getPath();
	    	selectFile.setText(selectedFile.getName());
	    	fileError.setVisible(false);
	    };
	}
    
    /**
     * checks import type and run the import method based on the type
     * @param e
     * @throws IOException
     */
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
				CreateAndImportData da = new CreateAndImportData(selectedFilePathToImport, selectedFileTypeToImport);
				
				switch(selectedFileTypeToImport) {
					case "property":
						da.importAllProperties();
						FileDataHandler.writeToFile(da.getAllProperties());
						break;
					case "customer":
						da.importAllCustomers();
						FileDataHandler.writeToFile(da.getAllCustomers());
						break;
					case "landmark":
						da.importAllLandmarks();
						FileDataHandler.writeToFile(da.getAllLandmarks());
						break;
				}
				
				alert.setAlertType(AlertType.INFORMATION);
	            alert.setTitle("Successful");
	            alert.setContentText(selectedFileTypeToImport.toUpperCase() +" has been imported successfully");

	            //show alert, wait for user to close and then refresh
	            Optional<ButtonType> result = alert.showAndWait();            
	            if(result.get() != null) {
	            	goToDashboardListener(e);
	            }     
			}
			catch(Exception exception) {
				alert.setAlertType(AlertType.ERROR);
                alert.setTitle("ERROR IMPORTING DATA");
                //to accomodate the different messages that can be thrown by the import methods in the switch statement
                alert.setContentText(exception.getMessage() != null ? exception.getMessage() : "An error occurred");
                alert.show();
			}
		}
	}
 
    
    /**
     * action listeners to switch to different scenes
     * @param e
     * @throws IOException
     */
    public void goToAppStartListener(ActionEvent e) throws IOException {
    	Parent parent = FXMLLoader.load(getClass().getResource("AppStart.fxml")); 
	      
	    Scene scene = new Scene(parent); 
	    scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
	
	    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
	    stage.setTitle("Properties - CSYM025 Lettings"); 
	    stage.setScene(scene);
	    stage.show(); 
    }
    
    public void goToDashboardListener(ActionEvent e) throws IOException {
    	Parent parent = FXMLLoader.load(getClass().getResource("Dashboard.fxml")); 
	      
	    Scene scene = new Scene(parent); 
	    scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
	
	    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
	    stage.setTitle("Dashboard - CSYM025 Lettings"); 
	    stage.setScene(scene);
	    stage.show(); 
    }
     
    public void goToPropertiesListener(ActionEvent e) throws IOException {
    	Parent parent = FXMLLoader.load(getClass().getResource("Properties.fxml")); 
	      
	    Scene scene = new Scene(parent); 
	    scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
	
	    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
	    stage.setTitle("All Properties - CSYM025 Lettings"); 
	    stage.setScene(scene);
	    stage.show(); 
    }
    
    public void goToCustomersListener(ActionEvent e) throws IOException {
    	Parent parent = FXMLLoader.load(getClass().getResource("Customers.fxml")); 
	      
	    Scene scene = new Scene(parent); 
	    scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
	
	    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
	    stage.setTitle("All Customers - CSYM025 Lettings"); 
	    stage.setScene(scene);
	    stage.show(); 
    }
    
    public void goToRentalsListener(ActionEvent e) throws IOException {
    	Parent parent = FXMLLoader.load(getClass().getResource("Rentals.fxml")); 
	      
	    Scene scene = new Scene(parent); 
	    scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
	
	    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
	    stage.setTitle("Rent Properties - CSYM025 Lettings"); 
	    stage.setScene(scene);
	    stage.show(); 
    }
    
    public void goToInvoicesListener(ActionEvent e) throws IOException {
    	Parent parent = FXMLLoader.load(getClass().getResource("TenancyInvoice.fxml")); 
	      
	    Scene scene = new Scene(parent); 
	    scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
	
	    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
	    stage.setTitle("Invoices - CSYM025 Lettings"); 
	    stage.setScene(scene);
	    stage.show(); 
    }
    
    public void goToLandmarksListener(ActionEvent e) throws IOException {
    	Parent parent = FXMLLoader.load(getClass().getResource("Landmarks.fxml")); 
	      
	    Scene scene = new Scene(parent); 
	    scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
	
	    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
	    stage.setTitle("Landmarks - CSYM025 Lettings"); 
	    stage.setScene(scene);
	    stage.show(); 
    }
    
   
}
