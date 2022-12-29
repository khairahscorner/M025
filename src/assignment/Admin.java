package assignment;

import java.io.Serializable;

public class Admin implements Serializable, DateFormatter {
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	
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
