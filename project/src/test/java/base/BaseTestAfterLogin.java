package base;

import org.testng.annotations.BeforeMethod;
import steps.LoginSteps;

public abstract class BaseTestAfterLogin extends BaseTestWithDriver {

	private LoginSteps loginSteps = getUiStepsComponent().loginSteps();

	@BeforeMethod
	public void loginToApplication() {
		loginSteps.loginIfRequired(defaultUser);
	}

}
