package base;

import org.testng.annotations.BeforeMethod;
import steps.LoginSteps;
import utils.annotations.LoginAs;

import java.lang.reflect.Method;

public abstract class BaseTestForLoggedInUserWithRestart extends BaseTestWithRestart {

	private LoginSteps loginSteps = getStepsComponent().loginSteps();

	@BeforeMethod
	public void loginToApplication(Method method) {
		LoginAs loginData = method.getAnnotation(LoginAs.class);
		loginSteps.loginUserBeforeTest(defaultUser, loginData);
	}

}
