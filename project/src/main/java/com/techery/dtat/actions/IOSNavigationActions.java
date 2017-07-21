package com.techery.dtat.actions;

import com.techery.dtat.data.Configuration;
import com.techery.dtat.data.ui.MenuItem;
import org.openqa.selenium.By;
import com.techery.dtat.utils.runner.Assert;
import com.techery.dtat.utils.waiters.Waiter;

import java.util.Arrays;
import java.util.List;

import static com.techery.dtat.data.ui.MenuItem.*;

public class IOSNavigationActions extends NavigationActions {

	private static final List<MenuItem> ITEMS_ON_MENU_BAR = Configuration.isPhone()
			? Arrays.asList(ACTIVITY_FEED, DREAM_TRIPS, NOTIFICATIONS, LOCAL)
			: Arrays.asList(ACTIVITY_FEED, DREAM_TRIPS, NOTIFICATIONS, LOCAL,
			MESSENGER, BOOK_TRAVEL, TRIP_IMAGES, MEMBERSHIP, BUCKET_LIST);

	@Override
	public void assertLandingPageLoaded() {
		Assert.assertThat("Main navigation bar is displayed", new Waiter().isDisplayed(navigationMenu.menuBar));
	}

	@Override
	public String getPageTitle() {
		return new Waiter().getText(By.className("XCUIElementTypeStaticText"), navigationMenu.titleBar);
	}

	@Override
	public void openMenu() {
		new Waiter().click(navigationMenu.menuButton);
	}

	@Override
	public void selectMenuItem(MenuItem menuItem) {
		if (!isMenuItemPresentOnNavigationBar(menuItem)) {
			openMenu();
		}
		By buttonLocator = navigationMenu.getMenuItemLocator(menuItem);
		new Waiter().click(buttonLocator);
	}

	private boolean isMenuItemPresentOnNavigationBar(MenuItem menuItem) {
		return ITEMS_ON_MENU_BAR.contains(menuItem);
	}
}
