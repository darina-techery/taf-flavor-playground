package actions;

import screens.NavigationMenu;
import utils.runner.Assert;
import utils.waiters.Waiter;

public class IPhoneNavigationActions extends NavigationActions implements Waiter {

	NavigationMenu navigationMenu = new NavigationMenu();

	@Override
	public void verifyLandingPageLoaded() {
		Assert.assertThat("Main navigation bar is displayed", isDisplayed(navigationMenu.menuBar));
	}
}
