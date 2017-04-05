package actions.definitions;

import actions.CalculateActions;
import actions.DriverActions;
import actions.LoginActions;

public interface ActionDefinitions {

    CalculateActions calculateActions();

    LoginActions loginActions();

    DriverActions driverActions();
}
