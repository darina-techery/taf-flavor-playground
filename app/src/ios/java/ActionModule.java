import action.IPhoneCalculateActions;
import actions.CalculateActions;
import dagger.Module;

@Module()
public class ActionModule {

    CalculateActions calculateActions() {
        return new IPhoneCalculateActions();
    }

}
