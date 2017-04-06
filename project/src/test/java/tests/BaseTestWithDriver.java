package tests;

import driver.DriverProvider;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import steps.DriverSteps;

abstract class BaseTestWithDriver extends BaseTest {

	private DriverSteps driverSteps = getStepsComponent().driverSteps();

	private boolean isFirstTest = true;

	@BeforeMethod
	public void resetAndroidApp() {
		log.debug("BeforeTest [START]");
		if (isFirstTest) {
			isFirstTest = false;
		} else {
			if (getConfiguration().isAndroid()) {
				driverSteps.resetApplication();
			}
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
		DriverProvider.receiveTeardownNotification();
	}

	@AfterSuite(alwaysRun = true)
	public void shutdownDriver(){
		driverSteps.shutdownApplication();
	}

}
