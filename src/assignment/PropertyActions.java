package assignment;

import java.util.ArrayList;
import java.util.Comparator;

public class PropertyActions {

	public static PropertyList filterPropertiesByRentalStatus(PropertyList pList, String availability) {
		PropertyList pListToShow = new PropertyList();
		
		if(availability == "Rented") {
			pList.getKeys().forEach(key -> {
				if(pList.getProperties().get(key).getRentalStatus()) {
			    	Property availablePpty = pList.getProperties().get(key);		    	
			    	pListToShow.addProperty(availablePpty);
			    }
			});
			System.out.println(pListToShow.getProperties().size());
		} 
		else if (availability == "Available") {
			pList.getKeys().forEach(key -> {
				if(!pList.getProperties().get(key).getRentalStatus()) {
			    	Property availablePpty = pList.getProperties().get(key);		    	
			    	pListToShow.addProperty(availablePpty);
			    }
			});
			System.out.println(pListToShow.getProperties().size());
		}
		else {
			pListToShow.setPropertyList(pList);
			System.out.println(pListToShow.getProperties().size());
		}
		  
		return pListToShow;
	}
	
	public static PropertyList filterPropertiesByBedrooms(PropertyList pList, int num) {
		PropertyList pListToShow = new PropertyList();
		
		if(num == 3) {
			pList.getKeys().forEach(key -> {
				if(pList.getProperties().get(key).getBedrooms() >= 3) {
			    	Property availablePpty = pList.getProperties().get(key);		    	
			    	pListToShow.addProperty(availablePpty);
			    }
			});
		} else {
			pList.getKeys().forEach(key -> {
				if(pList.getProperties().get(key).getBedrooms() == num) {
			    	Property availablePpty = pList.getProperties().get(key);		    	
			    	pListToShow.addProperty(availablePpty);
			    }
			});
		}
		  
		return pListToShow;
	}
	
	public static PropertyList filterPropertiesByBathrooms(PropertyList pList, int num) {
		PropertyList pListToShow = new PropertyList();
		
		if(num == 3) {
			pList.getKeys().forEach(key -> {
				if(pList.getProperties().get(key).getBathrooms() >= 3) {
			    	Property availablePpty = pList.getProperties().get(key);		    	
			    	pListToShow.addProperty(availablePpty);
			    }
			});
		} else {
			pList.getKeys().forEach(key -> {
				if(pList.getProperties().get(key).getBathrooms() == num) {
			    	Property availablePpty = pList.getProperties().get(key);		    	
			    	pListToShow.addProperty(availablePpty);
			    }
			});
		}
			  
		return pListToShow;
	}
	
	public static PropertyList filterPropertiesByPostcode(PropertyList pList, String code) {
		PropertyList pListToShow = new PropertyList();
		
		pList.getKeys().forEach(key -> {
			if(pList.getProperties().get(key).getPostcode().split(" ")[0].equals(code)) {
		    	Property availablePpty = pList.getProperties().get(key);		    	
		    	pListToShow.addProperty(availablePpty);
		    }
		});
		  
		return pListToShow;
	}
	
	public static PropertyList sortPropertiesByPrice(PropertyList pList, String order) {
		//REFERENCED CODE to convert hashmap to array - START
        ArrayList<Property> propertiesToSort = new ArrayList<Property>(pList.getProperties().values());
        
        //REFERENCED CODE to sort objects in asc./reverse order
        // method reference operator(::) equivalent to lambda expression
        if(order.equals("ASC")) {
    		propertiesToSort.sort(Comparator.comparingDouble(Property::getRentPerMonth));        	
        }
        else {
        	propertiesToSort.sort(Comparator.comparing(Property::getRentPerMonth, (r1, r2) -> r2.compareTo(r1)));
        }
        

		PropertyList pptyListToShow = new PropertyList();
		
		for(Property ppty : propertiesToSort) {
			pptyListToShow.addProperty(ppty);
		};
		
		return pptyListToShow;
		
	}
	public static PropertyList sortPropertiesByDate(PropertyList pList, String order) {
		//REFERENCED CODE to convert hashmap to array - START
        ArrayList<Property> propertiesToSort = new ArrayList<Property>(pList.getProperties().values());
        
        //REFERENCED CODE to sort objects in asc./reverse order
        // method reference operator(::) equivalent to lambda expression
        if(order.equals("ASC")) {
    		propertiesToSort.sort(Comparator.comparing(Property::getDateListed));        	
        }
        else {
        	propertiesToSort.sort(Comparator.comparing(Property::getDateListed, (r1, r2) -> r2.compareTo(r1)));
        }
        

		PropertyList pptyListToShow = new PropertyList();
		
		for(Property ppty : propertiesToSort) {
			pptyListToShow.addProperty(ppty);
		};
		
		return pptyListToShow;
		
	}
}
