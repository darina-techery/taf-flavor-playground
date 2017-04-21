package tests;

import driver.DriverProvider;
import org.testng.annotations.*;
import steps.DriverSteps;

public abstract class BaseTestWithDriver extends BaseTest {

	private DriverSteps driverSteps = getStepsComponent().driverSteps();

	@BeforeSuite
	public void resetApplicationToDefaultState(){
		driverSteps.resetApplication();
	}

	@BeforeClass
	public void extractAppStrings() {
		String locale = getConfiguration().locale;
		driverSteps.readMainAppStrings(locale);
	}

	@BeforeMethod
	public void resetAndroidApp() {
		if (getConfiguration().isAndroid()) {
			driverSteps.resetApplication();
		}
	}

	@AfterMethod(alwaysRun = true)
	public void resetIOSApp() {
		if (getConfiguration().isIOS()) {
			driverSteps.resetApplication();
		}
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
