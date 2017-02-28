import dagger.Component;
import steps.CalculateSteps;

@Component(modules = {
        DaggerActionModule.class,
        StepModule.class
})
public interface MainComponent {

    CalculateSteps calculateSteps();
}
