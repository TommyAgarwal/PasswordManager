package helperClass;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
public class DateFormatConvert {

	public DateFormatConvert() {
	}

	public String convertLocalDateToString(LocalDate localDate) {
		if (localDate != null) {
			String dateTimeString = localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
			return dateTimeString;
		}
		return LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
	}

	public long getExpiredDate(String date) {
		LocalDate todayDate = LocalDate.now();
		LocalDate dateExpired = LocalDate.parse(date);
		if (todayDate.isBefore(dateExpired)) {
			long dateDiff = Math.abs(ChronoUnit.DAYS.between(dateExpired,todayDate));
			return dateDiff;
		}else {
			return 0;
		}



	}

}
