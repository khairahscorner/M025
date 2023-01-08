package assignment;

import java.util.*;
import java.io.Serializable;

/**
 * this class is used to create a new list object consisting of multiple
 * property objects from the Property class
 * 
 * @author Airat Yusuff 22831467
 *
 */
public class PropertyList implements Serializable {
	private static final long serialVersionUID = 1L;

	// hash the list and create a property keys list for ease of use in other classes
	private Map<String, Property> pptyList;
	private List<String> pptyKeys;

	public PropertyList() {
		pptyList = new HashMap<String, Property>();
		pptyKeys = new ArrayList<String>();
	}

	public Map<String, Property> getProperties() {
		return pptyList;
	}

	public List<String> getKeys() {
		return pptyKeys;
	}

	public void addProperty(Property p) {
		pptyList.put(p.getPropertyId(), p);
		pptyKeys.add(p.getPropertyId());
	}
	
	public void removeProperty(String pptyId) {
		pptyList.remove(pptyId);	
		pptyKeys.remove(pptyId);
	}

	@Override
	public String toString() {
		String s = "";
		for (int i = 0; i < pptyKeys.size(); i++) {
			s += ("key: " + pptyKeys.get(i) + "ppty type: " + pptyList.get(pptyKeys.get(i)) + "\n");
		}
		return s;
	}
}
