package com.techery.dtat.tests.base;

import com.techery.dtat.data.Configuration;
import com.techery.dtat.driver.DriverProvider;
import org.testng.annotations.*;
import com.techery.dtat.steps.DriverSteps;
import com.techery.dtat.user.UserSessionManager;

public abstract class BaseTestWithoutRestart extends BaseTest {

	private DriverSteps driverSteps = getStepsComponent().driverSteps();

	@BeforeClass
	public void resetApplicationToDefaultState(){
		driverSteps.resetApplication();
	}

	@BeforeClass
	public void extractAppStrings() {
		String locale = Configuration.getParameters().locale;
		driverSteps.readMainAppStrings(locale);
	}

	@AfterClass(alwaysRun = true)
	private void sendTeardownNotificationToDriver() {
		DriverProvider.removeDriverListeners();
	}

	@AfterClass(alwaysRun = true)
	public void resetActiveUserCredentials() {
		UserSessionManager.resetUserData();
	}

	@AfterSuite(alwaysRun = true)
	public void shutdownDriver(){
		driverSteps.shutdownApplication();
	}

}
