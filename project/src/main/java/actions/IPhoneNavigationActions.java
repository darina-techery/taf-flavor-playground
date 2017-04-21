package actions;

import screens.NavigationMenu;
import utils.runner.Assert;
import static utils.waiters.Waiter.*;

public class IPhoneNavigationActions extends NavigationActions{

	NavigationMenu navigationMenu = new NavigationMenu();

	@Override
	public void verifyLandingPageLoaded() {
		Assert.assertThat("Main navigation bar is displayed", isDisplayed(navigationMenu.menuBar));
	}
}
