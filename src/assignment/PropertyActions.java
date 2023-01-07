package assignment;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * The class contains static methods for filtering and sorting properties in a
 * PropertyList object
 * 
 * @author Airat YUsuff 22831467
 */
public class PropertyActions implements DataFormatter {

	/**
	 * get distance between the property and landmark, and formats to a string
	 * 
	 * @param currPpty         current property
	 * @param selectedLandmark landmaselected landmark
	 * @return distance
	 */
	public static String getDistanceToLandmark(Property currPpty, Landmark selectedLandmark) {
		return "Distance to " + selectedLandmark.getName() + ": "
				+ dpFormatter.format(DistanceCalculator.getDistance(currPpty.getLatitude(), currPpty.getLongitude(),
						selectedLandmark.getLatitude(), selectedLandmark.getLongitude(), "K"))
				+ "km\n";
	}

	/**
	 * gets landmarks with postcode corresponding to property postcode, and formats
	 * to a string
	 * 
	 * @param currPpty     current property
	 * @param allLandmarks landmarks
	 * @return string with details of all matched
	 */
	public static String getClosestLandmarksDistance(Property currPpty, List<Landmark> allLandmarks) {
		String pptyPostCode = currPpty.getPostcode().split(" ")[0];
		String str = "\n\n-- Closest Landmarks (" + pptyPostCode + ") --\n\n";

		for (int i = 0; i < Landmark.getLastIndex(); i++) {
			String landmarkCode = allLandmarks.get(i).getPostcode().split(" ")[0];
			if (pptyPostCode.equals(landmarkCode)) {
				str += allLandmarks.get(i).getName() + ": "
						+ dpFormatter
								.format(DistanceCalculator.getDistance(currPpty.getLatitude(), currPpty.getLongitude(),
										allLandmarks.get(i).getLatitude(), allLandmarks.get(i).getLongitude(), "K"))
						+ "km\n";
			}
			;
		}
		return str;
	}

	/**
	 * filter the properties list for all properties that match the availability in
	 * the parameter
	 * 
	 * @param pList        properties list
	 * @param availability rental status to filter for
	 * @return filtered properties list
	 */
	public static PropertyList filterPropertiesByRentalStatus(PropertyList pList, String availability) {
		PropertyList pListToShow = new PropertyList();

		if (availability == "Rented") {
			pList.getKeys().forEach(key -> {
				if (pList.getProperties().get(key).getRentalStatus()) {
					Property availablePpty = pList.getProperties().get(key);
					pListToShow.addProperty(availablePpty);
				}
			});
			return pListToShow;
		} else if (availability == "Available") {
			pList.getKeys().forEach(key -> {
				if (!pList.getProperties().get(key).getRentalStatus()) {
					Property availablePpty = pList.getProperties().get(key);
					pListToShow.addProperty(availablePpty);
				}
			});
			return pListToShow;
		} else {
			return pList;
		}
	}

	/**
	 * filter the properties list for all properties that match the number of
	 * bedrooms in the parameter
	 * 
	 * @param pList properties list
	 * @param num   number of bedrooms
	 * @return filtered list
	 */
	public static PropertyList filterPropertiesByBedrooms(PropertyList pList, int num) {
		PropertyList pListToShow = new PropertyList();

		if (num == 3) {
			pList.getKeys().forEach(key -> {
				if (pList.getProperties().get(key).getBedrooms() >= 3) {
					Property availablePpty = pList.getProperties().get(key);
					pListToShow.addProperty(availablePpty);
				}
			});
		} else {
			pList.getKeys().forEach(key -> {
				if (pList.getProperties().get(key).getBedrooms() == num) {
					Property availablePpty = pList.getProperties().get(key);
					pListToShow.addProperty(availablePpty);
				}
			});
		}

		return pListToShow;
	}

	/**
	 * filter the properties list for all properties that match the number of
	 * bathrooms in the parameter
	 * 
	 * @param pList properties list
	 * @param num   number of bathrooms
	 * @return filtered list
	 */
	public static PropertyList filterPropertiesByBathrooms(PropertyList pList, int num) {
		PropertyList pListToShow = new PropertyList();

		if (num == 3) {
			pList.getKeys().forEach(key -> {
				if (pList.getProperties().get(key).getBathrooms() >= 3) {
					Property availablePpty = pList.getProperties().get(key);
					pListToShow.addProperty(availablePpty);
				}
			});
		} else {
			pList.getKeys().forEach(key -> {
				if (pList.getProperties().get(key).getBathrooms() == num) {
					Property availablePpty = pList.getProperties().get(key);
					pListToShow.addProperty(availablePpty);
				}
			});
		}

		return pListToShow;
	}

