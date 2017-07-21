package com.techery.dtat.utils;

import com.techery.dtat.data.Configuration;
import com.techery.dtat.driver.DriverProvider;

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
		TemporalAccessor temporalAccessor = getDeviceTimeAsTemporalAccessor();
		return ZoneId.from(temporalAccessor);
	}

	public static LocalDateTime getDeviceTime() {
		TemporalAccessor temporalAccessor = getDeviceTimeAsTemporalAccessor();
		return LocalDateTime.from(temporalAccessor);
	}

	private static TemporalAccessor getDeviceTimeAsTemporalAccessor() {
		String dateTime = DriverProvider.get().getDeviceTime();
		DateTimeFormatter formatter = Configuration.isAndroid()
				//example: Mon Jul 17 12:30:03 EEST 2017
				? DateTimeFormatter.ofPattern("ccc MMM dd HH:mm:ss zzz yyyy")
				//example: Thu Jul 13 2017 13:31:56 GMT+0300 (EEST)
				: DateTimeFormatter.ofPattern("ccc MMM dd yyyy HH:mm:ss zzzxxxx (zzz)");
		return formatter.parse(dateTime);
	}
}
