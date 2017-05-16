import base.BaseTestWithDriver;
import data.TestData;
import data.structures.User;
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

	private LoginSteps loginSteps = getStepsComponent().loginSteps();
	private NavigationSteps navigationSteps = getStepsComponent().navigationSteps();
	private DriverSteps driverSteps = getStepsComponent().driverSteps();

	private final Logger log = getLogger();

	@TestData(file = "user_credentials.json", key = "default_user")
	User defaultUser;

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