	/**
	 * filter the properties list for all properties that their type or furnishing
	 * status matches the string in the parameter
	 * 
	 * @param pList     properties list
	 * @param searchStr search parameter
	 * @return filtered list
	 */
	public static PropertyList filterPropertiesBySearch(PropertyList pList, String searchStr) {
		PropertyList pListToShow = new PropertyList();

		pList.getKeys().forEach(key -> {
			// get lowercase of both strings for comparison
			if (pList.getProperties().get(key).getType().toLowerCase().contains(searchStr.toLowerCase()) || pList
					.getProperties().get(key).getFurnishedStatus().toLowerCase().contains(searchStr.toLowerCase())) {
				Property availablePpty = pList.getProperties().get(key);
				pListToShow.addProperty(availablePpty);
			}
		});

		return pListToShow;
	}

	/**
	 * filter the properties list for all properties that the first half of their
	 * postcode matches the string in the parameter
	 * 
	 * @param pList properties list
	 * @param code  postcode string
	 * @return filtered list
	 */
	public static PropertyList filterPropertiesByPostcode(PropertyList pList, String code) {
		PropertyList pListToShow = new PropertyList();

		pList.getKeys().forEach(key -> {
			if (pList.getProperties().get(key).getPostcode().split(" ")[0].equals(code)) {
				Property availablePpty = pList.getProperties().get(key);
				pListToShow.addProperty(availablePpty);
			}
		});

		return pListToShow;
	}

	/**
	 * sort properties list in ascending or descending order of pricing
	 * 
	 * @param pList properties list
	 * @param order order in which to sort the list
	 * @return sorted properties list
	 */
	public static PropertyList sortPropertiesByPrice(PropertyList pList, String order) {
		ArrayList<Property> propertiesToSort = new ArrayList<Property>(pList.getProperties().values());

		// method reference operator(::) is equivalent to lambda expression
		// <-***** Kevy(2021) [1] - START
		if (order.equals("ASC")) {
			propertiesToSort.sort(Comparator.comparingDouble(Property::getRentPerMonth));
		}
		// <-***** Kevy(2021) [1] - END

		// <-***** BenchResources.Net (2021) [2] - START
		else {
			propertiesToSort.sort(Comparator.comparing(Property::getRentPerMonth, (r1, r2) -> r2.compareTo(r1)));
		}
		// ->***** BenchResources.Net (2021) [2] - END

		PropertyList pptyListToShow = new PropertyList();

		for (Property ppty : propertiesToSort) {
			pptyListToShow.addProperty(ppty);
		}

		return pptyListToShow;

	}

	/**
	 * sort properties by date by creating an arraylist from the properties hashmap
	 * to iterate through, and comparing property dates
	 * 
	 * @param pList properties list
	 * @param order order in which to sort the list
	 * @return sorted properties list
	 */
	public static PropertyList sortPropertiesByDate(PropertyList pList, String order) {
		ArrayList<Property> propertiesToSort = new ArrayList<Property>(pList.getProperties().values());

		// <-***** Kevy(2021) [1] - START
		if (order.equals("ASC")) {
			propertiesToSort.sort(Comparator.comparing(Property::getDateListed));
		}
		// <-***** Kevy(2021) [1] - END

		// <-***** BenchResources.Net (2021) [2] - START
		else {
			propertiesToSort.sort(Comparator.comparing(Property::getDateListed, (r1, r2) -> r2.compareTo(r1)));
		}
		// ->***** BenchResources.Net (2021) [2] - END

		PropertyList pptyListToShow = new PropertyList();

		for (Property ppty : propertiesToSort) {
			pptyListToShow.addProperty(ppty);
		}

		return pptyListToShow;

	}
}

// REFERENCES
//1. Kevy, F. (2021) How to sort List of objects by some property. Stack Overflow [online]. Available from: https://stackoverflow.com/questions/5805602/how-to-sort-list-of-objects-by-some-property#:~:text=Comparator.comparing() [Accessed 28/12/22].
//2. BenchResources.Net (2021) Comparator.comparing() method for custom/reverse sorting. BenchResources.Net [online]. Available from: https://www.benchresources.net/java-8-comparator-comparing-method-for-custom-reverse-sorting/ [Accessed 28/12/22].
