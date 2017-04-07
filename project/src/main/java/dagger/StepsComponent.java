package dagger;

import steps.ActivityFeedSteps;
import steps.DriverSteps;
import steps.LoginSteps;

@Component(modules = {
        ActionsModule.class,
        StepsModule.class
})
public interface StepsComponent {

    LoginSteps loginSteps();

    DriverSteps driverSteps();

    ActivityFeedSteps activityFeedSteps();
}
