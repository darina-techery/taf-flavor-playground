package steps;

import actions.LoginActions;
import ru.yandex.qatools.allure.annotations.Step;
import utils.annotations.UseActions;

public class LoginSteps {
	private final LoginActions loginActions;

	@UseActions
	public LoginSteps(LoginActions loginActions) {
		this.loginActions = loginActions;
	}

	@Step("Login to application with provided credentials")
	public void login(String username, String password) {
		loginActions.waitForScreen();
		loginActions.setLogin(username);
		loginActions.setPassword(password);
		loginActions.loginToApp();
	}
}
