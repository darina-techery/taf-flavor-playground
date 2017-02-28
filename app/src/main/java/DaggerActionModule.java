import actions.ActionModule;
import actions.CalculateActions;
import dagger.Module;
import dagger.Provides;

@Module
public class DaggerActionModule implements ActionModule {

    private final ActionModule actionModule;

    public DaggerActionModule(ActionModule actionModule) {
        this.actionModule = actionModule;
    }

    @Override
    @Provides
    public CalculateActions calculateActions() {
        return actionModule.calculateActions();
    }
}
