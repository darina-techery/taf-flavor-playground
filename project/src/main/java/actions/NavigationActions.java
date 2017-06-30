package actions;

import data.ui.MenuItem;
import org.openqa.selenium.By;
import ui.components.NavigationMenu;
import utils.waiters.Waiter;

public abstract class NavigationActions extends BaseUiActions {

	protected NavigationMenu navigationMenu = new NavigationMenu();

	public abstract void assertLandingPageLoaded();

	public abstract String getPageTitle();

	public abstract void openMenu();

	public abstract void selectMenuItem(MenuItem menuItem);

	public void confirmDialog() {
		new Waiter().click(navigationMenu.btnLogout);
	};
}
