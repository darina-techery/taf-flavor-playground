package actions;

import data.ui.MenuItem;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import utils.exceptions.FailedTestException;
import utils.ui.SwipeHelper;
import utils.waiters.AnyWait;
import utils.waiters.Waiter;

import java.time.Duration;

public class DroidNavigationActions extends NavigationActions {

	@Override
	public void assertLandingPageLoaded() {
		String expectedActivityName = "MainActivity";
		AnyWait<Void, String> activityWait = new AnyWait<>();
		activityWait.duration(Duration.ofMinutes(1));
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
		return Waiter.getText(By.className("android.widget.TextView"), navigationMenu.titleBar);
	}

	@Override
	public void openMenu() {
		Waiter.click(navigationMenu.menuButton);
		boolean menuShown = Waiter.isDisplayed(navigationMenu.menuDrawer);
		if (!menuShown) {
			throw new FailedTestException("Failed to open menu by clicking Menu button");
		}
	}

	@Override
	public void selectMenuItem(MenuItem menuItem) {
		openMenu();
		By elementLocator = navigationMenu.getMenuItemLocator(menuItem);
		SwipeHelper.scrollUp();
		SwipeHelper.scrollDownToElement(elementLocator, navigationMenu.menuDrawer);
		Waiter.click(elementLocator);
	}
}
