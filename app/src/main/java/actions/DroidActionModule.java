package actions;

public class DroidActionModule implements ActionModule {

    @Override
    public CalculateActions calculateActions() {
        return new DroidPhoneCalculateActions();
    }
}
