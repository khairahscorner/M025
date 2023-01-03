package assignment;

import java.util.*;
import java.io.Serializable;

/**
 * this class is used to create a new list object consisting of multiple landmark objects from the Landmark class
 * @author Airat YUsuff 22831467
 *
 */
public class LandmarkList implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<Landmark> landmarks;

	public  LandmarkList() {
		landmarks = new ArrayList<Landmark>();
	}

	public List<Landmark> getLandmarks() {
		return landmarks;
	}

	public void addLandmark(Landmark l) {
		landmarks.add(l);
	}

	public void setLandmarkList(List<Landmark> l) {
		landmarks = l;
	}
	
	@Override
	public String toString() {
		String s = "";
		for(int i = 0; i < landmarks.size(); i++) {
			s += ("landmark: " + landmarks.get(i) + "\n");
		}
		return s;
	}
}
