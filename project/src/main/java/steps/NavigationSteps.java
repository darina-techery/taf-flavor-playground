package steps;

import actions.NavigationActions;
import data.ui.MenuItem;
import ru.yandex.qatools.allure.annotations.Step;
import utils.annotations.UseActions;

public class NavigationSteps  {
	private final NavigationActions navigationActions;

	@UseActions
	public NavigationSteps(NavigationActions navigationActions) {
		this.navigationActions = navigationActions;
	}

	@Step("Verify that landing page is loaded")
	public void assertLandingPageLoaded() {
		navigationActions.assertLandingPageLoaded();
	}

	@Step("Go to '{0}'")
	public void openSectionFromMenu(MenuItem menuItem) {
		navigationActions.selectMenuItem(menuItem);
	}
}
