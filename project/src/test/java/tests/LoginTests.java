package tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import steps.LoginSteps;

public final class LoginTests extends BaseTestWithDriver {

	LoginSteps loginSteps;

	@BeforeMethod
	void initSteps() {
		loginSteps = getStepsComponent().loginSteps();
	}

	@Test(invocationCount = 5)
	public void loginToApp() {
		loginSteps.login("65663904", "65663904");
	}
}
