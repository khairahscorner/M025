package assignment;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

public interface DateFormatter {
	DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
	
	DecimalFormat dpFormatter = new DecimalFormat("##.##");
}
