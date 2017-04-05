package steps;

import actions.CalculateActions;
import ru.yandex.qatools.allure.annotations.Step;

public final class CalculateSteps {

    private final CalculateActions calculateActions;

    public CalculateSteps(CalculateActions calculateActions) {
        this.calculateActions = calculateActions;
    }

    @Step
    public int calculateTwoPlusThree(){
        return calculateActions.twoPlusThree();
    }

    public int calculateTwoAndTwo(){
        return calculateActions.twoAndTwo();
    }
}
