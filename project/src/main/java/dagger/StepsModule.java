package dagger;

import actions.BaseActivityFeedActions;
import actions.BaseDriverActions;
import actions.BaseLoginActions;
import steps.ActivityFeedSteps;
import steps.DriverSteps;
import steps.LoginSteps;

@Module()
class StepsModule {

    @Provides
    public LoginSteps loginSteps(BaseLoginActions loginActions) {
        return new LoginSteps(loginActions);
    }

    @Provides
    public DriverSteps driverSteps(BaseDriverActions driverActions) { return new DriverSteps(driverActions); }

    @Provides
    public ActivityFeedSteps activityFeedSteps(BaseActivityFeedActions activityFeedActions) {
    	return new ActivityFeedSteps(activityFeedActions);
    }

}
