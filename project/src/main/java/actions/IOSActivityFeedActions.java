package actions;

import io.appium.java_client.MobileElement;
import ui.screens.ActivityFeedScreen;
import utils.waiters.Waiter;

public class IOSActivityFeedActions extends ActivityFeedActions {

	public String getPostTimeStamp(MobileElement postContainer) {
		String date = new Waiter().getText(ActivityFeedScreen.IOS_POST_DATE_LOCATOR, postContainer);
		String time = new Waiter().getText(ActivityFeedScreen.IOS_POST_TIME_LOCATOR, postContainer);
		return date + "  " + time;
	}
}
