import dagger.Component;
import steps.CalculateSteps;

@Component(modules = {
        ActionModuleWrapper.class,
        StepModule.class
})
public interface MainComponent {

    CalculateSteps calculateSteps();
}
