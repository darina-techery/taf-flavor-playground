package actions;

import data.ui.MenuItem;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import utils.exceptions.FailedTestException;
import utils.exceptions.NotImplementedException;
import utils.waiters.AnyWait;
import utils.waiters.Waiter;

public class DroidTabletNavigationActions extends NavigationActions {

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
	public String getPageTitle() {
		return new Waiter().getText(By.className("android.widget.TextView"), navigationMenu.titleBar);
	}

	@Override
	public void openMenu() {
		throw new NotImplementedException();
	}

	@Override
	public void selectMenuItem(MenuItem menuItem) {
		throw new NotImplementedException();
	}
}
