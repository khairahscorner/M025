package assignment;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

/**
 * interface provides different formatting patterns for other classes
 * @author Airat YUsuff 22831467
 *
 */
public interface DataFormatter {
	DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
	
	DecimalFormat dpFormatter = new DecimalFormat("##.##");
	
	DecimalFormat locationFormatter = new DecimalFormat("##.#####");
}
