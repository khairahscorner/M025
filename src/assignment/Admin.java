package assignment;

import java.io.Serializable;

/**
 * This class is used to create an admin with a username and password to login from the start of the application
 * @author Airat Yusuff 22831467
 *
 */
public class Admin implements Serializable, DataFormatter {
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
	
	/**
	 * the default constructor is used in DataHandler class to create new empty admin objects to be populated with data read from file
	 */
	public Admin()  {
		username = "";
		password = "";
	}
	
	public Admin(String u, String p)  {
		username = u;
		password = p;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String n) {
		username = n;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String p) {
		password = p;
	}

	@Override
	public String toString() {
		return "Username: " + username + "\nPassword: " + password;
	}

}
