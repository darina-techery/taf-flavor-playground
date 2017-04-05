package dagger;

import actions.CalculateActions;
import actions.DriverActions;
import actions.LoginActions;
import actions.definitions.ActionDefinitions;

@Module
public class ActionsModule implements ActionDefinitions {

    private final ActionDefinitions actionDefinitions;

    public ActionsModule(ActionDefinitions actionDefinitions) {
        this.actionDefinitions = actionDefinitions;
    }

    @Override
    @Provides
    public CalculateActions calculateActions() {
        return actionDefinitions.calculateActions();
    }

    @Override
    @Provides
    public LoginActions loginActions() {
        return actionDefinitions.loginActions();
    }

    @Override
    @Provides
    public DriverActions driverActions() {
        return actionDefinitions.driverActions();
    }


}
