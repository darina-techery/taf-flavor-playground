package actions.modules;

import actions.CalculateActions;
import actions.DroidPhoneCalculateActions;

public class DroidActionModule implements ActionModule {

    @Override
    public CalculateActions calculateActions() {
        return new DroidPhoneCalculateActions();
    }
}
