package actions;

import data.ui.MenuItem;
import org.openqa.selenium.By;
import utils.exceptions.FailedTestException;
import utils.ui.SwipeHelper;
import utils.waiters.Waiter;

public class DroidTabletMenuActions extends MenuActions {
	@Override
	public void selectMenuItem(MenuItem menuItem) {
		openMenu();
		By elementLocator = navigationMenu.getAndroidMenuItemLocator(menuItem);
		SwipeHelper.scrollUp();
		SwipeHelper.scrollDownToElement(elementLocator, navigationMenu.menuDrawer);
		Waiter.click(elementLocator);
	}

	@Override
	public void openMenu() {
		Waiter.click(navigationMenu.menuButton);
		boolean menuShown = Waiter.isDisplayed(navigationMenu.menuDrawer);
		if (!menuShown) {
			throw new FailedTestException("Failed to open menu by clicking Menu button");
		}
	}
}
