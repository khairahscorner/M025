package assignment;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

public interface DataFormatter {
	DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
	
	DecimalFormat dpFormatter = new DecimalFormat("##.##");
	
	DecimalFormat locationFormatter = new DecimalFormat("##.#####");
}
