package steps;

import actions.AlertActions;
import actions.LoginActions;
import user.UserCredentials;
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
	public void submitCredentials(UserCredentials user) {
		loginActions.waitForScreen();
		loginActions.setLogin(user.getUsername());
		loginActions.setPassword(user.getPassword());
		loginActions.submit();
	}

	@Step("Login to application with valid credentials: '{0}' / '{1}'")
	public void loginWithValidCredentials(UserCredentials user) {
		submitCredentials(user);
		alertActions.declinePermissionRequestAlert();
	}

	@Step("Login to application if required")
	public void loginIfRequired(UserCredentials user) {
		if (loginActions.isScreenActive()) {
			loginWithValidCredentials(user);
		}
		LogManager.getLogger().info("logged in");
	}
}
