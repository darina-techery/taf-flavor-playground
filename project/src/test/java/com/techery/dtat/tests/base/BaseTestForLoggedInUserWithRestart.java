package com.techery.dtat.tests.base;

import org.testng.annotations.BeforeMethod;
import com.techery.dtat.steps.LoginSteps;
import com.techery.dtat.utils.annotations.LoginAs;

import java.lang.reflect.Method;

public abstract class BaseTestForLoggedInUserWithRestart extends BaseTestWithRestart {

	private LoginSteps loginSteps = getStepsComponent().loginSteps();

	@BeforeMethod
	public void loginToApplication(Method method) {
		LoginAs loginData = method.getAnnotation(LoginAs.class);
		loginSteps.loginUserBeforeTest(defaultUser, loginData);
	}

}
