import base.BaseTestAfterLogin;
import utils.runner.Assert;
import org.testng.annotations.Test;
import steps.LoginSteps;
import steps.NavigationSteps;
import utils.log.LogProvider;

public final class LogoutTests extends BaseTestAfterLogin implements LogProvider {

	private LoginSteps loginSteps = getUiStepsComponent().loginSteps();
	private NavigationSteps navigationSteps = getUiStepsComponent().navigationSteps();

	@Test
	public void logoutFromApp() {
		navigationSteps.logoutUser();
		Assert.assertThat("Login screen is NOT active after logout from application.",loginSteps.isScreenActive());
	}

}

