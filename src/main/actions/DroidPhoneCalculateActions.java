package main.actions;

public final class DroidPhoneCalculateActions extends CalculateActions {
    @Override
    public int twoAndTwo() {
        System.out.println("Two plus two (droid phone)");
        return calculatePage.two + calculatePage.two;
    }
}
