package actions;

import data.ui.MenuItem;
import ui.components.NavigationMenu;

public abstract class MenuActions extends BaseUiActions {
	protected NavigationMenu navigationMenu = new NavigationMenu();

	public abstract void selectMenuItem(MenuItem menuItem);

	public abstract void openMenu();
}
