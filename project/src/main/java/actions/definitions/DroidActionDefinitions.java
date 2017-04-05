package actions.definitions;

import actions.*;

public class DroidActionDefinitions implements ActionDefinitions {

    @Override
    public CalculateActions calculateActions() {
        return new DroidPhoneCalculateActions();
    }

    @Override
    public LoginActions loginActions() {
        return new DroidPhoneLoginActions();
    }

    @Override
    public DriverActions driverActions() {
        return new DroidPhoneDriverActions();
    }

//    @Override
//    public DriverActions driverActions() {
//        return new DriverActions();
//    }
}
