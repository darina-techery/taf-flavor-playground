package base;

import org.testng.annotations.BeforeMethod;
import steps.LoginSteps;
import user.UserCredentials;
import user.UserCredentialsProvider;
import user.UserSessionManager;
import utils.annotations.LoginAs;

import java.lang.reflect.Method;

public abstract class BaseTestAfterLogin extends BaseTestWithDriver {

	private LoginSteps loginSteps = getStepsComponent().loginSteps();

	@BeforeMethod
	public void loginToApplication(Method method) {
		LoginAs loginData = method.getAnnotation(LoginAs.class);
		if (loginData != null) {
			UserCredentials userCredentials = new UserCredentialsProvider().getUserByRole(loginData.role());
			loginSteps.loginEvenIfLoggedId(userCredentials);
			UserSessionManager.setActiveUser(userCredentials);
		} else {
			loginSteps.loginIfRequired(defaultUser);
			UserSessionManager.setActiveUser(defaultUser);
		}
	}

}
