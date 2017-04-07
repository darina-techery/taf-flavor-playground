package actions.definitions;

import actions.*;

public class IPhoneActionDefinitions implements ActionDefinitions {

    @Override
    public BaseLoginActions loginActions() {
        return new IPhoneLoginActions();
    }

    @Override
    public BaseDriverActions driverActions() {
        return new IPhoneDriverActions();
    }

    @Override
    public BaseActivityFeedActions activityFeedActions() {
        return new IPhoneActivityFeedActions();
    }

}
