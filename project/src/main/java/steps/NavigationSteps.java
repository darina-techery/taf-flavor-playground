package steps;

import actions.LoginActions;
import actions.NavigationActions;
import data.ui.MenuItem;
import ru.yandex.qatools.allure.annotations.Step;
import utils.annotations.UseActions;
import utils.waiters.Waiter;

public class NavigationSteps  {
	private final NavigationActions navigationActions;
	private final LoginActions loginActions;

	@UseActions
	public NavigationSteps(NavigationActions navigationActions, LoginActions loginActions) {
		this.navigationActions = navigationActions;
		this.loginActions = loginActions;
	}

	@Step("Verify that landing page is loaded")
	public void assertLandingPageLoaded() {
		navigationActions.assertLandingPageLoaded();
	}

	@Step("Go to '{0}'")
	public void openSectionFromMenu(MenuItem menuItem) {
		navigationActions.selectMenuItem(menuItem);
	}

	@Step("Logout user from app")
	public void logoutUser() {
		openSectionFromMenu(MenuItem.LOGOUT);
		navigationActions.confirmDialog();
		loginActions.waitForScreen();
	}

}
