package com.techery.dtat.actions;

import io.appium.java_client.MobileElement;
import com.techery.dtat.ui.screens.ActivityFeedScreen;
import com.techery.dtat.utils.waiters.Waiter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalAccessor;

public class DroidActivityFeedActions extends ActivityFeedActions {

	public LocalDateTime getPostTimestamp(MobileElement postContainer) {
		String dateTimeStr = new Waiter()
				.getText(ActivityFeedScreen.ANDROID_POST_DATE_TIME_LOCATOR, postContainer)
				.replace(" at ", " ")
				//workaround: get current year (SOCIAL-1036)
				+ LocalDateTime.now().getYear();
		DateTimeFormatter formatter = new DateTimeFormatterBuilder()
				.appendPattern("MMM d h:mmayyyy").toFormatter();
		TemporalAccessor temporalAccessor = formatter.parse(dateTimeStr);
		return LocalDateTime.from(temporalAccessor);
	}
}


