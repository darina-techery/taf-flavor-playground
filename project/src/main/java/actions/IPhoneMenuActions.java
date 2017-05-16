package actions;

import data.ui.MenuItem;
import utils.waiters.Waiter;

public class IPhoneMenuActions extends MenuActions {
	@Override
	public void selectMenuItem(MenuItem menuItem) {

	}

	@Override
	public void openMenu() {
		Waiter.click(navigationMenu.menuButton);
	}
}
