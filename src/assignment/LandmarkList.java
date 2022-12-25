package assignment;

import java.util.*;
import java.io.Serializable;


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
