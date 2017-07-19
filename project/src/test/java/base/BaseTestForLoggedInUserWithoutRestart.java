package base;

import org.testng.annotations.BeforeClass;
import steps.LoginSteps;
import utils.annotations.LoginAs;

public abstract class BaseTestForLoggedInUserWithoutRestart extends BaseTestWithoutRestart {

	private LoginSteps loginSteps = getStepsComponent().loginSteps();

	@BeforeClass
	public void loginToApplication() {
		LoginAs loginData = this.getClass().getAnnotation(LoginAs.class);
		loginSteps.loginUserBeforeTest(defaultUser, loginData);
	}

}
