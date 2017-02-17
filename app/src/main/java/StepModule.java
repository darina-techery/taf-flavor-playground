import dagger.Module;
import dagger.Provides;
import steps.CalculateSteps;

@Module
public class StepModule {

    @Provides
    CalculateSteps calculateSteps() {
        return new CalculateSteps(null);
    }

}
