package utils;

import driver.DriverProvider;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public final class DateTimeHelper {
	private DateTimeHelper(){}

	public static String getCurrentZonedDateTime() {
		ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault());
		return zonedDateTime.format(DateTimeFormatter.ISO_INSTANT);
	}

	public static ZoneId getDeviceZoneId() {
		//example: Thu Jul 13 2017 13:31:56 GMT+0300 (EEST)
		String dateTime = DriverProvider.get().getDeviceTime().replaceAll("\\(.*\\)", "").trim();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ccc MMM dd yyyy HH:mm:ss zzzxxxx");
		TemporalAccessor temporalAccessor = formatter.parse(dateTime);
		return ZoneId.from(temporalAccessor);
	}
}
