package actions;

import ui.components.NavigationMenu;
import ui.components.Spinner;

public abstract class NavigationActions extends BaseActions {

	protected NavigationMenu navigationMenu = new NavigationMenu();
	protected Spinner spinner;

	public abstract void assertLandingPageLoaded();

	public abstract void waitSpinnerGone();

	public abstract String getPageTitle();
}
