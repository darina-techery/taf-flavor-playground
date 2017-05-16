package actions;

import ui.components.NavigationMenu;
import utils.exceptions.NotImplementedException;
import utils.runner.Assert;

import static utils.waiters.Waiter.isDisplayed;

public class IPhoneNavigationActions extends NavigationActions{

	NavigationMenu navigationMenu = new NavigationMenu();

	@Override
	public void assertLandingPageLoaded() {
		Assert.assertThat("Main navigation bar is displayed", isDisplayed(navigationMenu.menuBar));
	}

	@Override
	public void waitSpinnerGone() {

	}

	@Override
	public String getPageTitle() {
		throw new NotImplementedException();
	}

//	@Override
//	public void selectMenuItem(MenuItem menuItem) {
//		throw new NotImplementedException();
//	}
}
