package actions;

import ui.components.NavigationMenu;

public abstract class NavigationActions extends BaseActions {

	protected NavigationMenu navigationMenu = new NavigationMenu();

	public abstract void assertLandingPageLoaded();

	public abstract String getPageTitle();
}
