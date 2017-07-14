package actions;

import io.appium.java_client.MobileElement;
import ui.screens.ActivityFeedScreen;
import utils.waiters.Waiter;

public class DroidActivityFeedActions extends ActivityFeedActions {

	public String getPostTimeStamp(MobileElement postContainer) {
		String dateTime = new Waiter().getText(ActivityFeedScreen.ANDROID_POST_DATE_TIME_LOCATOR, postContainer);
		return dateTime;
	}
}
