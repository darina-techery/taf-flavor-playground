import actions.ActionModule;
import actions.CalculateActions;
import dagger.Module;
import dagger.Provides;

@Module
public class ActionModuleWrapper implements ActionModule {

    private final ActionModule actionModule;

    public ActionModuleWrapper(ActionModule actionModule) {
        this.actionModule = actionModule;
    }

    @Override
    @Provides
    public CalculateActions calculateActions() {
        return actionModule.calculateActions();
    }
}
