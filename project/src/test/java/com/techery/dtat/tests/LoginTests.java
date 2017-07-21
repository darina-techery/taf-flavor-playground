package com.techery.dtat.tests;

import com.techery.dtat.tests.base.BaseTestWithRestart;
import org.testng.annotations.Test;
import com.techery.dtat.steps.DriverSteps;
import com.techery.dtat.steps.LoginSteps;
import com.techery.dtat.steps.NavigationSteps;
import com.techery.dtat.utils.log.LogProvider;

public final class LoginTests extends BaseTestWithRestart implements LogProvider {

	private LoginSteps loginSteps = getStepsComponent().loginSteps();
	private NavigationSteps navigationSteps = getStepsComponent().navigationSteps();
	private DriverSteps driverSteps = getStepsComponent().driverSteps();

	@Test
	public void loginToAppAsFirstTimeUser() {
		driverSteps.resetApplication();
		loginSteps.loginWithValidCredentials(defaultUser);
		navigationSteps.assertLandingPageLoaded();
	}

	@Test
	public void loginToApp() {
		loginSteps.loginIfRequired(defaultUser);
		navigationSteps.assertLandingPageLoaded();
	}

}

