package main.steps;

import main.actions.CalculateActions;

public final class CalculateSteps {
    private CalculateActions calculateActions;

    public int calculateTwoPlusThree(){
        return calculateActions.twoPlusThree();
    }

    public int calculateTwoAndTwo(){
        return calculateActions.twoAndTwo();
    }
}
