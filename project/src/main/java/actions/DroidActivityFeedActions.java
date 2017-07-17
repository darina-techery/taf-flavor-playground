package actions;

import io.appium.java_client.MobileElement;
import ui.screens.ActivityFeedScreen;
import utils.waiters.Waiter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class DroidActivityFeedActions extends ActivityFeedActions {

	public LocalDateTime getPostTimestamp(MobileElement postContainer) {
		String dateTimeStr = new Waiter()
				.getText(ActivityFeedScreen.ANDROID_POST_DATE_TIME_LOCATOR, postContainer)
				.replace(" at ", " ");
		DateTimeFormatter formatter = new DateTimeFormatterBuilder()
				.appendPattern("MMM d K:mm a")
				.optionalStart()
				.appendPattern(" yyyy")
				.optionalEnd()
				.appendPattern(" K:mm a")
				.toFormatter();
		return LocalDateTime.from(formatter.parse(dateTimeStr));
	}
}
