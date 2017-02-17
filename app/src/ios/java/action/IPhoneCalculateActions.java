package action;

import actions.CalculateActions;

public final class IPhoneCalculateActions extends CalculateActions {
    @Override
    public int twoAndTwo() {
        System.out.println("Two multiplied by two (iPhone)");
        return calculatePage.two * calculatePage.two;
    }

    @Override
    public int twoPlusThree() {
        System.out.println("IPhoneCalculateActions");
        return super.twoPlusThree();
    }
}
