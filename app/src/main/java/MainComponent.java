import dagger.Component;
import steps.CalculateSteps;

@Component(modules = {
        ActionModule.class,
        StepModule.class
})
public interface MainComponent {

    CalculateSteps calculateSteps();
}
