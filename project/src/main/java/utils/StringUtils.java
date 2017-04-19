package utils;

import java.time.LocalDateTime;

public class StringUtils {
	public static String getTimestampSuffix(){
		LocalDateTime time = LocalDateTime.now();
		return time.format(java.time.format.DateTimeFormatter.ofPattern("_hh_mm_ss"));
	}
}
