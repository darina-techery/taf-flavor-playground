package actions;

import io.appium.java_client.android.AndroidDriver;
import screens.MenuItem;
import utils.exceptions.FailedTestException;
import utils.waiters.AnyWait;

public class DroidNavigationActions extends NavigationActions {

	@Override
	public void assertLandingPageLoaded() {
		String expectedActivityName = "MainActivity";
		AnyWait<Void, String> activityWait = new AnyWait<>();
		activityWait.calculate(()->((AndroidDriver) getDriver()).currentActivity());
		activityWait.until(activityName -> activityName.contains(expectedActivityName));
		activityWait.describe("Get current activity name and compare to ["+expectedActivityName+"]");
		activityWait.go();
		if (!activityWait.isSuccess()) {
			throw new FailedTestException("Cannot load "+expectedActivityName+", "+activityWait.result() + " found instead.");
		}
	}

	@Override
	public void selectMenuItem(MenuItem menuItem) {
		openMenu();

	}
}
