package com.techery.dtat.actions;

import com.techery.dtat.data.Configuration;
import com.techery.dtat.data.ui.MenuItem;
import com.techery.dtat.utils.exceptions.FailedTestException;
import com.techery.dtat.utils.runner.Assert;
import com.techery.dtat.utils.ui.SwipeHelper;
import com.techery.dtat.utils.waiters.Waiter;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;

import java.time.Duration;

public class DroidNavigationActions extends NavigationActions {

	@Override
	public void assertLandingPageLoaded() {
		boolean isMenuButtonDisplayed = new Waiter(Duration.ofSeconds(10))
				.waitDisplayed(navigationMenu.menuButton);
		Assert.assertThat("Menu button should be visible now as an attribute of landing page", isMenuButtonDisplayed);
//		String expectedActivityName = "MainActivity";
//		AnyWait<Void, String> activityWait = new AnyWait<>();
//		activityWait.duration(Duration.ofMinutes(1));
//		activityWait.calculate(()->((AndroidDriver) getDriver()).currentActivity());
//		activityWait.until(activityName -> activityName.contains(expectedActivityName));
//		activityWait.describe("Get current activity name and compare to ["+expectedActivityName+"]");
//		activityWait.go();
//		if (!activityWait.isSuccess()) {
//			throw new FailedTestException("Cannot load "+expectedActivityName+", "+activityWait.result() + " found instead.");
//		}
	}

	@Override
	public String getPageTitle() {
		return new Waiter().getText(By.className("android.widget.TextView"), navigationMenu.titleBar);
	}

	@Override
	public void openMenu() {
		if (Configuration.isPhone()) {
			new Waiter().click(navigationMenu.menuButton);
			boolean menuShown = new Waiter().isDisplayed(navigationMenu.menuDrawer);
			if (!menuShown) {
				throw new FailedTestException("Failed to open menu by clicking Menu button");
			}
		} else {
			LogManager.getLogger().info("Menu on tablet is present on screen by default.");
		}
	}

	@Override
	public void selectMenuItem(MenuItem menuItem) {
		openMenu();
		By elementLocator = navigationMenu.getMenuItemLocator(menuItem);
		SwipeHelper.scrollUpInContainer(navigationMenu.menuDrawer);
		SwipeHelper.scrollDownToElement(elementLocator, navigationMenu.menuDrawer);
		new Waiter().click(elementLocator, navigationMenu.menuDrawer);
	}
}
