package actions.definitions;

import actions.BaseActivityFeedActions;
import actions.BaseDriverActions;
import actions.BaseLoginActions;

public interface ActionDefinitions {

    BaseLoginActions loginActions();

    BaseDriverActions driverActions();

    BaseActivityFeedActions activityFeedActions();

}
