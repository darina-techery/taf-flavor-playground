import action.DroidPhoneCalculateActions;
import actions.CalculateActions;
import dagger.Module;

@Module()
public class ActionModule {

    CalculateActions calculateActions() {

        return new DroidPhoneCalculateActions();
    }
}
