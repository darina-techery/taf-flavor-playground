package steps;

import actions.ActivityFeedActions;
import actions.LoginActions;
import ru.yandex.qatools.allure.annotations.Step;
import utils.annotations.UseActions;

public class LoginSteps {
	private final LoginActions loginActions;
	private final ActivityFeedActions activityFeedActions;

	@UseActions
	public LoginSteps(LoginActions loginActions, ActivityFeedActions activityFeedActions) {
		this.loginActions = loginActions;
		this.activityFeedActions = activityFeedActions;
	}

	@Step("Login to application with provided credentials")
	public void login(String username, String password) {
		loginActions.waitForScreen();
		loginActions.setLogin(username);
		loginActions.setPassword(password);
		loginActions.loginToApp();
		//navigation.waitForLandingPage();
	}
}
