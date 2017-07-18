import base.BaseTestWithRestart;
import org.testng.annotations.Test;
import steps.DriverSteps;
import steps.LoginSteps;
import steps.NavigationSteps;
import utils.log.LogProvider;

public final class LoginTests extends BaseTestWithRestart implements LogProvider {

	private LoginSteps loginSteps = getStepsComponent().loginSteps();
	private NavigationSteps navigationSteps = getStepsComponent().navigationSteps();
	private DriverSteps driverSteps = getStepsComponent().driverSteps();

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

