package main.actions;

public final class IPhoneCalculateActions extends CalculateActions {
    @Override
    public int twoAndTwo() {
        System.out.println("Two multiplied by two (iPhone)");
        return calculatePage.two * calculatePage.two;
    }
}
