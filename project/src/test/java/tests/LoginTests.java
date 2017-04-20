package tests;

import driver.DriverProvider;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import steps.LoginSteps;
import steps.NavigationSteps;
import utils.DelayMeter;
import utils.log.LogProvider;

import java.util.concurrent.TimeUnit;

public final class LoginTests extends BaseTestWithDriver implements LogProvider {

	private LoginSteps loginSteps = getStepsComponent().loginSteps();
	private NavigationSteps navigationSteps = getStepsComponent().navigationSteps();

	private final Logger log = getLogger();

	@Test(invocationCount = 5)
	public void loginToApp() {
		loginSteps.login("65663904", "65663904");
		navigationSteps.verifyLandingPageLoaded();
	}

	@AfterClass(alwaysRun = true)
	public void reportDuration() {
		DelayMeter.reportOperationDuration(DriverProvider.INIT_PAGES_OPERATION, TimeUnit.MILLISECONDS);
	}
}
