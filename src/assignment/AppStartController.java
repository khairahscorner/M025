package assignment;

import java.io.IOException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * the class controls the home page of the application where the user logs in.
 * Includes user input validation
 * 
 * @author Airat YUsuff 22831467
 *
 */
public class AppStartController {

	@FXML
	private TextField usernameField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private Button startBtn;
	@FXML
	private Label titleHeader;
	@FXML
	private Button extraBtn;
	@FXML
	private Label extraText;
	@FXML
	private Label extraSignupNote;

	private AdminList adminUsers;

	/**
	 * get admin stored in file
	 */
	public void initialize() {
		try {
			adminUsers = FileDataHandler.readAdmins();
			System.out.println("Admins:\n" + adminUsers);
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("An error has occured in the app");
			alert.show();
			System.out.println(e.toString());
		}
	}

	/**
	 * if in login view, calls login method, else call signup
	 * 
	 * @param e
	 * @throws IOException
	 */
	public void startBtnListener(ActionEvent e) throws IOException {
		// if currently login, perform login action
		if (startBtn.getText().toLowerCase().equals("login")) {
			loginHandler(e);
		} else {
			signupHandler(e);
		}
	}

	/**
	 * update views based on whether to show login or signup
	 */
	public void switchActions() {
		// if view is currently login, switch to signup view
		if (startBtn.getText().toLowerCase().equals("login")) {
			titleHeader.setText("Sign up or update your password");
			startBtn.setText("Signup/Update");
			extraText.setText("Already an admin/remember password?:");
			extraBtn.setText("Go to login");
			extraSignupNote.setVisible(true);
		} else {
			titleHeader.setText("Login with your credentials");
			startBtn.setText("Login");
			extraText.setText("New Admin/Forgot password?:");
			extraBtn.setText("Register/Update");
			extraSignupNote.setVisible(false);
		}
		// always reset form
		usernameField.setText("");
		passwordField.setText("");
	}

	/**
	 * handles click action of the login button, checks for input validation and
	 * logs into the dashboard
	 * 
	 * @param e
	 * @throws IOException
	 */
	private void loginHandler(ActionEvent e) throws IOException {
		Alert alert = new Alert(AlertType.NONE);

		if (adminUsers.getAdmins().size() == 0) {
			alert.setAlertType(AlertType.ERROR);
			alert.setTitle("Error logging in");
			alert.setContentText("There are no admins registered for this app");
			alert.show();
		}

		else if (usernameField.getText() == "" || passwordField.getText() == "") {
			alert.setAlertType(AlertType.ERROR);
			alert.setTitle("Error logging in");
			alert.setContentText("Please fill in all details correctly");
			alert.show();
		}
		// if username not in admins list
		else if (!checkIfAdminExists(usernameField.getText())) {
			alert.setAlertType(AlertType.ERROR);
			alert.setTitle("Error logging in");
			alert.setContentText("Your username is not registered as admin");
			alert.show();
		}
		// if user exists but password entered does not match
		else if (!adminUsers.getAdmins().get(usernameField.getText()).equals(passwordField.getText())) {
			alert.setAlertType(AlertType.ERROR);
			alert.setTitle("Error logging in");
			alert.setContentText("Your password is incorrect, please try again");
			alert.show();
		} else {
			Parent parent = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
			Scene scene = new Scene(parent);

			Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			String css = getClass().getResource("styles.css").toExternalForm();
			scene.getStylesheets().add(css);

			stage.setTitle("Dashboard - CSYM025 Lettings");
			stage.setScene(scene);
			stage.show();
		}
	}

	/**
	 * check whether the string exists in the adminUsers hashmap
	 * 
	 * @param username String to use as key
	 * @return boolean
	 */
	private boolean checkIfAdminExists(String username) {
		if (adminUsers.getAdmins().containsKey(username)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * handles click action of the signup/update button, checks for input validation
	 * and either adds a new admin to the list or update the password(value) of
	 * existing admin(key)
	 * 
	 * @param e
	 * @throws IOException
	 */
	private void signupHandler(ActionEvent e) throws IOException {
		Alert alert = new Alert(AlertType.NONE);

		if (usernameField.getText() == "" || passwordField.getText() == "") {
			alert.setAlertType(AlertType.ERROR);
			alert.setTitle("Error signing up");
			alert.setContentText("Please fill in all details correctly");
			alert.show();
		} else if (usernameField.getText().length() < 3 || passwordField.getText().length() < 3) {
			alert.setAlertType(AlertType.ERROR);
			alert.setTitle("Error signing up");
			alert.setContentText("Your username and password must be at least three characters");
			alert.show();
		} else {
			boolean existingAdmin = checkIfAdminExists(usernameField.getText());
			// add new admin and write updated list to file
			adminUsers.addAdmin(usernameField.getText(), passwordField.getText());
			FileDataHandler.writeToFile(adminUsers);

			System.out.println(adminUsers);
			
			if (existingAdmin) {
				alert.setContentText("Password updated. You can login now");
			} else {
				alert.setContentText("New Admin registered. You can login now");
			}

			alert.setAlertType(AlertType.INFORMATION);
			alert.setTitle("Successful");

			// show alert, wait for user to close and then refresh
			Optional<ButtonType> result = alert.showAndWait();

			if (result.get() != null) {
				switchActions();
			}
		}
	}

}
