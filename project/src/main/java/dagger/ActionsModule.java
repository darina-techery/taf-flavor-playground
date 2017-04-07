package dagger;

import actions.BaseActivityFeedActions;
import actions.BaseDriverActions;
import actions.BaseLoginActions;
import actions.definitions.ActionDefinitions;

@Module
public class ActionsModule implements ActionDefinitions {

    private final ActionDefinitions actionDefinitions;

    public ActionsModule(ActionDefinitions actionDefinitions) {
        this.actionDefinitions = actionDefinitions;
    }

    @Override
    @Provides
    public BaseLoginActions loginActions() {
        return actionDefinitions.loginActions();
    }

    @Override
    @Provides
    public BaseDriverActions driverActions() {
        return actionDefinitions.driverActions();
    }

    @Override
    @Provides
    public BaseActivityFeedActions activityFeedActions() {
        return actionDefinitions.activityFeedActions();
    }


}
