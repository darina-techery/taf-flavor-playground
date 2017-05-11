package actions;

import screens.MenuItem;
import screens.NavigationMenu;
import utils.exceptions.NotImplementedException;
import utils.runner.Assert;
import static utils.waiters.Waiter.*;

public class IPhoneNavigationActions extends NavigationActions{

	NavigationMenu navigationMenu = new NavigationMenu();

	@Override
	public void assertLandingPageLoaded() {
		Assert.assertThat("Main navigation bar is displayed", isDisplayed(navigationMenu.menuBar));
	}

	@Override
	public void selectMenuItem(MenuItem menuItem) {
		throw new NotImplementedException();
	}
}
