package tests;

import actions.DroidPhoneCalculateActions;
import com.sun.tools.javac.util.Assert;
import steps.CalculateSteps;

public final class CalculateTests {

    private CalculateSteps calculateSteps = new CalculateSteps(new DroidPhoneCalculateActions());

    //@Test
    public void testCalculateCommonWay(){
        int actualResult = calculateSteps.calculateTwoPlusThree();
        int expectedResult = 5;
        Assert.check(actualResult == expectedResult, "2 + 3 should be "+expectedResult+", but was "+actualResult);
    }

    //@Test
    public void testCalculateDifferentWay(){
        int actualResult = calculateSteps.calculateTwoAndTwo();
        int expectedResult = 4;
        Assert.check(actualResult == expectedResult, "2 and 2 should be "+expectedResult+", but was "+actualResult);
    }
}
