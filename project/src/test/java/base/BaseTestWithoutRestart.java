package base;

import data.Configuration;
import driver.DriverProvider;
import org.testng.annotations.*;
import steps.DriverSteps;
import user.UserSessionManager;

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
