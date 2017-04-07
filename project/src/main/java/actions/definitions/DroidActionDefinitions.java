package actions.definitions;

import actions.*;

public class DroidActionDefinitions implements ActionDefinitions {

    @Override
    public BaseLoginActions loginActions() {
        return new DroidPhoneLoginActions();
    }

    @Override
    public BaseDriverActions driverActions() {
        return new DroidPhoneDriverActions();
    }

	@Override
	public BaseActivityFeedActions activityFeedActions() {
		return new DroidActivityFeedActions();
	}

}
