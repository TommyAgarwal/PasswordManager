package helperClass;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class convertToString {

	public convertToString() {}
	
	public String convertLocalDateToString (LocalDate localDate) {
		if (localDate != null) {
			String dateTimeString = localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
	        return dateTimeString;
		}	return "Blank";
	}
	
	
	

}
