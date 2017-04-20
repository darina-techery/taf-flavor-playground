package tests;

import driver.DriverProvider;
import org.testng.annotations.*;
import steps.DriverSteps;

public abstract class BaseTestWithDriver extends BaseTest {

	private DriverSteps driverSteps = getStepsComponent().driverSteps();

	@BeforeClass
	public void extractAppStrings() {
		String locale = getConfiguration().locale;
		driverSteps.readMainAppStrings(locale);
	}

	@BeforeMethod
	public void resetAndroidApp() {
		log.debug("BeforeTest [START]");
		if (getConfiguration().isAndroid()) {
			driverSteps.resetApplication();
		}
		log.debug("BeforeTest [ END ]");
	}

	@AfterMethod(alwaysRun = true)
	public void resetIOSApp() {
		if (getConfiguration().isIOS()) {
			log.debug("Reset iOS app in @AfterTest [START]");
			driverSteps.resetApplication();
			log.debug("Reset iOS app in @AfterTest [ END ]");
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
