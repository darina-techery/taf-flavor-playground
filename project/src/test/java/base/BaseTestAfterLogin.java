package base;

import data.TestData;
import org.testng.annotations.BeforeMethod;
import steps.LoginSteps;
import user.UserCredentials;

public abstract class BaseTestAfterLogin extends BaseTestWithDriver {

	private LoginSteps loginSteps = getStepsComponent().loginSteps();

	@BeforeMethod
	public void loginToApplication() {
		loginSteps.loginIfRequired(defaultUser);
	}

}
