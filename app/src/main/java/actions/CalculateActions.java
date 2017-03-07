package actions;

import pages.CalculatePage;

public abstract class CalculateActions {

    protected CalculatePage calculatePage = new CalculatePage();

    public abstract int twoAndTwo();

    public int twoPlusThree() {
        return calculatePage.two + calculatePage.three;
    }
}
