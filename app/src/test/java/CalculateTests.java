import org.junit.Assert;
import org.testng.annotations.Test;
import steps.CalculateSteps;
import static org.hamcrest.CoreMatchers.is;

public final class CalculateTests extends BaseTest {

    private CalculateSteps calculateSteps = getComponent().calculateSteps();

    @Test
    public void testCalculateCommonWay(){
        int actualResult = calculateSteps.calculateTwoPlusThree();
        int expectedResult = 5;
	    Assert.assertThat("Check 2 + 3 result: ", actualResult, is(expectedResult));
    }

    @Test
    public void testCalculateDifferentWay(){
        int actualResult = calculateSteps.calculateTwoAndTwo();
        int expectedResult = 4;
	    Assert.assertThat("Check 2 and 2 result: ", actualResult, is(expectedResult));
    }


}
