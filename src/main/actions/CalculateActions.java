package main.actions;

import main.pages.CalculatePage;

public abstract class CalculateActions {
    protected CalculatePage calculatePage;
    public abstract int twoAndTwo();
    public int twoPlusThree(){
        return calculatePage.two + calculatePage.three;
    }
}
