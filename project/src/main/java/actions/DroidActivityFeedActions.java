package actions;

import io.appium.java_client.MobileElement;
import ui.screens.ActivityFeedScreen;
import utils.waiters.Waiter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DroidActivityFeedActions extends ActivityFeedActions {

	public LocalDateTime getPostTimestamp(MobileElement postContainer) {
		String dateTimeStr = new Waiter()
				.getText(ActivityFeedScreen.ANDROID_POST_DATE_TIME_LOCATOR, postContainer)
				.replace(" at ", " ");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd K:mma");
		return LocalDateTime.from(formatter.parse(dateTimeStr));
	}
}
