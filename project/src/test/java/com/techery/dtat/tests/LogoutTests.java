package com.techery.dtat.tests;

import com.techery.dtat.tests.base.BaseTestForLoggedInUserWithRestart;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Issue;
import ru.yandex.qatools.allure.annotations.TestCaseId;
import com.techery.dtat.steps.LoginSteps;
import com.techery.dtat.steps.NavigationSteps;
import com.techery.dtat.utils.log.LogProvider;

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

