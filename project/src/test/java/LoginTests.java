import base.BaseTestWithDriver;
import org.testng.annotations.Test;
import steps.DriverSteps;
import steps.LoginSteps;
import steps.NavigationSteps;
import utils.log.LogProvider;

public final class LoginTests extends BaseTestWithDriver implements LogProvider {

	private LoginSteps loginSteps = getUiStepsComponent().loginSteps();
	private NavigationSteps navigationSteps = getUiStepsComponent().navigationSteps();
	private DriverSteps driverSteps = getUiStepsComponent().driverSteps();

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
}

