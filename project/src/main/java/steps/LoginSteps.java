package steps;

import actions.AlertActions;
import actions.LoginActions;
import data.structures.User;
import org.apache.logging.log4j.LogManager;
import ru.yandex.qatools.allure.annotations.Step;
import utils.annotations.UseActions;

public class LoginSteps {
	private final LoginActions loginActions;

	private final AlertActions alertActions;

	@UseActions
	public LoginSteps(LoginActions loginActions, AlertActions alertActions) {
		this.alertActions = alertActions;
		this.loginActions = loginActions;
	}

	@Step("Submit provided login credentials: '{0}' ")
	public void submitCredentials(User user) {
		loginActions.waitForScreen();
		loginActions.setLogin(user.username);
		loginActions.setPassword(user.password);
		loginActions.submit();
	}

	@Step("Login to application with valid credentials: '{0}' / '{1}'")
	public void loginWithValidCredentials(User user) {
		submitCredentials(user);
		alertActions.declinePermissionRequestAlert();
	}

	@Step("Login to application if required")
	public void loginIfRequired(User user) {
		if (loginActions.isScreenActive()) {
			loginWithValidCredentials(user);
		}
		LogManager.getLogger().info("logged in");
	}
}
