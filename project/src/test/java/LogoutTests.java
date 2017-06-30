import base.BaseTestAfterLogin;
import base.BaseTestWithDriver;
import data.ui.MenuItem;
import org.testng.Assert;
import org.testng.annotations.Test;
import steps.DriverSteps;
import steps.LoginSteps;
import steps.NavigationSteps;
import utils.log.LogProvider;

public final class LogoutTests extends BaseTestAfterLogin implements LogProvider {

	private LoginSteps loginSteps = getUiStepsComponent().loginSteps();
	private NavigationSteps navigationSteps = getUiStepsComponent().navigationSteps();

	@Test
	public void logoutFromApp() {
		navigationSteps.logoutUser();
		Assert.assertTrue(loginSteps.isScreenActive(),"Login screen is NOT active after logout from application.");

	}

}

