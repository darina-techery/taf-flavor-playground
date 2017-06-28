package actions;

import data.ui.MenuItem;
import ui.components.NavigationMenu;
import utils.exceptions.NotImplementedException;
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
		throw new NotImplementedException();
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
