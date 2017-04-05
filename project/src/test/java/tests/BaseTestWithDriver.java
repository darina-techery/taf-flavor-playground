package tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import steps.DriverSteps;

abstract class BaseTestWithDriver extends BaseTest {

	private DriverSteps driverSteps = getStepsComponent().driverSteps();

	private boolean isFirstTest = true;

	@BeforeMethod
	public void resetAndroidApp() {
		System.out.println("BeforeTest started");
		if (isFirstTest) {
			isFirstTest = false;
		} else {
			if (getConfiguration().isAndroid()) {
				driverSteps.resetApplication();
			}
		}
		System.out.println("BeforeTest completed");
	}

	@AfterMethod(alwaysRun = true)
	public void resetIOSApp() {
		if (getConfiguration().isIOS()) {
			System.out.println("Reset iOS app in @AfterTest");
			driverSteps.resetApplication();
			System.out.println("Reset iOS app in @AfterTest: done");

		}
	}

	@AfterSuite
	public void shutdownDriver(){
		driverSteps.shutdownApplication();
	}

}
