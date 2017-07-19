import base.BaseTestForLoggedInUserWithRestart;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Issue;
import ru.yandex.qatools.allure.annotations.TestCaseId;
import steps.LoginSteps;
import steps.NavigationSteps;
import utils.log.LogProvider;

public final class LogoutTests extends BaseTestForLoggedInUserWithRestart implements LogProvider {

	private LoginSteps loginSteps = getStepsComponent().loginSteps();
	private NavigationSteps navigationSteps = getStepsComponent().navigationSteps();

	@TestCaseId("https://techery.testrail.net/index.php?/cases/view/213563")
	@Issue("https://techery.atlassian.net/browse/DTAUT-435")
	@Test
	public void logoutFromApp() {
		navigationSteps.logoutUser();
		loginSteps.assertThatLoginScreenIsShown();
		loginSteps.assertThatPasswordFieldEmpty();
	}

}

