package assignment;

import java.util.*;
import java.io.Serializable;


public class PropertyList implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Map<String, Property> pptyList;
	private List<String> pptyKeys;

	public  PropertyList() {
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

	public void setPropertyList(PropertyList pList) {
		pptyList = pList.getProperties();
		pptyKeys = pList.getKeys();
	}
	
	@Override
	public String toString() {
		String s = "";
		for(int i = 0; i < pptyKeys.size(); i++) {
			s += ("key: " + pptyKeys.get(i) + "ppty type: " + pptyList.get(pptyKeys.get(i)) + "\n");
		}
		return s;
	}
}
