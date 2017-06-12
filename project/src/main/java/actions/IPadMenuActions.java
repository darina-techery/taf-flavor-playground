package actions;

import data.ui.MenuItem;
import utils.exceptions.NotImplementedException;
import utils.waiters.Waiter;

public class IPadMenuActions extends MenuActions {
	@Override
	public void selectMenuItem(MenuItem menuItem) {
		throw new NotImplementedException();
	}

	@Override
	public void openMenu() {
		Waiter.click(navigationMenu.menuButton);
	}
}
