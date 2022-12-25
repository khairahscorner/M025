package assignment;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
        
    
    private PropertyList pList;
    private CustomerList cList;
    private RentalList rList;
    
    
 
    
    public void initialize() throws ClassNotFoundException, IOException {	      
	      pList = DataHandler.readPropertyList();
	      Property.setLastPropertyIndex(pList.getProperties().size());
	      
	      cList = DataHandler.readCustomerList();
	      Customer.setLastIndex(cList.getCustomers().size());
	      
	      rList = DataHandler.readRentalList();
	      Rental.setLastRentalIndex(rList.getRentals().size());
	      
	      //initial things to run here
	      propertyCount.setText(Integer.toString(pList.getProperties().size()));
	      customerCount.setText(Integer.toString(cList.getCustomers().size()));
	      rentalCount.setText(Integer.toString(rList.getRentals().size()));
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
	               getClass().getResource("Invoices.fxml")); 
	      
	      // Build the scene graph.
	      Scene scene = new Scene(parent); 
	      scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
	
	      Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
	      // Display our window, using the scene graph.
	      stage.setTitle("Invoices - CSYM025 Lettings"); 
	      stage.setScene(scene);
	      stage.show(); 
    }


}
