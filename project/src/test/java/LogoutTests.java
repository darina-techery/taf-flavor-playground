import base.BaseTestAfterLogin;
import ru.yandex.qatools.allure.annotations.Issue;
import ru.yandex.qatools.allure.annotations.TestCaseId;
import utils.runner.Assert;
import org.testng.annotations.Test;
import steps.LoginSteps;
import steps.NavigationSteps;
import utils.log.LogProvider;

public final class LogoutTests extends BaseTestAfterLogin implements LogProvider {

	private LoginSteps loginSteps = getStepsComponent().loginSteps();
	private NavigationSteps navigationSteps = getStepsComponent().navigationSteps();

	@TestCaseId("https://techery.testrail.net/index.php?/cases/view/213563")
	@Issue("https://techery.atlassian.net/browse/DTAUT-435")
	@Test
	public void logoutFromApp() {
		navigationSteps.logoutUser();
		Assert.assertThat("Login screen should be active after logout from application.",loginSteps.isScreenActive());
	}

}

