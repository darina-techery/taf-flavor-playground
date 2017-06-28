package actions;

import data.ui.MenuItem;
import org.openqa.selenium.By;
import ui.components.NavigationMenu;
import utils.exceptions.NotImplementedException;
import utils.runner.Assert;
import utils.waiters.Waiter;

public class IPhoneNavigationActions extends NavigationActions {

	NavigationMenu navigationMenu = new NavigationMenu();

	@Override
	public void assertLandingPageLoaded() {
		Assert.assertThat("Main navigation bar is displayed", new Waiter().isDisplayed(navigationMenu.menuBar));
	}

	@Override
	public String getPageTitle() {
		throw new NotImplementedException();
	}

	@Override
	public void openMenu() {
		new Waiter().click(navigationMenu.menuButton);
	}

	@Override
	public void selectMenuItem(MenuItem menuItem) {
		switch (menuItem) {
			case ACTIVITY_FEED:
			case DREAM_TRIPS:
			case LOCAL:
				//items are available on menu bar, we don't open menu
				break;
			default:
				openMenu();
		}
		By buttonLocator = navigationMenu.getMenuItemLocator(menuItem);
		new Waiter().click(buttonLocator);
	}
}
