package steps;

import actions.ActivityFeedActions;
import utils.annotations.UseActions;

public class ActivityFeedSteps {

	private final ActivityFeedActions activityFeedActions;

	@UseActions
	public ActivityFeedSteps(ActivityFeedActions activityFeedActions) {
		this.activityFeedActions = activityFeedActions;
	}
}
