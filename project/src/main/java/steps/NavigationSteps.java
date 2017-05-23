package steps;

import actions.ActivityFeedActions;
import actions.MenuActions;
import actions.NavigationActions;
import data.ui.MenuItem;
import ru.yandex.qatools.allure.annotations.Step;
import utils.annotations.UseActions;

public class NavigationSteps  {
	private final ActivityFeedActions activityFeedActions;
	private final NavigationActions navigationActions;
	private final MenuActions menuActions;

	@UseActions
	public NavigationSteps(ActivityFeedActions activityFeedActions,
	                       NavigationActions navigationActions,
	                       MenuActions menuActions) {
		this.activityFeedActions = activityFeedActions;
		this.navigationActions = navigationActions;
		this.menuActions = menuActions;
	}

	@Step("Verify that landing page is loaded")
	public void assertLandingPageLoaded() {
		navigationActions.assertLandingPageLoaded();
	}

	@Step("Go to '{0}'")
	public void openSectionFromMenu(MenuItem menuItem) {
		menuActions.selectMenuItem(menuItem);
	}
}
