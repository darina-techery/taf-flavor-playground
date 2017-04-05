package dagger;

import actions.CalculateActions;
import actions.DriverActions;
import actions.LoginActions;
import steps.CalculateSteps;
import steps.DriverSteps;
import steps.LoginSteps;

@Module()
class StepsModule {

    @Provides
    public CalculateSteps calculateSteps(CalculateActions calculateActions) {
        return new CalculateSteps(calculateActions);
    }

    @Provides
    public LoginSteps loginSteps(LoginActions loginActions) {
        return new LoginSteps(loginActions);
    }

    @Provides
    public DriverSteps driverSteps(DriverActions driverActions) { return new DriverSteps(driverActions); }

}
