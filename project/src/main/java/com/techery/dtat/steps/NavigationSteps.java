package com.techery.dtat.steps;

import com.techery.dtat.actions.LoginActions;
import com.techery.dtat.actions.NavigationActions;
import com.techery.dtat.data.ui.MenuItem;
import ru.yandex.qatools.allure.annotations.Step;
import com.techery.dtat.utils.annotations.UseActions;

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
