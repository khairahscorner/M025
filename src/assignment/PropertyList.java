package assignment;

import java.util.*;
import java.io.Serializable;


public class PropertyList implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private HashMap<String, Property> pptyList;
//	private List<Property> pptyList;
	private ArrayList<String> pptyKeys;

	public  PropertyList() {
		pptyList = new HashMap<String, Property>();
//		pptyList = new ArrayList<Property>();
		pptyKeys = new ArrayList<String>();
	}

	public Map<String, Property> getProperties() {
//	public List<Property> getProperties() {
		return pptyList;
	}
	public ArrayList<String> getKeys() {
		return pptyKeys; 
	}

	public void addProperty(Property p) {
		pptyList.put(p.getPropertyId(), p);
//		pptyList.add(p);
		pptyKeys.add(p.getPropertyId());
	}

	public void setPropertyList(HashMap<String, Property> pList) {
//	public void setPropertyList(List<Property> pList) {
		pptyList = pList;
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
