package actions.modules;

import actions.CalculateActions;
import actions.IPhoneCalculateActions;

public class IPhoneActionModule implements ActionModule {

    @Override
    public CalculateActions calculateActions() {
        return new IPhoneCalculateActions();
    }
}
