package dagger;

import steps.CalculateSteps;
import steps.DriverSteps;
import steps.LoginSteps;

@Component(modules = {
        ActionsModule.class,
        StepsModule.class
})
public interface StepsComponent {

    CalculateSteps calculateSteps();

    LoginSteps loginSteps();

    DriverSteps driverSteps();
}
