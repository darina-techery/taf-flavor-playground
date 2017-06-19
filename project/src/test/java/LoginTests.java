import base.BaseTestWithDriver;
import driver.DriverProvider;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import steps.DriverSteps;
import steps.LoginSteps;
import steps.NavigationSteps;
import utils.DelayMeter;
import utils.log.LogProvider;

import java.util.concurrent.TimeUnit;

public final class LoginTests extends BaseTestWithDriver implements LogProvider {

	private LoginSteps loginSteps = getUiStepsComponent().loginSteps();
	private NavigationSteps navigationSteps = getUiStepsComponent().navigationSteps();
	private DriverSteps driverSteps = getUiStepsComponent().driverSteps();

	private final Logger log = getLogger();

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

	@AfterClass(alwaysRun = true)
	public void reportDuration() {
		DelayMeter.reportOperationDuration(DriverProvider.INIT_PAGES_OPERATION, TimeUnit.MILLISECONDS);
	}
}

