package actions;

public class IPhoneActionModule implements ActionModule {

    @Override
    public CalculateActions calculateActions() {
        return new IPhoneCalculateActions();
    }
}
