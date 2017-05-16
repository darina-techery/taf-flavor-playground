package base;

import data.TestData;
import data.structures.User;
import org.testng.annotations.BeforeMethod;
import steps.LoginSteps;

public abstract class BaseTestAfterLogin extends BaseTestWithDriver {

	private LoginSteps loginSteps = getStepsComponent().loginSteps();

	@TestData(file = "user_credentials.json", key = "default_user")
	User defaultUser;

	@BeforeMethod
	public void loginToApplication() {
		loginSteps.loginIfRequired(defaultUser);
	}

}
