package base;

import data.Configuration;
import driver.DriverProvider;
import org.testng.annotations.*;
import steps.DriverSteps;
import user.UserSessionManager;

public abstract class BaseTestWithRestart extends BaseTest {

	private DriverSteps driverSteps = getStepsComponent().driverSteps();

	@BeforeSuite
	public void resetApplicationToDefaultState(){
		driverSteps.resetApplication();
	}

	@BeforeClass
	public void extractAppStrings() {
		String locale = Configuration.getParameters().locale;
		driverSteps.readMainAppStrings(locale);
	}

	@BeforeMethod
	public void resetAndroidApp() {
		if (Configuration.isAndroid()) {
			driverSteps.resetApplication();
		}
	}

	@AfterMethod(alwaysRun = true)
	public void resetIOSApp() {
		if (Configuration.isIOS()) {
			driverSteps.resetApplication();
		}
	}

	@AfterMethod(alwaysRun = true)
	public void resetActiveUserCredentials() {
		UserSessionManager.resetUserData();
	}

	@AfterClass(alwaysRun = true)
	private void sendTeardownNotificationToDriver() {
		DriverProvider.removeDriverListeners();
	}

	@AfterSuite(alwaysRun = true)
	public void shutdownDriver(){
		driverSteps.shutdownApplication();
	}

}
