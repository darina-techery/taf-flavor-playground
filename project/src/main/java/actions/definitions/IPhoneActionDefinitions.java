package actions.definitions;

import actions.*;

public class IPhoneActionDefinitions implements ActionDefinitions {

    @Override
    public CalculateActions calculateActions() {
        return new IPhoneCalculateActions();
    }

    @Override
    public LoginActions loginActions() {
        return new IPhoneLoginActions();
    }

    @Override
    public DriverActions driverActions() {
        return new IPhoneDriverActions();
    }

//    @Override
//    public DriverActions driverActions() {
//        return new DriverActions();
//    }
}
