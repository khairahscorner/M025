package assignment;

import java.io.Serializable;
import java.util.*;

/**
 * This class is used to create a new list object consisting of a hashmap of
 * usernames and passwords (username as key, password as value
 * 
 * @author Airat YUsuff 22831467
 *
 */
public class AdminList implements Serializable {

	private static final long serialVersionUID = 1L;
	private Map<String, String> admins;

	public AdminList() {
		admins = new HashMap<String, String>();
	}

	public Map<String, String> getAdmins() {
		return admins;
	}

	public void addAdmin(String username, String password) {
		admins.put(username, password);
	}

	@Override
	public String toString() {
		String s = "";
		for (String username : admins.keySet()) {
			s += ("Username: " + username + ", Password: " + admins.get(username) + "\n");
		}
		return s;
	}
}
