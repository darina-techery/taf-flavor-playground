package actions;

import screens.CalculateScreen;

public abstract class CalculateActions extends BaseActions {

    CalculateScreen calculatePage = new CalculateScreen();

    public abstract int twoAndTwo();

    public int twoPlusThree() {
        return calculatePage.two + calculatePage.three;
    }
}
