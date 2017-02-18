import actions.CalculateActions;
import dagger.Module;
import dagger.Provides;
import steps.CalculateSteps;

@Module
public class StepModule {

    @Provides
    CalculateSteps calculateSteps(CalculateActions calculateActions) {
        return new CalculateSteps(calculateActions);
    }

}
