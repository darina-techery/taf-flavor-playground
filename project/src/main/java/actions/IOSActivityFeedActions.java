package actions;

import io.appium.java_client.MobileElement;
import ui.screens.ActivityFeedScreen;
import utils.waiters.Waiter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class IOSActivityFeedActions extends ActivityFeedActions {

	public LocalDateTime getPostTimestamp(MobileElement postContainer) {
		String date = new Waiter().getText(ActivityFeedScreen.IOS_POST_DATE_LOCATOR, postContainer);
		String time = new Waiter().getText(ActivityFeedScreen.IOS_POST_TIME_LOCATOR, postContainer);

		String dateTimeStr = date + " " + time.replace(" at ", " ");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy K:mm a");
		return LocalDateTime.from(formatter.parse(dateTimeStr));
	}
}
