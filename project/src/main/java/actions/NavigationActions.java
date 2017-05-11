package actions;

import screens.MenuItem;
import screens.NavigationMenu;
import utils.waiters.Waiter;

public abstract class NavigationActions extends BaseActions {

	private NavigationMenu navigationMenu = new NavigationMenu();

	public abstract void assertLandingPageLoaded();

	public abstract void selectMenuItem(MenuItem menuItem);

	public void openMenu() {
		Waiter.click(navigationMenu.menuButton);
	}
}
