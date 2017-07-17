package actions;

import io.appium.java_client.MobileElement;
import ui.screens.ActivityFeedScreen;
import utils.waiters.Waiter;

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


