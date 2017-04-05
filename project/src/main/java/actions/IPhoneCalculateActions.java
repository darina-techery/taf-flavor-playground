package actions;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public final class IPhoneCalculateActions extends CalculateActions {

    @Override
    public int twoAndTwo() {
        System.out.println("Two multiplied by two (iPhone)");
        return calculatePage.two * calculatePage.two;
    }

    @Override
    public int twoPlusThree() {
        System.out.println("Two plus three (iPhone)");
        return super.twoPlusThree();
    }
}
