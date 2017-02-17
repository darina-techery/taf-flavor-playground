package steps;

import actions.CalculateActions;

public final class CalculateSteps {

    private final CalculateActions calculateActions;

    public CalculateSteps(CalculateActions calculateActions) {
        this.calculateActions = calculateActions;
    }

    public int calculateTwoPlusThree(){
        return calculateActions.twoPlusThree();
    }

    public int calculateTwoAndTwo(){
        return calculateActions.twoAndTwo();
    }
}
