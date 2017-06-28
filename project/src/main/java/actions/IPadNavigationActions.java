package actions;

import data.ui.MenuItem;
import org.openqa.selenium.By;
import ui.components.NavigationMenu;
import utils.runner.Assert;
import utils.waiters.Waiter;

public class IPadNavigationActions extends NavigationActions {

	NavigationMenu navigationMenu = new NavigationMenu();

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
		switch (menuItem) {
			case ACTIVITY_FEED:
			case NOTIFICATIONS:
			case DREAM_TRIPS:
			case LOCAL:
			case MESSENGER:
			case BOOK_TRAVEL:
			case TRIP_IMAGES:
			case MEMBERSHIP:
			case BUCKET_LIST:
				//items are available on menu bar, we don't open menu
				break;
			default:
				openMenu();
		}
		By buttonLocator = navigationMenu.getMenuItemLocator(menuItem);
		new Waiter().click(buttonLocator);
	}
}
